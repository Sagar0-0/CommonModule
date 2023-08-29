package fit.asta.health.feature.scheduler.ui.viewmodel

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.db.entity.TagEntity
import fit.asta.health.data.scheduler.remote.net.scheduler.Time
import fit.asta.health.data.scheduler.remote.net.tag.ScheduleTagNetData
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.doman.usecase.getAlarm
import fit.asta.health.feature.scheduler.doman.usecase.getAlarmScreenUi
import fit.asta.health.feature.scheduler.doman.usecase.getIntervalUi
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ASUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AdvUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.TimeUi
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.feature.scheduler.ui.screen.tagscreen.TagState
import fit.asta.health.feature.scheduler.ui.screen.tagscreen.TagsUiState
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.feature.scheduler.util.VibrationPattern
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
class SchedulerViewModel
@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo,
    private val prefManager: PrefManager,
    private val stateManager: StateManager
) : ViewModel() {
    private var alarmEntity: AlarmEntity? = null


    private val _alarmSettingUiState = MutableStateFlow(ASUiState())
    val alarmSettingUiState = _alarmSettingUiState.asStateFlow()

    private val interval = MutableStateFlow(IvlUiState())
    val timeSettingUiState = interval.asStateFlow()
    private val advancedReminder = mutableStateOf(AdvUiState())

    private val _tagsUiState = MutableStateFlow(TagsUiState())
    val tagsUiState = _tagsUiState.asStateFlow()

    private val userId = "6309a9379af54f142c65fbff"

    private val _uiError = MutableStateFlow("")
    val uiError = _uiError.asStateFlow()
    val areInputsValid =
        combine(alarmSettingUiState, tagsUiState) { alarm, _ ->
            alarm.alarmName.isNotEmpty() && alarm.alarmDescription.isNotEmpty() && alarm.tagName.isNotEmpty()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

    init {
        getEditUiData()
    }


    private fun getEditUiData() {
        viewModelScope.launch {
            prefManager.userData.map { it.alarmId }
                .collectLatest { alarmId ->
                    if (alarmId != 999L) {
                        alarmLocalRepo.getAlarm(alarmId)?.let { alarm ->
                            alarmEntity = alarm
                            updateUi(alarm)
                        }
                    }
                }
        }
        viewModelScope.launch {
            prefManager.userData.map { it.tone }
                .collectLatest { tone ->
                    if (tone != "hi" && alarmEntity == null) {
                        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(toneUri = tone)
                    }
                }
        }

    }


    fun setImportant(important: Boolean) {
        _alarmSettingUiState.value =
            _alarmSettingUiState.value.copy(important = important)
    }

    fun setSound(tone: ToneUiState) {
        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
            toneName = tone.name,
            toneType = tone.type,
            toneUri = tone.uri
        )
    }

    fun setVibrationPattern(vibration: VibrationPattern) {
        _alarmSettingUiState.value =
            _alarmSettingUiState.value.copy(
                vibrationPattern = vibration,
                vibration = when (vibration) {
                    VibrationPattern.Short -> "Short"
                    VibrationPattern.Long -> "Long"
                    VibrationPattern.Intermittent -> "Intermittent"
                }
            )
    }

    fun setVibration(vibrationStatus: Boolean) {
        _alarmSettingUiState.value =
            _alarmSettingUiState.value.copy(vibrationStatus = vibrationStatus)
    }

    fun setReminderMode(choice: String) {
        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(mode = choice)
    }

    fun setDescription(description: String) {
        _alarmSettingUiState.value =
            _alarmSettingUiState.value.copy(alarmDescription = description)
    }

    fun setLabel(label: String) {
        _alarmSettingUiState.value =
            _alarmSettingUiState.value.copy(alarmName = label)
    }

    fun setStatus(status: Boolean) {
        _alarmSettingUiState.value =
            _alarmSettingUiState.value.copy(status = status)
    }

    fun setWeek(index: Int) {
        val checked = _alarmSettingUiState.value.week.isBitOn(index)
        _alarmSettingUiState.value =
            _alarmSettingUiState.value.copy(
                week = _alarmSettingUiState.value.week.setBit(index, !checked)
            )
    }

    fun setAlarmTime(time: Time) {
        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
            timeHours = time.hours,
            timeMinutes = time.minutes
        )
    }

    fun getTagData() {
        viewModelScope.launch {
            alarmLocalRepo.getAllTags().collect {
                _tagsUiState.value = _tagsUiState.value.copy(tagsList = it)
                Log.d("manish", "getTagData: $it")
            }
        }
    }


    fun updateServerTag(label: String, url: String) {
        viewModelScope.launch {
            val result = backendRepo.updateScheduleTag(
                schedule = ScheduleTagNetData(
                    uid = userId, type = 1, tag = label, url = url
                )
            )
            when (result) {
                is ResponseState.Success -> {
                    getTagData()
                }

                else -> {}
            }
        }
    }

    fun selectedTag(tag: TagEntity) {
        tag.selected = !tag.selected
        _tagsUiState.value = _tagsUiState.value.copy(
            selectedTag = TagState(
                selected = tag.selected, meta = tag.meta, id = tag.id
            )
        )
        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
            tagId = tag.meta.id, tagName = tag.meta.name, tagUrl = tag.meta.url
        )
    }

    fun undoTag(tag: TagEntity) {
        viewModelScope.launch {
            alarmLocalRepo.insertTag(tag)
            getTagData()
        }
    }

    fun deleteTag(tag: TagEntity) {
        viewModelScope.launch {
            alarmLocalRepo.deleteTag(tag)
            getTagData()
        }
    }


    fun setSnoozeTime(snoozeTime: Int) {
        interval.value = interval.value.copy(snoozeTime = snoozeTime)
    }


    fun setPreNotificationDuration(time: Int) {
        if (isValidAdvancedDuration(time)) {
            advancedReminder.value = advancedReminder.value.copy(time = time)
            interval.value = interval.value.copy(advancedReminder = advancedReminder.value)
        }
    }

    fun setPreNotificationStatus(choice: Boolean) {
        advancedReminder.value = advancedReminder.value.copy(status = choice)
        interval.value = interval.value.copy(advancedReminder = advancedReminder.value)
    }


    private fun isValidAdvancedDuration(time: Int): Boolean {
        val hour = _alarmSettingUiState.value.timeHours
        val min = _alarmSettingUiState.value.timeMinutes - time
        if (hour == 0 && min < 0) {
            return false
        }
        return true
    }


    fun setDataAndSaveAlarm(
        context: Context,
        alarmItem: AlarmEntity? = alarmEntity,
    ) {
        if (alarmItem == null) {
            _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                uDate = LocalTime.now().toString(),
                cDate = LocalTime.now().toString(),
                sync = 1,
                cBy = 1
            )
        } else {
            _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                uDate = LocalTime.now().toString(),
                cDate = alarmItem.meta.cDate,
                sync = 1,
                cBy = alarmItem.meta.cBy
            )
        }
        val alarmTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
            context, RingtoneManager.TYPE_ALARM
        )
        if (_alarmSettingUiState.value.toneUri.isEmpty()) {
            _alarmSettingUiState.value =
                _alarmSettingUiState.value.copy(toneUri = alarmTone.toString())
        }
        val entity = alarmSettingUiState.value.getAlarm(
            userId = alarmItem?.userId ?: userId,
            idFromServer = alarmItem?.idFromServer ?: "",
            alarmId = alarmItem?.alarmId
                ?: (System.currentTimeMillis() + AtomicLong().incrementAndGet()),
            interval = timeSettingUiState.value
        )
        viewModelScope.launch {
            Log.d("alarm", "setDataAndSaveAlarm: $entity")
            if (entity.status) {
                stateManager.registerAlarm(context, entity)
            }
            alarmLocalRepo.insertAlarm(entity)
            resetUi()
        }
    }

    fun resetUi() {
        _alarmSettingUiState.value = ASUiState()
        interval.value = IvlUiState()
    }

    //timeSettingActivity

    private fun updateUi(it: AlarmEntity) {
        interval.value = it.getIntervalUi()
        _alarmSettingUiState.value = it.getAlarmScreenUi()
    }


    private fun isValidState(state: TimeUi): Boolean {
        try {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date()
            val format = "yyyy-MM-dd HH:mm"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val dateObj1: Date = sdf.parse(
                dateFormat.format(date) + " " + alarmSettingUiState.value.timeHours + ":" + alarmSettingUiState.value.timeMinutes
            )!!
            val dateObj2: Date = sdf.parse(
                dateFormat.format(date) + " " + state.hours + ":" + state.minutes
            )!!
            dateObj1.time.hours
            return dateObj1.time < dateObj2.time


        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    fun setHourMin(hourMinAmPm: HourMinAmPm?) {
        hourMinAmPm?.let {
            _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                timeHours = it.hour,
                timeMinutes = hourMinAmPm.min
            )
        }
    }

    fun setEndAlarm(time: TimeUi) {
        if (isValidState(time)) interval.value = interval.value.copy(endAlarmTime = time)
    }

    fun setStatusEndAlarm(choice: Boolean) {
        interval.value = interval.value.copy(statusEnd = choice)
    }

    fun deleteEndAlarm() {
        interval.value = interval.value.copy(endAlarmTime = TimeUi())
    }

    companion object {
        const val TAG = "debug_spotify"
    }
}