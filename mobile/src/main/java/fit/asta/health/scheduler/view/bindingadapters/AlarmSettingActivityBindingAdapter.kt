package fit.asta.health.scheduler.view.bindingadapters

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.google.gson.Gson
import fit.asta.health.R
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.scheduler.Info
import fit.asta.health.scheduler.model.net.scheduler.Time
import fit.asta.health.scheduler.model.net.scheduler.Tone
import fit.asta.health.scheduler.model.net.scheduler.Vib
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.util.Constants.Companion.settings
import fit.asta.health.scheduler.view.alarmsetting.TagsActivity
import fit.asta.health.scheduler.view.alarmsetting.bottomsheets.*
import fit.asta.health.scheduler.view.interfaces.BottomSheetInterface
import fit.asta.health.scheduler.viewmodel.AlarmViewModel
import fit.asta.health.scheduler.viewmodel.SchedulerBackendViewModel
import fit.asta.health.common.utils.NetworkResult
import xyz.aprildown.ultimateringtonepicker.RingtonePickerDialog
import xyz.aprildown.ultimateringtonepicker.UltimateRingtonePicker


class AlarmSettingActivityBindingAdapter {

    companion object {
        private const val TAG = "TAG-BOTTOMBINDING"

        @RequiresApi(Build.VERSION_CODES.M)
        @BindingAdapter(
            value = ["android:setAlarmStatus", "android:setAlarmWeek", "android:setAlarmTag", "android:setTimepicker", "android:setAlarmTitle", "android:setAlarmDescription", "android:setAlarmMode", "android:setAlarmImportant", "android:setAlarmVibrate", "android:setAlarmVibrateStatus", "android:setAlarmTone", "android:setAlarmViewModel", "android:alarmItem", "android:setBackendConnectivityViewModel"],
            requireAll = true
        )
        @JvmStatic
        fun setDataAndSaveAlarm(
            saveButton: ImageButton,
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
            alarmViewModel: AlarmViewModel,
            alarmItem: AlarmEntity?,
            backendViewModel: SchedulerBackendViewModel
        ) {
            Log.d(TAG, "setDataAndSaveAlarm status: ${alarmStatus.isChecked}")
            saveButton.setOnClickListener {
                Log.d(
                    TAG,
                    "setDataAndSaveAlarm: $alarmName ${alarmRepeat.tag}"
                )
                lateinit var newAlarmItem: AlarmEntity
                val tag = alarmTag.getTag(R.id.tagMeta) as TagEntity?
                if (alarmItem != null) {
                    if (!alarmItem.interval.isVariantInterval) {
                        alarmItem.interval.staticIntervals =
                            Constants.getSlots(
                                saveButton.context, alarmItem.copy(
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
//                    alarmItem.alarmInterval!!.isIntervalBeingUsed = false
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
                            userId = Constants.USER_ID,
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
                } else {
//                    if (timePicker.hour >= 12) {
//                        newAlarmItem = AlarmEntity(
//                            alarmStatus = alarmStatus.isChecked,
//                            alarmWeek = Constants.extractDaysOfAlarm(alarmRepeat.tag.toString()),
//                            alarmTag = alarmTag,
//                            alarmHour = "${timePicker.hour - 12}",
//                            alarmMinute = "${timePicker.minute}",
//                            alarmMidDay = true,
//                            alarmName = alarmName.tag.toString(),
//                            alarmDescription = alarmDescription.tag.toString(),
//                            alarmInterval = IntervalItem(
//                                false,
//                                false,
//                                RepeatItem(1, "Hour"),
//                                emptyList(),
//                                emptyList(),
//                                0,
//                                AdvancedReminderItem(false, 0),
//                                false,
//                                0
//                            ),
//                            alarmMode = alarmMode.tag.toString(),
//                            alarmImportant = alarmImportant.tag.toString().toBoolean(),
//                            alarmVibrate = VibrationItem(
//                                alarmVibrateStatus.tag.toString().toBoolean(),
//                                alarmVibratePercentage.tag.toString().toInt()
//                            ),
//                            alarmTone = RingtoneItem(
//                                alarmTone.text.toString(),
//                                if (alarmTone.tag != null) (alarmTone.tag.toString()) else ""
//                            )
//                        )
//                    } else {
//                        newAlarmItem = AlarmEntity(
//                            alarmStatus = alarmStatus.isChecked,
//                            alarmWeek = Constants.extractDaysOfAlarm(alarmRepeat.tag.toString()),
//                            alarmTag = alarmTag,
//                            alarmHour = "${timePicker.hour}",
//                            alarmMinute = "${timePicker.minute}",
//                            alarmMidDay = false,
//                            alarmName = alarmName.tag.toString(),
//                            alarmDescription = alarmDescription.tag.toString(),
//                            alarmInterval = IntervalItem(
//                                false,
//                                false,
//                                RepeatItem(1, "Hour"),
//                                emptyList(),
//                                emptyList(),
//                                0,
//                                AdvancedReminderItem(false, 0),
//                                false,
//                                0
//                            ),
//                            alarmMode = alarmMode.tag.toString(),
//                            alarmImportant = alarmImportant.tag.toString().toBoolean(),
//                            alarmVibrate = VibrationItem(
//                                alarmVibrateStatus.tag.toString().toBoolean(),
//                                alarmVibratePercentage.tag.toString().toInt()
//                            ),
//                            alarmTone = RingtoneItem(
//                                alarmTone.text.toString(),
//                                alarmTone.tag.toString()
//                            )
//                        )
//                    }
                }
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
                                    return@setOnClickListener
                                } else if (it.hours.toInt() + 12 == newAlarmItem.time.hours.toInt() + 12) {
                                    if (it.hours.toInt() < newAlarmItem.time.hours.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
                                    } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
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
                                    return@setOnClickListener
                                } else if (it.hours.toInt() == newAlarmItem.time.hours.toInt() + 12) {
                                    if (it.minutes.toInt() < newAlarmItem.time.minutes.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
                                    } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
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
                                    return@setOnClickListener
                                } else if (it.hours.toInt() + 12 == newAlarmItem.time.hours.toInt()) {
                                    if (it.minutes.toInt() < newAlarmItem.time.minutes.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
                                    } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
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
                                    return@setOnClickListener
                                } else if (it.hours.toInt() == newAlarmItem.time.hours.toInt()) {
                                    if (it.minutes.toInt() < newAlarmItem.time.minutes.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
                                    } else if (it.minutes.toInt() == newAlarmItem.time.minutes.toInt()) {
                                        Toast.makeText(
                                            alarmName.context,
                                            "Please select interval which are only after main alarm!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
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
                var map: LinkedHashMap<String, Any> = LinkedHashMap()
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
                Log.d(TAG, "setDataAndSaveAlarm: $map")
                val jsonObject: String? = Gson().toJson(map)
                val simpleObject = Gson().fromJson(jsonObject, AlarmEntity::class.java)
                Log.d(TAG, "setDataAndSaveAlarm: $jsonObject")
                backendViewModel.sendScheduler(simpleObject)
                backendViewModel.sendSchedulerResponse.observe(saveButton.context as AppCompatActivity) { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            Log.d(TAG, "setDataAndSaveAlarm: ${result.data} ${result.message}")
                            if (result.data?.data?.id == null) {
                                backendViewModel.getScheduleDetails(simpleObject.idFromServer)
                            } else {
                                backendViewModel.getScheduleDetails(result.data.data.id)
                            }
                            backendViewModel.scheduleResponse.observe(saveButton.context as AppCompatActivity) { schedule ->
                                when (schedule) {
                                    is NetworkResult.Success -> {
                                        Log.d(
                                            TAG,
                                            "setDataAndSaveAlarm: ${schedule.data} ${schedule.message}"
                                        )
                                        alarmViewModel.insertAlarm(schedule.data?.data!!)
                                        if (alarmStatus.isChecked) {
                                            newAlarmItem.schedule(saveButton.context.applicationContext)
                                        }
                                        (saveButton.context as AppCompatActivity).finish()
                                    }
                                    is NetworkResult.Error -> {
                                        Log.d(
                                            TAG,
                                            "setDataAndSaveAlarm: ${schedule.data} ${schedule.message}"
                                        )
                                    }
                                    is NetworkResult.Loading -> {
                                        Log.d(
                                            TAG,
                                            "setDataAndSaveAlarm: ${schedule.data} ${schedule.message}"
                                        )
                                    }
                                }
                            }
                        }
                        is NetworkResult.Error -> {
                            Log.d(TAG, "setDataAndSaveAlarm: ${result.data} ${result.message}")
                        }
                        is NetworkResult.Loading -> {
                            Log.d(TAG, "setDataAndSaveAlarm: ${result.data} ${result.message}")
                        }
                    }
                }
            }
        }

        @BindingAdapter("android:dismissBottomSheet")
        @JvmStatic
        fun dismissBottomSheet(
            dismissButton: ImageButton,
            string: String
        ) {
            dismissButton.setOnClickListener {
                (dismissButton.context as AppCompatActivity).finish()
            }
        }

        @BindingAdapter("android:retrieveAlarmName")
        @JvmStatic
        fun retrieveAlarmName(alarmNameLabel: TextView, alarmItem: AlarmEntity?) {
            if (alarmItem != null) {
                alarmNameLabel.text = alarmItem.info.name
            }
        }

//        @BindingAdapter("android:retrieveAlarmMode")
//        @JvmStatic
//        fun retrieveAlarmMode(alarmModeLabel: TextView, alarmItem: AlarmEntity?) {
//            if (alarmItem != null) {
//                alarmModeLabel.text = alarmItem.alarmMode
//                Log.d(TAG, "retrieveAlarmMode: ${alarmItem.alarmMode}")
//            } else {
//                alarmModeLabel.text = "Notification"
//            }
//        }

        @BindingAdapter("android:retrieveSwitches")
        @JvmStatic
        fun retrieveAlarmSwitches(switch: SwitchCompat, alarmItem: AlarmEntity?) {
            switch.tag = switch.isChecked
            switch.setOnCheckedChangeListener { _, _ ->
                switch.tag = switch.isChecked
            }
        }

        @BindingAdapter("android:retrieveAlarmTag")
        @JvmStatic
        fun retrieveAlarmTag(alarmModeTag: TextView, alarmItem: AlarmEntity?) {
            if (alarmItem != null) {
                alarmModeTag.text = alarmItem.info.tag
            }
        }

        @BindingAdapter("android:retrieveAlarmWeek")
        @JvmStatic
        fun retrieveAlarmWeek(alarmRepeat: TextView, alarmItem: AlarmEntity?) {
            if (alarmItem != null) {
                alarmRepeat.text = Constants.getRecurringDaysText(alarmItem.week)
                alarmRepeat.tag = Constants.getRecurringDaysText(alarmItem.week)
            }
        }

//        @BindingAdapter("android:retrieveAlarmDescription")
//        @JvmStatic
//        fun retrieveAlarmDescription(alarmModeDescription: TextView, alarmItem: AlarmEntity?) {
//            if (alarmItem != null) {
//                alarmModeDescription.text = alarmItem.alarmDescription
//            } else {
//                alarmModeDescription.text = "Relax to Energize"
//            }
//        }

        @BindingAdapter(
            value = ["android:retrieveAlarmVibrationPoints", "android:retrieveAlarmVibrationStatus"],
            requireAll = true
        )
        @JvmStatic
        fun retrieveAlarmVibration(
            alarmModeVibration: TextView,
            alarmItem: AlarmEntity?,
            alarmModeStatusSwitch: SwitchCompat
        ) {
            if (alarmItem != null) {
                alarmModeVibration.text = "${alarmItem.vibration.percentage}%"
                alarmModeVibration.tag = alarmItem.vibration.percentage
                alarmModeStatusSwitch.isChecked = alarmItem.vibration.status
            }
        }

        @BindingAdapter("android:retrieveAlarmSound")
        @JvmStatic
        fun retrieveAlarmSound(alarmModeSound: TextView, alarmItem: AlarmEntity?) {
            if (alarmItem != null) {
                alarmModeSound.text = alarmItem.tone.name
                alarmModeSound.tag = alarmItem.tone.uri
            } else {
                val alarmTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
                    alarmModeSound.context,
                    RingtoneManager.TYPE_ALARM
                )
                alarmModeSound.text = "Default Tone"
                alarmModeSound.tag = alarmTone
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        @BindingAdapter("android:retrieveAlarmTime")
        @JvmStatic
        fun retrieveAlarmTimer(alarmTimePicker: TimePicker, alarmItem: AlarmEntity?) {
            if (alarmItem != null) {
                alarmTimePicker.hour =
                    if (alarmItem.time.midDay) alarmItem.time.hours.toInt() + 12 else alarmItem.time.hours.toInt()
                alarmTimePicker.minute = alarmItem.time.minutes.toInt()
            }
        }

//        @BindingAdapter(
//            value = ["android:setupIntervalIntentListener", "android:setupIntervalIntentResultListener"],
//            requireAll = true
//        )
//        @JvmStatic
//        fun setupIntervalScreenDisplayListener(
//            container: LinearLayout,
//            alarmItem: AlarmEntity?,
//            resultLauncher: ActivityResultLauncher<Intent>
//        ) {
//            container.setOnClickListener {
//
//            }
//        }

        @BindingAdapter("android:setupTagsIntentListener")
        @JvmStatic
        fun setupTagsScreenDisplayListener(container: LinearLayout, tag: String) {
            container.setOnClickListener {
                val intent = Intent(container.context, TagsActivity::class.java)
                container.context.startActivity(intent)
            }
        }

        @BindingAdapter(
            value = ["android:setupRepeatInputItem", "android:setupAlarmRepeatChangedListener"],
            requireAll = true
        )
        @JvmStatic
        fun setupRepeatInputListener(
            container: LinearLayout,
            alarmItem: AlarmEntity?,
            bottomSheetInterface: BottomSheetInterface
        ) {
            container.setOnClickListener {
                val repeatBottomSheet = RepeatBottomSheet(bottomSheetInterface)
                val bundle = Bundle()
                bundle.putParcelable("alarmItem", alarmItem)
                repeatBottomSheet.arguments = bundle
                repeatBottomSheet.show(
                    (container.context as AppCompatActivity).supportFragmentManager,
                    RepeatBottomSheet.TAG
                )
            }
        }

        @BindingAdapter(
            value = ["android:setupLabelInputItem", "android:setupAlarmLabelChangedListener"],
            requireAll = true
        )
        @JvmStatic
        fun setupLabelInputListener(
            container: LinearLayout,
            alarmItem: AlarmEntity?,
            bottomSheetInterface: BottomSheetInterface
        ) {
            container.setOnClickListener {
                val labelInputBottomSheet = LabelInputBottomSheet(bottomSheetInterface)
                val bundle = Bundle()
                bundle.putParcelable("alarmItem", alarmItem)
                labelInputBottomSheet.arguments = bundle
                labelInputBottomSheet.show(
                    (container.context as AppCompatActivity).supportFragmentManager,
                    LabelInputBottomSheet.TAG
                )
            }
        }

        @BindingAdapter(
            value = ["android:setupDescriptionInputItem", "android:setupAlarmDescriptionChangedListener"],
            requireAll = true
        )
        @JvmStatic
        fun setupDescriptionInputListener(
            container: LinearLayout,
            alarmItem: AlarmEntity?,
            bottomSheetInterface: BottomSheetInterface
        ) {
            container.setOnClickListener {
                val descriptionInputBottomSheet = DescriptionInputBottomSheet(bottomSheetInterface)
                val bundle = Bundle()
                bundle.putParcelable("alarmItem", alarmItem)
                descriptionInputBottomSheet.arguments = bundle
                descriptionInputBottomSheet.show(
                    (container.context as AppCompatActivity).supportFragmentManager,
                    DescriptionInputBottomSheet.TAG
                )
            }
        }

        @BindingAdapter(
            value = ["android:setupReminderModeInputItem", "android:setupAlarmReminderModeChangedListener"],
            requireAll = true
        )
        @JvmStatic
        fun setupReminderModeInputListener(
            container: LinearLayout,
            alarmItem: AlarmEntity?,
            bottomSheetInterface: BottomSheetInterface
        ) {
            container.setOnClickListener {
                val reminderModeBottomSheetBinding = ReminderModeBottomSheet(bottomSheetInterface)
                val bundle = Bundle()
                bundle.putParcelable("alarmItem", alarmItem)
                reminderModeBottomSheetBinding.arguments = bundle
                reminderModeBottomSheetBinding.show(
                    (container.context as AppCompatActivity).supportFragmentManager,
                    ReminderModeBottomSheet.TAG
                )
            }
        }

        @BindingAdapter(
            value = ["android:setupVibrationInputItem", "android:setupAlarmVibrationChangedListener"],
            requireAll = true
        )
        @JvmStatic
        fun setupVibrationInputListener(
            container: LinearLayout,
            alarmItem: AlarmEntity?,
            bottomSheetInterface: BottomSheetInterface
        ) {
            container.setOnClickListener {
                val vibrationBottomSheet = VibrationBottomSheet(bottomSheetInterface)
                val bundle = Bundle()
                bundle.putParcelable("alarmItem", alarmItem)
                vibrationBottomSheet.arguments = bundle
                vibrationBottomSheet.show(
                    (container.context as AppCompatActivity).supportFragmentManager,
                    ReminderModeBottomSheet.TAG
                )
            }
        }

        @BindingAdapter(
            value = ["android:setupSoundInputItem", "android:setupRingtoneLabelLayout"],
            requireAll = true
        )
        @JvmStatic
        fun setupSoundInputListener(
            container: LinearLayout,
            alarmItem: AlarmEntity?,
            ringtoneLabel: TextView
        ) {
            container.setOnClickListener {
                RingtonePickerDialog.createEphemeralInstance(
                    settings = settings,
                    dialogTitle = "Select Sound",
                    listener = object : UltimateRingtonePicker.RingtonePickerListener {
                        override fun onRingtonePicked(ringtones: List<UltimateRingtonePicker.RingtoneEntry>) {
                            Log.d(TAG, "onRingtonePicked: $ringtones")
                            ringtoneLabel.text = ringtones[0].name
                            ringtoneLabel.tag = ringtones[0].uri
                        }
                    }
                ).show((container.context as AppCompatActivity).supportFragmentManager, null)
            }

        }
    }
}