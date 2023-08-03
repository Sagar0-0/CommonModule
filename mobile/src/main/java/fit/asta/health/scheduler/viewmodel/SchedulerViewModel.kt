package fit.asta.health.scheduler.viewmodel

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.ASUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AdvUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmSettingEvent
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.RepUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.StatUiState
import fit.asta.health.scheduler.compose.screen.tagscreen.TagState
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsEvent
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsUiState
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingEvent
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.AlarmUtils
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.AlarmSync
import fit.asta.health.scheduler.model.doman.getAlarm
import fit.asta.health.scheduler.model.net.tag.ScheduleTagNetData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
class SchedulerViewModel
@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo,
    private val prefUtils: PrefUtils,
    private val alarmUtils: AlarmUtils
) : ViewModel() {
    private var alarmEntity: AlarmEntity? = null

    private val _staticIntervalsList = mutableStateListOf<StatUiState>()
    val staticIntervalsList = MutableStateFlow(_staticIntervalsList)
    private val _variantIntervalsList = mutableStateListOf<StatUiState>()
    val variantIntervalsList = MutableStateFlow(_variantIntervalsList)

    private val _alarmSettingUiState = mutableStateOf(ASUiState())
    val alarmSettingUiState: State<ASUiState> = _alarmSettingUiState

    private val interval = mutableStateOf(IvlUiState())
    private val advancedReminder = mutableStateOf(AdvUiState())
    val timeSettingUiState: State<IvlUiState> = interval

    private val _tagsUiState = mutableStateOf(TagsUiState())
    val tagsUiState: State<TagsUiState> = _tagsUiState

    private val userId = "6309a9379af54f142c65fbff"

    init {  getEditUiData() }

    private fun getEditUiData() {
        viewModelScope.launch {
            prefUtils.getPreferences("alarm", defaultValue = 999).collect {
                if (it != 999) {
                    alarmLocalRepo.getAlarm(it)?.let { alarm ->
                        alarmEntity = alarm
                        updateUi(alarm)
                    }
                } else {
                    _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                        time_hours = LocalTime.now().hour.toString(),
                        time_midDay = LocalTime.now().hour > 12,
                        time_minutes = LocalTime.now().minute.toString()
                    )
                }
            }
        }
    }

    fun aSEvent(uiEvent: AlarmSettingEvent) {
        when (uiEvent) {
            is AlarmSettingEvent.SetAlarmTime -> {
                _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                    time_hours = uiEvent.Time.hours,
                    time_midDay = uiEvent.Time.midDay,
                    time_minutes = uiEvent.Time.minutes
                )
            }

            is AlarmSettingEvent.SetWeek -> {
                when (uiEvent.week) {
                    0 -> {
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(sunday = !_alarmSettingUiState.value.sunday)
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(recurring = checkRecurring())
                    }

                    1 -> {
                        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                            monday = !_alarmSettingUiState.value.monday
                        )
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(recurring = checkRecurring())
                    }

                    2 -> {
                        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                            tuesday = !_alarmSettingUiState.value.tuesday
                        )
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(recurring = checkRecurring())
                    }

                    3 -> {
                        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                            wednesday = !_alarmSettingUiState.value.wednesday
                        )
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(recurring = checkRecurring())
                    }

                    4 -> {
                        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                            thursday = !_alarmSettingUiState.value.thursday
                        )
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(recurring = checkRecurring())
                    }

                    5 -> {
                        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                            friday = !_alarmSettingUiState.value.friday
                        )
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(recurring = checkRecurring())
                    }

                    6 -> {
                        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                            saturday = !_alarmSettingUiState.value.saturday
                        )
                        _alarmSettingUiState.value =
                            _alarmSettingUiState.value.copy(recurring = checkRecurring())
                    }
                }
            }

            is AlarmSettingEvent.SetStatus -> {
                _alarmSettingUiState.value =
                    _alarmSettingUiState.value.copy(status = uiEvent.status)
            }

            is AlarmSettingEvent.SetLabel -> {
                _alarmSettingUiState.value =
                    _alarmSettingUiState.value.copy(alarm_name = uiEvent.label)
            }

            is AlarmSettingEvent.SetDescription -> {
                _alarmSettingUiState.value =
                    _alarmSettingUiState.value.copy(alarm_description = uiEvent.description)
            }

            is AlarmSettingEvent.SetReminderMode -> {
                _alarmSettingUiState.value = _alarmSettingUiState.value.copy(mode = uiEvent.choice)
            }

            is AlarmSettingEvent.SetVibration -> {
                _alarmSettingUiState.value =
                    _alarmSettingUiState.value.copy(vibration_status = uiEvent.choice)
            }

            is AlarmSettingEvent.SetVibrationIntensity -> {
                _alarmSettingUiState.value =
                    _alarmSettingUiState.value.copy(vibration_percentage = uiEvent.vibration.toString())
            }

            is AlarmSettingEvent.SetSound -> {
                _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                    tone_name = uiEvent.tone.name,
                    tone_type = uiEvent.tone.type,
                    tone_uri = uiEvent.tone.uri
                )
            }

            is AlarmSettingEvent.SetImportant -> {
                _alarmSettingUiState.value =
                    _alarmSettingUiState.value.copy(important = uiEvent.important)
            }

            is AlarmSettingEvent.GotoTagScreen -> {
                getTagData()
            }

            is AlarmSettingEvent.GotoTimeSettingScreen -> {
                _variantIntervalsList.addAll(interval.value.variantIntervals)
                _staticIntervalsList.addAll(interval.value.staticIntervals)
            }

            is AlarmSettingEvent.Save -> {
                setDataAndSaveAlarm(uiEvent.context, alarmEntity)
            }

            is AlarmSettingEvent.ResetUi -> {
                resetUi()
            }
        }
    }

    private fun getTagData() {
        viewModelScope.launch {
            alarmLocalRepo.getAllTags().collect {
                _tagsUiState.value = _tagsUiState.value.copy(tagsList = it)
                Log.d("manish", "getTagData: $it")
            }
        }
    }

    fun tagsEvent(uiEvent: TagsEvent) {
        when (uiEvent) {
            is TagsEvent.DeleteTag -> {
                viewModelScope.launch {
                    alarmLocalRepo.deleteTag(uiEvent.tag)
                    getTagData()
                }
            }

            is TagsEvent.UndoTag -> {
                viewModelScope.launch {
                    alarmLocalRepo.insertTag(uiEvent.tag)
                    getTagData()
                }
            }

            is TagsEvent.SelectedTag -> {
                val tag = uiEvent.tag
                tag.selected = !tag.selected
                _tagsUiState.value = _tagsUiState.value.copy(
                    selectedTag = TagState(
                        selected = tag.selected, meta = tag.meta, id = tag.id
                    )
                )
                _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                    tagId = tag.meta.id, tag_name = tag.meta.name, tag_url = tag.meta.url
                )
            }

            is TagsEvent.UpdateTag -> {
                viewModelScope.launch {
                    val result = backendRepo.updateScheduleTag(
                        schedule = ScheduleTagNetData(
                            uid = userId, type = 1, tag = uiEvent.label, url = uiEvent.url
                        )
                    )
                    when (result) {
                        is NetworkResult.Success -> {
                            getTagData()
                        }
                        is NetworkResult.Error -> {}
                        is NetworkResult.Loading -> {}
                    }
                }
            }
        }
    }

    fun tSEvent(uiEvent: TimeSettingEvent) {
        when (uiEvent) {
            is TimeSettingEvent.SetSnooze -> {
                interval.value = interval.value.copy(snoozeTime = uiEvent.time)
            }

            is TimeSettingEvent.SetDuration -> {
                interval.value = interval.value.copy(duration = uiEvent.time)
            }

            is TimeSettingEvent.SetAdvancedDuration -> {
                if (isValidAdvancedDuration(uiEvent.time)) {
                    advancedReminder.value = advancedReminder.value.copy(time = uiEvent.time)
                    interval.value = interval.value.copy(advancedReminder = advancedReminder.value)
                } else {
                    Toast.makeText(uiEvent.context, "select minimum duration", Toast.LENGTH_LONG)
                        .show()
                }
            }

            is TimeSettingEvent.SetAdvancedStatus -> {
                advancedReminder.value = advancedReminder.value.copy(status = uiEvent.choice)
                interval.value = interval.value.copy(advancedReminder = advancedReminder.value)
            }

            is TimeSettingEvent.RemindAtEndOfDuration -> {
                interval.value = interval.value.copy(isRemainderAtTheEnd = uiEvent.choice)
            }

            is TimeSettingEvent.SetStatus -> {
                interval.value = interval.value.copy(status = uiEvent.choice)
            }

            is TimeSettingEvent.SetVariantStatus -> {
                interval.value = interval.value.copy(isVariantInterval = uiEvent.choice)
            }

            is TimeSettingEvent.AddVariantInterval -> {
                if (isValidState(
                        uiEvent.variantInterval,
                        uiEvent.context
                    ) && !_variantIntervalsList.contains(uiEvent.variantInterval)
                ) {
                    _variantIntervalsList.add(uiEvent.variantInterval)
                    interval.value =
                        interval.value.copy(variantIntervals = variantIntervalsList.value)
                }
            }

            is TimeSettingEvent.DeleteVariantInterval -> {
                if (_variantIntervalsList.contains(uiEvent.variantInterval)) {
                    _variantIntervalsList.remove(uiEvent.variantInterval)
                    interval.value =
                        interval.value.copy(variantIntervals = variantIntervalsList.value)
                }
            }

            is TimeSettingEvent.SetRepetitiveIntervals -> {
                interval.value = interval.value.copy(repeatableInterval = uiEvent.interval)
                _staticIntervalsList.addAll(getSlots(uiEvent.context))
                interval.value = interval.value.copy(staticIntervals = staticIntervalsList.value)
            }

            is TimeSettingEvent.Save -> {
            }
        }
    }

    private fun isValidAdvancedDuration(time: Int): Boolean {
        val hour = _alarmSettingUiState.value.time_hours.toInt()
        val min = _alarmSettingUiState.value.time_minutes.toInt() - time
        if (hour == 0 && min < 0) {
            return false
        }
        return true
    }



    private fun setDataAndSaveAlarm(
        context: Context,
        alarmItem: AlarmEntity?,
    ) {
        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
            saveButtonEnable = false
        )
        alarmItem?.let {
            _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
                saveProgress = "cancel old alarm"
            )
            alarmUtils.cancelScheduleAlarm(it, true)
        }
        val alarmTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
            context, RingtoneManager.TYPE_ALARM
        )
        if (_alarmSettingUiState.value.tone_uri.isEmpty()) {
            _alarmSettingUiState.value =
                _alarmSettingUiState.value.copy(tone_uri = alarmTone.toString())
        }
        val entity =alarmSettingUiState.value.getAlarm( userId = alarmItem?.userId ?: userId,
            idFromServer = alarmItem?.idFromServer ?: "",
            alarmId = alarmItem?.alarmId ?: System.currentTimeMillis().mod(199999),
            interval =timeSettingUiState.value
        )
        viewModelScope.launch {
            _alarmSettingUiState.value = _alarmSettingUiState.value.copy(saveProgress = "Success..")
            if (entity.status) {
                alarmUtils.scheduleAlarm(entity)
            }
            if (alarmItem != null&&alarmItem.idFromServer.isNotEmpty()) {
                alarmLocalRepo.insertSyncData(
                    AlarmSync(
                        alarmId = entity.alarmId,
                        scheduleId = entity.idFromServer
                    )
                )
            }
            alarmLocalRepo.insertAlarm(entity)
            resetUi()
        }
    }

    private fun resetUi() {
        _alarmSettingUiState.value = ASUiState()
        interval.value = IvlUiState()
        _variantIntervalsList.clear()
        _staticIntervalsList.clear()
    }

    //timeSettingActivity


    fun setAlarmEntityIntent(alarmEntity: AlarmEntity?) {
        this@SchedulerViewModel.alarmEntity = alarmEntity
        alarmEntity?.let { updateUi(it) }
    }

    private fun updateUi(it: AlarmEntity) {
        this@SchedulerViewModel.alarmEntity = it
        interval.value = interval.value.copy(
            status = it.interval.status,
            advancedReminder = AdvUiState(
                status = it.interval.advancedReminder.status,
                time = it.interval.advancedReminder.time
            ),
            duration = it.interval.duration,
            isRemainderAtTheEnd = it.interval.isRemainderAtTheEnd,
            repeatableInterval = RepUiState(
                time = it.interval.repeatableInterval.time,
                unit = it.interval.repeatableInterval.unit
            ),
            staticIntervals = it.interval.staticIntervals.map {
                StatUiState(
                    hours = it.hours,
                    midDay = it.midDay,
                    minutes = it.minutes,
                    name = it.name,
                    id = it.id
                )
            },
            snoozeTime = it.interval.snoozeTime,
            variantIntervals = it.interval.variantIntervals.map {
                StatUiState(
                    hours = it.hours,
                    midDay = it.midDay,
                    minutes = it.minutes,
                    name = it.name,
                    id = it.id
                )
            },
            isVariantInterval = it.interval.isVariantInterval
        )

        _alarmSettingUiState.value = _alarmSettingUiState.value.copy(
            important = it.important,
            alarm_description = it.info.description,
            alarm_name = it.info.name,
            tag_name = it.info.tag,
            tagId = it.info.tagId,
            tag_url = it.info.url,
            cBy = it.meta.cBy,
            cDate = it.meta.cDate,
            sync = it.meta.sync,
            uDate = it.meta.uDate,
            status = it.status,
            time_hours = it.time.hours,
            time_midDay = it.time.midDay,
            time_minutes = it.time.minutes,
            tone_name = it.tone.name,
            tone_type = it.tone.type,
            tone_uri = it.tone.uri,
            vibration_percentage = it.vibration.percentage,
            vibration_status = it.vibration.status,
            friday = it.week.friday,
            saturday = it.week.saturday,
            sunday = it.week.sunday,
            monday = it.week.monday,
            thursday = it.week.thursday,
            tuesday = it.week.tuesday,
            wednesday = it.week.wednesday,
            recurring = it.week.recurring,
            mode = it.mode
        )
    }


    private fun getSlots(context: Context): ArrayList<StatUiState> {
        val slots = ArrayList<StatUiState>()
        try {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date()
            val format = "yyyy-MM-dd HH:mm"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val dateObj1: Date = sdf.parse(
                dateFormat.format(date) + " " + alarmSettingUiState.value.time_hours + ":" + alarmSettingUiState.value.time_minutes
            )!!

            val dateObj2: Date = sdf.parse(
                dateFormat.format(date) + " " + "23:59"
            )!!
            Toast.makeText(context, "$dateObj1 $dateObj2", Toast.LENGTH_LONG).show()
            var dif = dateObj1.time
            while (dif < dateObj2.time) {
                val slot = Date(dif)
                val sformat = "HH:mm:a"
                val aDateFormat = SimpleDateFormat(sformat, Locale.getDefault())
                val timeParts = aDateFormat.format(slot).toString().split(":")
                Log.d("TAGTAGTAG", "getSlots: $timeParts")
                slots.add(
                    StatUiState(
                        id = (1..999999999).random(),
                        name = alarmSettingUiState.value.alarm_name,
                        hours = timeParts[0],
                        minutes = timeParts[1],
                        midDay = timeParts[2].lowercase() != "am"
                    )
                )
                dif += if (interval.value.repeatableInterval.unit == "Hour") {
                    (interval.value.repeatableInterval.time * 60 * 60000).toLong()
                } else {
                    (interval.value.repeatableInterval.time * 60000).toLong()
                }
                if (slots.size > 2) {
                    break
                }
            }
            Log.d("TAGTAGTAG", "getSlots: $slots")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAGTAGTAG", "getSlots:error ${e.printStackTrace()}")
            Toast.makeText(context, "${e.printStackTrace()}", Toast.LENGTH_LONG).show()
        }
        slots.removeAt(0)
        return slots
    }


    fun isValidState(state: StatUiState, context: Context): Boolean {
        try {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date()
            val format = "yyyy-MM-dd HH:mm"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val dateObj1: Date = sdf.parse(
                dateFormat.format(date) + " " + alarmSettingUiState.value.time_hours + ":" + alarmSettingUiState.value.time_minutes
            )!!
            val dateObj2: Date = sdf.parse(
                dateFormat.format(date) + " " + state.hours + ":" + state.minutes
            )!!
            dateObj1.time.hours
            return if (dateObj1.time < dateObj2.time) {
                true
            } else {
                Toast.makeText(context, "select variant time after alarm time ", Toast.LENGTH_LONG)
                    .show()
                false
            }


        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "${e.printStackTrace()}", Toast.LENGTH_LONG).show()
        }
        return false
    }

    private fun checkRecurring(): Boolean {
        return (_alarmSettingUiState.value.sunday ||
                _alarmSettingUiState.value.monday ||
                _alarmSettingUiState.value.tuesday ||
                _alarmSettingUiState.value.wednesday ||
                _alarmSettingUiState.value.thursday ||
                _alarmSettingUiState.value.friday ||
                _alarmSettingUiState.value.saturday
                )
    }

    companion object {
        const val TAG = "debug_spotify"
    }
}