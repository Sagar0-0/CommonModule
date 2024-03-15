package fit.asta.health.navigation.today.ui.vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentDateTime
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.scheduler.local.AlarmInstanceDao
import fit.asta.health.data.scheduler.local.model.AlarmEntity
import fit.asta.health.data.scheduler.util.Weekdays
import fit.asta.health.data.scheduler.util.getTodayData
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.navigation.today.ui.view.CalendarDataSource
import fit.asta.health.navigation.today.ui.view.CalendarUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TodayPlanViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val alarmBackendRepo: AlarmBackendRepo,
    private val alarmInstanceDao: AlarmInstanceDao,
    private val prefManager: PrefManager,
    private val stateManager: StateManager,
    @UID private val uId: String,
    private val authRepo: AuthRepo
) : ViewModel() {
    private val _alarmListMorning = mutableStateListOf<AlarmEntity>()
    val alarmListMorning = MutableStateFlow(_alarmListMorning)
    private val _alarmListAfternoon = mutableStateListOf<AlarmEntity>()
    val alarmListAfternoon = MutableStateFlow(_alarmListAfternoon)
    private val _alarmListEvening = mutableStateListOf<AlarmEntity>()
    val alarmListEvening = MutableStateFlow(_alarmListEvening)
    private val _defaultScheduleVisibility = MutableStateFlow(false)
    val defaultScheduleVisibility = _defaultScheduleVisibility.asStateFlow()
    private val calendar: Calendar = Calendar.getInstance()
    val today: Int = calendar.get(Calendar.DAY_OF_WEEK)

    private val _todayState = MutableStateFlow<UiState<TodayData>>(UiState.Idle)
    val todayState = _todayState.asStateFlow()

    private val _alarmState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val alarmState = _alarmState.asStateFlow()


    private val dataSource = CalendarDataSource()

    private val _calendarUiModel =
        MutableStateFlow(dataSource.getData(lastSelectedDate = dataSource.today))
    val calendarUiModel = _calendarUiModel.asStateFlow()

    init {
        getWeatherSunSlots()
        setWeekDate()
    }

    val userEditMessage = prefManager.userData
        .map { it.userEditMessage }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = true,
        )

    fun setUserEditMessage(value: Boolean = true) {
        viewModelScope.launch {
            prefManager.setUserEditMessage(value)
        }
    }


    fun setWeekDate(date: CalendarUiModel.Date? = null) {
        if (date != null) {
            _calendarUiModel.value = _calendarUiModel.value.copy(selectedDate = date,
                visibleDates = _calendarUiModel.value.visibleDates.map {
                    it.copy(isSelected = it.date.isEqual(date.date))
                }
            )
        }
        getAlarms()
    }

    fun getUserName(): String {
        return authRepo.getUser()?.name ?: "Aastha"
    }

    fun setAlarmPreferences(value: Long) {
        viewModelScope.launch {
            prefManager.setAlarmId(value)
        }
    }

    fun getDefaultSchedule(context: Context) {
        viewModelScope.launch {
            when (val result = alarmBackendRepo.getDefaultSchedule(uId)) {
                is ResponseState.Success -> {
                    _alarmState.emit(result.toUiState())
                    result.data.forEach { alarmEntity ->
                        val alarm = alarmEntity.copy(
                            meta = Meta(
                                cBy = alarmEntity.meta.cBy,
                                cDate = getCurrentTime(),
                                sync = 1,
                                uDate = getCurrentTime()
                            ),
                            idFromServer = "",
                            userId = uId,
                            daysOfWeek = Weekdays.ALL,
                            selectedStartDateMillis = calendar.timeInMillis
//                        alarmId = (System.currentTimeMillis() + AtomicLong().incrementAndGet())
                        )
                        stateManager.registerAlarm(context, alarm)
                        viewModelScope.launch {
                            alarmLocalRepo.insertAlarm(alarm)
                        }
                    }
                }

                is ResponseState.ErrorMessage -> {
                    _alarmState.emit(result.toUiState())
                    Log.d("TAG", "getDefaultSchedule: ${result.resId.toStringFromResId(context)}")
                }

                else -> {
                    _alarmState.emit(result.toUiState())
                }
            }
        }
    }


    fun deleteAlarm(alarmItem: AlarmEntity, context: Context) {
        viewModelScope.launch {
            if (alarmItem.status) stateManager.deleteAlarm(context, alarmItem)
            if (alarmItem.idFromServer.isNotEmpty()) {
                alarmLocalRepo.updateAlarm(
                    alarmItem.copy(
                        status = false,
                        meta = Meta(
                            cBy = alarmItem.meta.cBy,
                            cDate = alarmItem.meta.cDate,
                            sync = 2,
                            uDate = getCurrentTime()
                        )
                    )
                )
            } else {
                alarmLocalRepo.deleteAlarm(alarmItem)
                alarmInstanceDao.getInstancesByAlarmId(alarmItem.alarmId)?.let {
                    alarmInstanceDao.delete(it)
                }
            }
            Log.d("today", "deleteAlarm: done")
        }
    }

    fun skipAlarm(alarmItem: AlarmEntity, context: Context) {
        val skipDate = LocalDate.now().dayOfMonth
        viewModelScope.launch {
            stateManager.skipAlarmTodayOnly(context, alarmItem)
            alarmLocalRepo.updateAlarm(alarmItem.copy(skipDate = skipDate))
            Log.d("today", "skipAlarm: done")
        }
    }


    private fun getAlarms() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collectLatest { list ->
                Log.d("TOOL", "getAlarms: ${list.map { it.info.tag }}")
                _defaultScheduleVisibility.value = list.isEmpty()
                _alarmListMorning.clear()
                _alarmListAfternoon.clear()
                _alarmListEvening.clear()
                val day = calendarUiModel.value.selectedDate.date
                val calendar1 = Calendar.getInstance()

                Log.d("TAG", "getAlarms: ${calendarUiModel.value.selectedDate.date.dayOfWeek}")
                list.forEach {
                    calendar1.timeInMillis = it.selectedStartDateMillis
                    val comparisonStartResult = day.compareTo(
                        calendar1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    )

                    if (it.status && comparisonStartResult >= 0) {
                        if (it.selectedEndDateMillis != null) {
                            calendar1.timeInMillis = it.selectedEndDateMillis!!
                            val comparisonEndResult = day.compareTo(
                                calendar1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            )
                            if (comparisonEndResult <= 0) {
                                if (it.daysOfWeek.isRepeating) {
                                    if (it.daysOfWeek.isBitOn(calendarUiModel.value.selectedDate.date.dayOfWeek.value)) {
                                        when (it.time.hours) {
                                            in 0..2 -> {
                                                if (it.time.minutes > 0) _alarmListMorning.add(it)
                                                else _alarmListEvening.add(it)
                                            }

                                            in 3..12 -> {
                                                if (it.time.minutes > 0) _alarmListAfternoon.add(it)
                                                else _alarmListMorning.add(it)
                                            }

                                            in 13..16 -> {
                                                if (it.time.minutes > 0) _alarmListEvening.add(it)
                                                else _alarmListAfternoon.add(it)
                                            }

                                            in 17..23 -> {
                                                _alarmListEvening.add(it)
                                            }
                                        }
                                    }
                                } else {
                                    if (calendarUiModel.value.selectedDate.isToday) {
                                        when (it.time.hours) {
                                            in 0..2 -> {
                                                if (it.time.minutes > 0) _alarmListMorning.add(it)
                                                else _alarmListEvening.add(it)
                                            }

                                            in 3..12 -> {
                                                if (it.time.minutes > 0) _alarmListAfternoon.add(it)
                                                else _alarmListMorning.add(it)
                                            }

                                            in 13..16 -> {
                                                if (it.time.minutes > 0) _alarmListEvening.add(it)
                                                else _alarmListAfternoon.add(it)
                                            }

                                            in 17..23 -> {
                                                _alarmListEvening.add(it)
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (it.daysOfWeek.isRepeating) {
                                if (it.daysOfWeek.isBitOn(calendarUiModel.value.selectedDate.date.dayOfWeek.value)) {
                                    when (it.time.hours) {
                                        in 0..2 -> {
                                            if (it.time.minutes > 0) _alarmListMorning.add(it)
                                            else _alarmListEvening.add(it)
                                        }

                                        in 3..12 -> {
                                            if (it.time.minutes > 0) _alarmListAfternoon.add(it)
                                            else _alarmListMorning.add(it)
                                        }

                                        in 13..16 -> {
                                            if (it.time.minutes > 0) _alarmListEvening.add(it)
                                            else _alarmListAfternoon.add(it)
                                        }

                                        in 17..23 -> {
                                            _alarmListEvening.add(it)
                                        }
                                    }
                                }
                            } else {
                                if (calendarUiModel.value.selectedDate.isToday) {
                                    when (it.time.hours) {
                                        in 0..2 -> {
                                            if (it.time.minutes > 0) _alarmListMorning.add(it)
                                            else _alarmListEvening.add(it)
                                        }

                                        in 3..12 -> {
                                            if (it.time.minutes > 0) _alarmListAfternoon.add(it)
                                            else _alarmListMorning.add(it)
                                        }

                                        in 13..16 -> {
                                            if (it.time.minutes > 0) _alarmListEvening.add(it)
                                            else _alarmListAfternoon.add(it)
                                        }

                                        in 17..23 -> {
                                            _alarmListEvening.add(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                _alarmListMorning.sortBy { it.time.hours * 60 + it.time.minutes }
                _alarmListAfternoon.sortBy { it.time.hours * 60 + it.time.minutes }
                _alarmListEvening.sortBy { it.time.hours * 60 + it.time.minutes }
            }
        }
    }


    private fun getWeatherSunSlots() {
        _todayState.value = UiState.Loading
        viewModelScope.launch {
            prefManager.address.collectLatest {
                viewModelScope.launch {
                    _todayState.value = when (val result = alarmBackendRepo.getTodayDataFromBackend(
                        userID = uId,
                        date = getCurrentDateTime(),
                        location = it.currentAddress,
                        latitude = it.lat.toFloat(),
                        longitude = it.long.toFloat()
                    )) {
                        is ResponseState.Success -> {
                            UiState.Success(result.data.getTodayData())
                        }

                        is ResponseState.ErrorMessage -> {
                            UiState.ErrorMessage(result.resId)
                        }

                        is ResponseState.ErrorRetry -> {
                            UiState.ErrorRetry(result.resId)
                        }

                        else -> {
                            UiState.NoInternet
                        }
                    }
                }
            }
        }
    }
}