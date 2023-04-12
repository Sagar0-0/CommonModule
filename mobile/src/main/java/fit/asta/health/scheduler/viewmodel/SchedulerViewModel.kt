package fit.asta.health.scheduler.viewmodel

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.*
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsUiState
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingUiState
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.scheduler.*
import fit.asta.health.scheduler.model.net.tag.Data
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulerViewModel
@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _alarmSettingUiState = mutableStateOf(AlarmSettingUiState())
    private val alarmSettingUiState: State<AlarmSettingUiState> = _alarmSettingUiState

    private val _timeSettingUiState = mutableStateOf(TimeSettingUiState())
    private val timeSettingUiState: State<TimeSettingUiState> = _timeSettingUiState

    private val _tagsUiState = mutableStateOf(TagsUiState())
    private val tagsUiState: State<TagsUiState> = _tagsUiState

    private val userId = "6309a9379af54f142c65fbff"
    private val newAlarmItem: AlarmEntity? = null

    //alarmSetting
    private val week = MutableStateFlow(WkUiState())
    private val customTagName = MutableStateFlow(TagUiState())
    private val alarmData = MutableStateFlow(MetaUiState())
    private val time = MutableStateFlow(TimeUiState())
    private val tone = MutableStateFlow(ToneUiState())
    private val vibration = MutableStateFlow(VibUiState())
    private val info = MutableStateFlow(InfoUiState())
    private val interval = MutableStateFlow(IvlUiState())
    private val meta = MutableStateFlow(MetaDataUiState())


    //timeSettingActivity
    private var listOfVariantIntervals: MutableList<Stat> =
        emptyList<Stat>().toMutableList()
    private var listOfStaticIntervals: MutableList<Stat> =
        emptyList<Stat>().toMutableList()

    init {

        refreshData()

    }


    fun ASEvent(uiEvent: AlarmSettingEvent) {
        when (uiEvent) {
            is AlarmSettingEvent.SetAlarmTime -> {
                time.value = time.value.copy(
                    hours = uiEvent.Time.hours,
                    midDay = uiEvent.Time.midDay,
                    minutes = uiEvent.Time.minutes
                )
                alarmData.value=alarmData.value.copy(time = time.value)
                customTagName.value=customTagName.value.copy(meta = alarmData.value)
                _alarmSettingUiState.value =_alarmSettingUiState.value.copy(CustomTagName=customTagName.value)
            }
            is AlarmSettingEvent.SetWeek->{
                week.value=week.value.copy(
                    friday = uiEvent.week.friday,
                    saturday = uiEvent.week.saturday,
                    sunday = uiEvent.week.sunday,
                    monday = uiEvent.week.monday,
                    thursday = uiEvent.week.thursday,
                    tuesday = uiEvent.week.tuesday,
                    wednesday = uiEvent.week.wednesday,
                    recurring = uiEvent.week.recurring
                )
                _alarmSettingUiState.value=_alarmSettingUiState.value.copy(Week = week.value)
            }
            is AlarmSettingEvent.SetStatus->{
                alarmData.value=alarmData.value.copy(status = uiEvent.status)
                customTagName.value=customTagName.value.copy(meta = alarmData.value)
                _alarmSettingUiState.value =_alarmSettingUiState.value.copy(CustomTagName=customTagName.value)
            }
            is AlarmSettingEvent.SetLabel->{
                _alarmSettingUiState.value=_alarmSettingUiState.value.copy(Label = uiEvent.label)
            }
            is AlarmSettingEvent.SetDescription->{
                _alarmSettingUiState.value=_alarmSettingUiState.value.copy(Description = uiEvent.description)
            }
            is AlarmSettingEvent.SetReminderMode->{
                _alarmSettingUiState.value=_alarmSettingUiState.value.copy(Choice = uiEvent.choice)
            }
            is AlarmSettingEvent.SetVibration->{
                vibration.value=vibration.value.copy(
                    percentage = uiEvent.vibration.percentage,
                    status = uiEvent.vibration.status
                )
                alarmData.value=alarmData.value.copy(vibration = vibration.value)
                customTagName.value=customTagName.value.copy(meta = alarmData.value)
                _alarmSettingUiState.value =_alarmSettingUiState.value.copy(CustomTagName=customTagName.value)
            }
            is AlarmSettingEvent.SetImportant->{
                alarmData.value=alarmData.value.copy(important = uiEvent.important)
                customTagName.value=customTagName.value.copy(meta = alarmData.value)
                _alarmSettingUiState.value =_alarmSettingUiState.value.copy(CustomTagName=customTagName.value)
            }
            else -> {}
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            backendRepo.getScheduleListDataFromBackend(userId).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        Log.d(SpotifyConstants.TAG, "onCreate list result: ${result.data}")
                        alarmLocalRepo.getAllAlarm().collect {
                            val offlineSchedules = it
                            val onlineSchedules = result.data?.list

                            val offlineIds = ArrayList<String>()
                            val onlineIds = ArrayList<String>()

                            onlineSchedules?.forEach {
                                onlineIds.add(it.idFromServer)
                            }

                            offlineSchedules.forEach {
                                offlineIds.add(it.idFromServer)
                            }

                            Log.d(SpotifyConstants.TAG, "onlineIds: $onlineIds")
                            Log.d(SpotifyConstants.TAG, "offlineIds: $offlineIds")
                            Log.d(
                                SpotifyConstants.TAG,
                                "minus: ${onlineIds.minus(offlineIds.toSet())}"
                            )

                            onlineIds.minus(offlineIds.toSet()).forEach { id ->
                                onlineSchedules?.forEach { entity ->
                                    if (entity.idFromServer == id) {
                                        alarmLocalRepo.insertAlarm(alarmEntity = entity)
                                    }
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

    // start when go to tagActivity
    private fun refreshTagData() {
        viewModelScope.launch {
            backendRepo.getTagListFromBackend(userId).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        Log.d(SpotifyConstants.TAG, "onCreate: ${result.data} ${result.message}")
                        alarmLocalRepo.getAllTags().collect {
                            val list = it
                            val offlineListOfTags: MutableList<Data> = ArrayList()
                            val onlineListOfTags: MutableList<Data> = ArrayList()
                            result.data?.list?.forEach { meta ->
                                onlineListOfTags.add(meta)
                            }
                            result.data?.customTagList?.forEach { meta ->
                                onlineListOfTags.add(meta)
                            }
                            list.forEach { tag ->
                                offlineListOfTags.add(tag.meta)
                            }

                            offlineListOfTags.containsAll(onlineListOfTags).let { bool ->
                                if (bool) {
                                    Log.d(SpotifyConstants.TAG, "onCreate: Same")
                                } else {
                                    Log.d(SpotifyConstants.TAG, "onCreate: Diff")
                                    onlineListOfTags.minus(offlineListOfTags.toSet())
                                        .forEach { tag ->
                                            alarmLocalRepo.insertTag(TagEntity(false, tag))
                                        }
                                }
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        Log.d(SpotifyConstants.TAG, "onCreate: ${result.data} ${result.message}")
                    }
                    is NetworkResult.Loading -> {
                        Log.d(SpotifyConstants.TAG, "onCreate: ${result.data} ${result.message}")
                    }
                }

            }
        }
    }

    // alarmSettingActivity
    private fun setAlarm(context: Context) {
        val alarmTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_ALARM
        )
        AlarmEntity(
            status = false,
            week = Wk(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
            ),
            time = Time(
                hours = "",
                minutes = "",
                midDay = true,
            ),
            info = Info(
                description = "description",
                name = "Label",
                tag = "Nap",
                tagId = "1",
                url = "url"
            ),
            interval = setIvl(),

            mode = "Notification",
            important = false,
            vibration = Vib(
                "50",
                false
            ),
            tone = Tone(
                "Default",
                1,
                alarmTone.toString()
            ),
            idFromServer = "",
            userId = Constants.USER_ID,
            meta = Meta(
                cBy = 1,
                cDate = "1",
                sync = 1,
                uDate = "1"
            )
        )
    }

    private fun setDataAndSaveAlarm(
        context: Context,
        alarmStatus: SwitchCompat,
        alarmRepeat: TextView,
        alarmTag: TextView,
        timePicker: TimePicker,
        alarmName: TextView,
        alarmDescription: TextView,
        alarmMode: TextView,
        alarmImportant: SwitchCompat,
        alarmVibratePercentage: TextView,
        alarmVibrateStatus: SwitchCompat,
        alarmTone: TextView,
        alarmItem: AlarmEntity?,
    ) {

        var newAlarmItem = newAlarmItem
        val tag = alarmTag.getTag(R.id.tagMeta) as TagEntity?
        if (alarmItem != null) {
            if (!alarmItem.interval.isVariantInterval) {
                alarmItem.interval.staticIntervals =
                    Constants.getSlots(
                        context, alarmItem.copy(
                            time = Time(
                                hours = if (timePicker.hour >= 12) "${timePicker.hour - 12}" else "${timePicker.hour}",
                                minutes = "${timePicker.minute}",
                                midDay = timePicker.hour >= 12,
                            ),
                            info = alarmItem.info.copy(
                                name = alarmName.tag.toString()
                            )
                        )
                    )
            }

            if (timePicker.hour >= 12) {
                newAlarmItem = AlarmEntity(
                    status = alarmStatus.isChecked,
                    week = Constants.extractDaysOfAlarm(alarmRepeat.tag.toString()),
                    info = Info(
                        name = alarmName.tag.toString(),
                        description = alarmDescription.tag.toString(),
                        tag = tag?.meta?.name!!,
                        tagId = tag.meta.id,
                        url = tag.meta.url
                    ),
                    time = Time(
                        hours = "${timePicker.hour - 12}",
                        minutes = "${timePicker.minute}",
                        midDay = true,
                    ),
                    interval = alarmItem.interval,
                    mode = alarmMode.tag.toString(),
                    important = alarmImportant.tag.toString().toBoolean(),
                    vibration = Vib(
                        alarmVibratePercentage.tag.toString(),
                        alarmVibrateStatus.tag.toString().toBoolean()
                    ),
                    tone = Tone(
                        alarmTone.text.toString(),
                        1,
                        alarmTone.tag.toString()
                    ),
                    alarmId = alarmItem.alarmId,
                    userId = userId,
                    idFromServer = alarmItem.idFromServer,
                    meta = alarmItem.meta
                )
            } else {
                newAlarmItem = AlarmEntity(
                    status = alarmStatus.isChecked,
                    week = Constants.extractDaysOfAlarm(alarmRepeat.tag.toString()),
                    info = Info(
                        name = alarmName.tag.toString(),
                        description = alarmDescription.tag.toString(),
                        tag = tag?.meta?.name!!,
                        tagId = tag.meta.id,
                        url = tag.meta.url
                    ),
                    time = Time(
                        hours = "${timePicker.hour}",
                        minutes = "${timePicker.minute}",
                        midDay = true,
                    ),
                    interval = alarmItem.interval,
                    mode = alarmMode.tag.toString(),
                    important = alarmImportant.tag.toString().toBoolean(),
                    vibration = Vib(
                        alarmVibratePercentage.tag.toString(),
                        alarmVibrateStatus.tag.toString().toBoolean()
                    ),
                    tone = Tone(
                        alarmTone.text.toString(),
                        1,
                        alarmTone.tag.toString()
                    ),
                    alarmId = alarmItem.alarmId,
                    userId = Constants.USER_ID,
                    idFromServer = alarmItem.idFromServer,
                    meta = alarmItem.meta
                )
            }
        }
        if (newAlarmItem != null) {
            if (newAlarmItem.interval.isVariantInterval) {
                newAlarmItem.interval.variantIntervals.forEach {
                    if (newAlarmItem.time.midDay) {
                        if (it.midDay) {
                            if (it.hours.toInt() + 12 < newAlarmItem.time.hours.toInt() + 12) {
                                Toast.makeText(
                                    alarmName.context,
                                    "Please select interval which are only after main alarm!",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (it.hours.toInt() + 12 == newAlarmItem.time.hours.toInt() + 12) {
                                if (it.hours.toInt() < newAlarmItem.time.hours.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    alarmName.context,
                                    "Successful",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            if (it.hours.toInt() < newAlarmItem.time.hours.toInt() + 12) {
                                Toast.makeText(
                                    alarmName.context,
                                    "Please select interval which are only after main alarm!",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else if (it.hours.toInt() == newAlarmItem.time.hours.toInt() + 12) {
                                if (it.minutes.toInt() < newAlarmItem.time.minutes.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    alarmName.context,
                                    "Successful",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        if (it.midDay) {
                            if (it.hours.toInt() + 12 < newAlarmItem.time.hours.toInt()) {
                                Toast.makeText(
                                    alarmName.context,
                                    "Please select interval which are only after main alarm!",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else if (it.hours.toInt() + 12 == newAlarmItem.time.hours.toInt()) {
                                if (it.minutes.toInt() < newAlarmItem.time.minutes.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    alarmName.context,
                                    "Successful",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            if (it.hours.toInt() < newAlarmItem.time.hours.toInt()) {
                                Toast.makeText(
                                    alarmName.context,
                                    "Please select interval which are only after main alarm!",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else if (it.hours.toInt() == newAlarmItem.time.hours.toInt()) {
                                if (it.minutes.toInt() < newAlarmItem.time.minutes.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Please select interval which are only after main alarm!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        alarmName.context,
                                        "Successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    alarmName.context,
                                    "Successful",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
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
                backendRepo.updateScheduleDataOnBackend(simpleObject).let { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val results = if (result.data?.data?.id == null) {
                                backendRepo.getScheduleDataFromBackend(simpleObject.idFromServer)
                            } else {
                                backendRepo.getScheduleDataFromBackend(result.data.data.id)
                            }
                            results.collect { schedule ->
                                when (schedule) {
                                    is NetworkResult.Success -> {
                                        alarmLocalRepo.insertAlarm(schedule.data?.data!!)
                                        if (alarmStatus.isChecked) {
                                            newAlarmItem.schedule(context)
                                        }
                                    }
                                    is NetworkResult.Error -> {}
                                    is NetworkResult.Loading -> {}
                                }
                            }
                        }
                        is NetworkResult.Error -> {}
                        is NetworkResult.Loading -> {}
                    }
                }
            }
        }
    }

    //timeSettingActivity
    private fun setIvl(): Ivl {
        return Ivl(
            advancedReminder = Adv(
                false, 15
            ),
            duration = 30,
            isRemainderAtTheEnd = false,
            repeatableInterval = Rep(
                1, "Hour"
            ),
            snoozeTime = 5,
            staticIntervals = listOfStaticIntervals,
            status = false,
            variantIntervals = listOfVariantIntervals,
            isVariantInterval = false
        )
    }

    companion object {
        val ALARMSETTINGKEY: String = "alarmSettingUiState"
        val TAGSKEY: String = "tagsUiState"
        val TIMESETTINGKEY: String = "timeSettingUiState"
    }
}