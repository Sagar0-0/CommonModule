package fit.asta.health.feature.scheduler.ui.viewmodel

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UserID
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.data.scheduler.local.model.AlarmEntity
import fit.asta.health.data.scheduler.local.model.TagEntity
import fit.asta.health.data.scheduler.remote.net.scheduler.Time
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.data.scheduler.remote.net.tag.TagData
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.data.scheduler.util.toTagEntity
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.usecase.getAlarm
import fit.asta.health.feature.scheduler.usecase.getAlarmScreenUi
import fit.asta.health.feature.scheduler.usecase.getIntervalUi
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ASUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AdvUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.TimeUi
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.feature.scheduler.util.VibrationPattern
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
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
    private val stateManager: StateManager,
    @UserID private val uId: String
) : ViewModel() {
    private var alarmEntity: AlarmEntity? = null


    private val _alarmSettingUiState = MutableStateFlow(ASUiState())
    val alarmSettingUiState = _alarmSettingUiState.asStateFlow()

    private val interval = MutableStateFlow(IvlUiState())
    val timeSettingUiState = interval.asStateFlow()
    private val advancedReminder = mutableStateOf(AdvUiState())

    private val _tagsList = mutableStateListOf<TagEntity>()
    val tagsList = MutableStateFlow(_tagsList)
    private val _customTagList = mutableStateListOf<TagEntity>()
    val customTagList = MutableStateFlow(_customTagList)
    private val _isToolTag = MutableStateFlow(false)
    val isToolTag = _isToolTag.asStateFlow()
    private val _toolTag = MutableStateFlow<String?>(null)
    val toolTag = _toolTag.asStateFlow()


    init {
        Log.d("viewmodel", ": init" )
//        resetUi()
        getEditUiData()
        getTagData()
        getAlarmList()
    }

    private fun getAlarmList() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarmInstanceList()?.let {
                _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                    alarmList = it
                )
            }
        }
    }

    fun setDateRange(start: Long, end: Long?) {
        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
            selectedStartDateMillis = start,
            selectedEndDateMillis = end
        )
    }


    fun getEditUiData() {
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
        Log.e("sound", "setSound: ${tone.name}", )
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
                _tagsList.clear()
                _customTagList.clear()
                it.forEach { tagEntity ->
                    if (!toolTag.value.isNullOrEmpty() && tagEntity.name.equals(
                            toolTag.value,
                            ignoreCase = true
                        )
                    ) {
                        selectedTag(tagEntity)
                        setDescription("description")
                        _isToolTag.value = true
                    }
                    if (tagEntity.uid == uId) {
                        _customTagList.add(tagEntity)
                    } else {
                        _tagsList.add(tagEntity)
                    }
                }
            }
        }
    }


    fun updateServerTag(label: String, url: String) {//change
        viewModelScope.launch {
            val result = backendRepo.updateScheduleTag(
                schedule = NetCustomTag(
                    uid = uId, name = label, localUrl = url.toUri()
                )
            )
            when (result) {
                is ResponseState.Success -> {
                    getTagDataFromServer()
                }

                is ResponseState.ErrorMessage -> {}
                else -> {}
            }
        }
    }


    fun selectedTag(tag: TagEntity) {
        viewModelScope.launch {
            _alarmSettingUiState.emit(
                _alarmSettingUiState.value.copy(
                    tagId = tag.id, tagName = tag.name, tagUrl = tag.url,
                    alarmName = tag.ttl, alarmDescription = tag.dsc
                )
            )
        }
    }


    fun deleteTag(tag: TagEntity) {
        viewModelScope.launch {
            when (backendRepo.deleteTagFromBackend(tag.uid, tag.id)) {
                is ResponseState.Success -> {
                    alarmLocalRepo.deleteTag(tag)
                }

                else -> {}
            }
        }
    }

    fun getTagDataFromServer() {
        viewModelScope.launch {
            when (val result = backendRepo.getTagListFromBackend(uId)) {
                is ResponseState.Success -> {
                    result.data.let { schedulerGetTagsList ->
                        schedulerGetTagsList.tagData.forEach { tag ->
                            insertTag(tag)
                        }
                        schedulerGetTagsList.customTagData?.forEach { tag ->
                            insertTag(tag)
                        }
                    }
                }

                else -> {}
            }
        }
    }

    private suspend fun insertTag(tag: TagData) {
        alarmLocalRepo.insertTag(tag.toTagEntity())
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
                uDate = getCurrentTime(),
                cDate = getCurrentTime(),
                sync = 1,
                cBy = 1
            )
        } else {
            stateManager.deleteAlarm(context, alarmItem)
            _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                uDate = getCurrentTime(),
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
            userId = uId,
            idFromServer = alarmItem?.idFromServer ?: "",
            alarmId = alarmItem?.alarmId
                ?: (System.currentTimeMillis() + AtomicLong().incrementAndGet()),
            interval = timeSettingUiState.value
        )
        viewModelScope.launch {
            if (entity.status) {
                stateManager.registerAlarm(context, entity)
            }
            alarmLocalRepo.insertAlarm(entity)
            resetUi()
        }
        Log.d("alarm", "setDataAndSaveAlarm: $entity")

    }

    fun resetUi() {
        Log.d("viewmodel", "resetUi: ")
        _alarmSettingUiState.value = ASUiState()
        interval.value = IvlUiState()
        setAlarmPreferences(999L)
        _isToolTag.value = false
        _toolTag.value = null
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
                timeMinutes = it.min
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

    fun setAlarmPreferences(value: Long) {
        viewModelScope.launch {
            prefManager.setAlarmId(value)
        }
    }

    fun setToolData(name: String) {
        _toolTag.value = name
        if (tagsList.value.isEmpty()){
            getTagData()
        }
        val tag = tagsList.value.firstOrNull { it.name.equals(name, ignoreCase = true) }
        if (tag != null) {
            selectedTag(tag)
            _isToolTag.value = true
            setDescription("description")
        }
        //we already set desc and all from tags if you want to still change it you can fetch the data set in contants and set here
        // setDescription("your desc")
    }
}