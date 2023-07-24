package fit.asta.health.scheduler.viewmodel

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.ASUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AdvUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmSettingEvent
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.RepUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.StatUiState
import fit.asta.health.scheduler.compose.screen.homescreen.HomeEvent
import fit.asta.health.scheduler.compose.screen.homescreen.HomeUiState
import fit.asta.health.scheduler.compose.screen.tagscreen.TagState
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsEvent
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsUiState
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingEvent
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.AlarmSync
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.scheduler.Adv
import fit.asta.health.scheduler.model.net.scheduler.Info
import fit.asta.health.scheduler.model.net.scheduler.Ivl
import fit.asta.health.scheduler.model.net.scheduler.Meta
import fit.asta.health.scheduler.model.net.scheduler.Rep
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.model.net.scheduler.Time
import fit.asta.health.scheduler.model.net.scheduler.Tone
import fit.asta.health.scheduler.model.net.scheduler.Vib
import fit.asta.health.scheduler.model.net.scheduler.Wk
import fit.asta.health.scheduler.model.net.tag.ScheduleTagNetData
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
class SchedulerViewModel
@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo,
    private val savedStateHandle: SavedStateHandle,
    private val prefUtils: PrefUtils
) : ViewModel() {
    private var alarmEntity: AlarmEntity? = null

    private val _staticIntervalsList = mutableStateListOf<StatUiState>()
    val staticIntervalsList = MutableStateFlow(_staticIntervalsList)
    private val _variantIntervalsList = mutableStateListOf<StatUiState>()
    val variantIntervalsList = MutableStateFlow(_variantIntervalsList)
    private val _alarmList = mutableStateListOf<AlarmEntity>()
    val alarmList = MutableStateFlow(_alarmList)

    private val _homeUiState = mutableStateOf(HomeUiState())
    val homeUiState: State<HomeUiState> = _homeUiState

    private val _alarmSettingUiState = mutableStateOf(ASUiState())
    val alarmSettingUiState: State<ASUiState> = _alarmSettingUiState

    private val interval = mutableStateOf(IvlUiState())
    private val advancedReminder = mutableStateOf(AdvUiState())
    val timeSettingUiState: State<IvlUiState> = interval

    private val _tagsUiState = mutableStateOf(TagsUiState())
    val tagsUiState: State<TagsUiState> = _tagsUiState

    private val userId = "6309a9379af54f142c65fbff"

    init {

//        refreshData()
        getAlarms()
       getEditUiData()
    }
    private fun getEditUiData(){
       viewModelScope.launch {
          prefUtils.getPreferences("alarm", defaultValue = 999).collect{
              if (it!=999){
                  alarmLocalRepo.getAlarm(it)?.let {alarm->
                      alarmEntity= alarm
                      updateUi(alarm)
                  }
              }
          }
       }
    }
    fun hSEvent(uiEvent: HomeEvent) {
        when (uiEvent) {
            is HomeEvent.EditAlarm -> {
                alarmEntity = uiEvent.alarm
                updateUi(uiEvent.alarm)
            }

            is HomeEvent.SetAlarm -> {
//                updateAlarmState(uiEvent.alarm, uiEvent.state, uiEvent.context)
            }

            is HomeEvent.DeleteAlarm -> {
                deleteAlarm(uiEvent.alarm, uiEvent.context)
            }

            is HomeEvent.UndoAlarm -> {
                undoAlarm(uiEvent.alarm,uiEvent.context)
            }

            else -> {}
        }
    }

    private fun undoAlarm(alarm: AlarmEntity,context: Context) {
        viewModelScope.launch {
            alarmLocalRepo.insertAlarm(alarm)
            if (alarm.status){alarm.scheduleAlarm(context)}
            if (alarm.idFromServer.isNotEmpty()) {
                alarmLocalRepo.deleteSyncData(
                    AlarmSync(
                        alarmId = alarm.alarmId,
                        scheduleId = alarm.idFromServer
                    )
                )
            }
            _alarmList.add(alarm)
        }
    }

    private fun updateAlarmState(alarm: AlarmEntity, state: Boolean, context: Context) {
        val newAlarm = alarm.copy(status = state)
        _homeUiState.value = _homeUiState.value.copy(buttonState = false)
        if (state) {
            newAlarm.scheduleAlarm(context)
        } else {
            alarm.cancelScheduleAlarm(context, alarm.alarmId, true)
        }
        viewModelScope.launch {
            alarmLocalRepo.insertAlarm(newAlarm)
            if (newAlarm.idFromServer.isEmpty()) {
                alarmLocalRepo.insertSyncData(AlarmSync(alarmId = newAlarm.alarmId))
            }
            _homeUiState.value = _homeUiState.value.copy(buttonState = true)
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
                Log.d("manish", "GotoTagScreen: ")
//                refreshTagData()
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

            else -> {}
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
//                            refreshTagData()
                            getTagData()
                        }

                        is NetworkResult.Error -> {}
                        is NetworkResult.Loading -> {}
                    }
                }
            }

            else -> {}
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
                setIvl()
            }

            else -> {}
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


    fun isIntervalDataValid(interval: IvlUiState): Boolean {
        return interval.duration > 0 && interval.snoozeTime > 0 && interval.variantIntervals.isNotEmpty()
    }


    private fun refreshData() {
        viewModelScope.launch {
            backendRepo.getScheduleListDataFromBackend(userId).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        Log.d(SpotifyConstants.TAG, "onCreate list result: ${result.data}")
                        alarmLocalRepo.getAllAlarm().collect { list ->
                            val map = hashMapOf<String, Boolean>()
                            result.data?.list?.forEach { alarmEntity ->
                                map[alarmEntity.idFromServer] = false
                            }
                            list.forEach { alarmData ->
                                map[alarmData.idFromServer] = true
                            }
                            Log.d(SpotifyConstants.TAG, "  result: $list")
                            result.data?.list?.forEach { alarmEntity ->
                                if (map[alarmEntity.idFromServer] == false) {
                                    alarmLocalRepo.insertAlarm(alarmEntity = alarmEntity)
                                }
                            }
                        }

                    }

                    is NetworkResult.Error -> {
                        Log.d(SpotifyConstants.TAG, "onCreate list result: ${result.data}")
                    }

                    is NetworkResult.Loading -> {
                        Log.d(SpotifyConstants.TAG, "onCreate list result: ${result.data}")
                    }
                }

            }
        }
    }

    private fun getAlarms() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect { list ->
                Log.d(SpotifyConstants.TAG, " list result: $list")
                _alarmList.clear()
                _alarmList.addAll(list)
            }
        }

    }

    private fun refreshTagData() {
        viewModelScope.launch {
            backendRepo.getTagListFromBackend(userId).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        Log.d("manish", "Success: ")
                        Log.d("manish", "onCreate: ${result.data} ${result.message}")
                        alarmLocalRepo.getAllTags().collect { list ->
                            Log.d("manish", "local: ")
                            val map = hashMapOf<String, Boolean>()
                            list.forEach {
                                map[it.meta.id] = true
                                Log.d("manish", "map: $map")
                            }
                            result.data?.list?.forEach {
                                if (map[it.id] != true) {
                                    Log.d("manish", "refreshTagData: ")
                                    alarmLocalRepo.insertTag(TagEntity(false, it))
                                }
                            }
                            result.data?.customTagList?.forEach {
                                if (map[it.id] != true) {
                                    Log.d("manish", "refreshTagData: ")
                                    alarmLocalRepo.insertTag(TagEntity(false, it))
                                }
                            }
                        }
                    }

                    is NetworkResult.Error -> {
                        Log.d("manish", "onCreate: ${result.data} ${result.message}")
                    }

                    is NetworkResult.Loading -> {
                        Log.d("manish", "onCreate: ${result.data} ${result.message}")
                    }
                }

            }
        }
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
            it.cancelScheduleAlarm(context, it.alarmId, true)
        }
        val alarmTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
            context, RingtoneManager.TYPE_ALARM
        )
        if (_alarmSettingUiState.value.tone_uri.isEmpty()) {
            _alarmSettingUiState.value =
                _alarmSettingUiState.value.copy(tone_uri = alarmTone.toString())
        }
        val newAlarmItem: AlarmEntity?
        newAlarmItem = AlarmEntity(
            status = alarmSettingUiState.value.status,
            week = Wk(
                friday = alarmSettingUiState.value.friday,
                monday = alarmSettingUiState.value.monday,
                saturday = alarmSettingUiState.value.saturday,
                sunday = alarmSettingUiState.value.sunday,
                thursday = alarmSettingUiState.value.thursday,
                tuesday = alarmSettingUiState.value.tuesday,
                wednesday = alarmSettingUiState.value.wednesday,
                recurring = alarmSettingUiState.value.recurring
            ),
            info = Info(
                name = alarmSettingUiState.value.alarm_name,
                description = alarmSettingUiState.value.alarm_description,
                tag = alarmSettingUiState.value.tag_name,
                tagId = alarmSettingUiState.value.tagId,
                url = alarmSettingUiState.value.tag_url
            ),
            time = Time(
                hours = alarmSettingUiState.value.time_hours,
                minutes = alarmSettingUiState.value.time_minutes,
                midDay = alarmSettingUiState.value.time_midDay,
            ),
            interval = setIvl(),
            mode = alarmSettingUiState.value.mode,
            important = alarmSettingUiState.value.important,
            vibration = Vib(
                percentage = alarmSettingUiState.value.vibration_percentage,
                status = alarmSettingUiState.value.vibration_status
            ),
            tone = Tone(
                name = alarmSettingUiState.value.tone_name,
                type = alarmSettingUiState.value.tone_type,
                uri = alarmSettingUiState.value.tone_uri
            ),
            userId = alarmItem?.userId ?: userId,
            idFromServer = alarmItem?.idFromServer ?: "",
            alarmId = alarmItem?.alarmId ?: System.currentTimeMillis().mod(199999),
            meta = Meta(
                cDate = alarmSettingUiState.value.cDate,
                cBy = alarmSettingUiState.value.cBy,
                sync = alarmSettingUiState.value.sync,
                uDate = alarmSettingUiState.value.uDate
            )
        )

        val map: LinkedHashMap<String, Any> = LinkedHashMap()
        map["id"] = newAlarmItem.idFromServer
        map["uid"] = newAlarmItem.userId
        map["almId"] = newAlarmItem.alarmId
        map["meta"] = newAlarmItem.meta
        map["info"] = newAlarmItem.info
        map["time"] = newAlarmItem.time
        map["sts"] = newAlarmItem.status
        map["imp"] = newAlarmItem.important
        map["mode"] = newAlarmItem.mode
        map["wk"] = newAlarmItem.week
        map["ivl"] = newAlarmItem.interval
        map["vib"] = newAlarmItem.vibration
        map["tone"] = newAlarmItem.tone
        val jsonObject: String? = Gson().toJson(map)
        val simpleObject = Gson().fromJson(jsonObject, AlarmEntity::class.java)
        viewModelScope.launch {
            _alarmSettingUiState.value = _alarmSettingUiState.value.copy(saveProgress = "Success..")
            if (simpleObject.status) {
                simpleObject.scheduleAlarm(context)
            }
            if (alarmItem != null) {
                alarmLocalRepo.insertSyncData(
                    AlarmSync(
                        alarmId = simpleObject.alarmId,
                        scheduleId = simpleObject.idFromServer
                    )
                )
            }
            alarmLocalRepo.insertAlarm(simpleObject)
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
    private fun setIvl(): Ivl {
        return Ivl(
            advancedReminder = Adv(
                status = timeSettingUiState.value.advancedReminder.status,
                time = timeSettingUiState.value.advancedReminder.time
            ),
            duration = timeSettingUiState.value.duration,
            isRemainderAtTheEnd = timeSettingUiState.value.isRemainderAtTheEnd,
            repeatableInterval = Rep(
                time = timeSettingUiState.value.repeatableInterval.time,
                unit = timeSettingUiState.value.repeatableInterval.unit
            ),
            snoozeTime = timeSettingUiState.value.snoozeTime,
            staticIntervals = timeSettingUiState.value.staticIntervals.map {
                Stat(
                    hours = it.hours,
                    midDay = it.midDay,
                    minutes = it.minutes,
                    name = it.name,
                    id = it.id
                )
            },
            variantIntervals = timeSettingUiState.value.variantIntervals.map {
                Stat(
                    hours = it.hours,
                    midDay = it.midDay,
                    minutes = it.minutes,
                    name = it.name,
                    id = it.id
                )
            },
            isVariantInterval = timeSettingUiState.value.isVariantInterval,
            status = false
        )
    }

    private fun deleteAlarm(alarmItem: AlarmEntity, context: Context) {
        _alarmList.remove(alarmItem)
        viewModelScope.launch {
            if (alarmItem.status) alarmItem.cancelScheduleAlarm(
                context, alarmItem.alarmId, true
            )
            alarmLocalRepo.deleteAlarm(alarmItem)
            if (alarmItem.idFromServer.isNotEmpty()) {
                alarmLocalRepo.insertSyncData(
                    AlarmSync(alarmId = alarmItem.alarmId, scheduleId = alarmItem.idFromServer)
                )
            }
        }
    }

    fun setAlarmEntityIntent(alarmEntity: AlarmEntity?) {
        this@SchedulerViewModel.alarmEntity = alarmEntity
        alarmEntity?.let { updateUi(it) }
    }

    private fun updateUi(it: AlarmEntity) {
        this@SchedulerViewModel.alarmEntity = it
        interval.value = interval.value.copy(
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
        const val ALARMSETTINGKEY: String = "alarmSettingUiState"
        const val TAGSKEY: String = "tagsUiState"
        const val TIMESETTINGKEY: String = "timeSettingUiState"
    }
}