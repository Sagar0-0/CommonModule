package fit.asta.health.scheduler.view.bindingadapters

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.shawnlin.numberpicker.NumberPicker
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Rep
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.view.alarmsetting.bottomsheets.AdvancedReminderBottomSheet
import fit.asta.health.scheduler.view.alarmsetting.bottomsheets.DurationTimeBottomSheet
import fit.asta.health.scheduler.view.alarmsetting.bottomsheets.RepeatTimeBottomSheet
import fit.asta.health.scheduler.view.alarmsetting.bottomsheets.SnoozeTimeBottomSheet
import fit.asta.health.scheduler.view.interfaces.DialogInterface
import fit.asta.health.scheduler.view.interfaces.TimeActivityBottomSheetListener
import fit.asta.health.scheduler.viewmodel.TimeSettingViewModel

class TimeSettingActivityBindingAdapter {
    companion object {
        @BindingAdapter("android:setupTimeActivitySwitch")
        @JvmStatic
        fun setupTimeActivitySwitches(switch: SwitchCompat, alarmEntity: AlarmEntity) {
            switch.setOnCheckedChangeListener { _, isChecked ->
                switch.tag = isChecked
                Log.d("TAGTAGTAG", "setupTimeActivitySwitches: $isChecked")
            }
        }

        @BindingAdapter(
            value = ["android:setListenerToDeleteButton", "android:setVariantItemToDeleteButton"],
            requireAll = true
        )
        @JvmStatic
        fun deleteVariantInterval(
            button: ImageView,
            listener: DialogInterface,
            variantItem: Stat
        ) {
            button.setOnClickListener {
                listener.deleteVariantIntervalData(variantItem)
            }
        }

        @BindingAdapter(
            value = ["android:setupInputItem", "android:setupListener", "android:setupChoiceTag"],
            requireAll = true
        )
        @JvmStatic
        fun setupContainerListener(
            container: LinearLayout,
            alarmItem: AlarmEntity?,
            bottomSheetListener: TimeActivityBottomSheetListener,
            choice: String
        ) {
            container.setOnClickListener {
                when (choice) {
                    "snooze" -> {
                        val snoozeTimeBottomSheet = SnoozeTimeBottomSheet(bottomSheetListener)
                        val bundle = Bundle()
                        bundle.putParcelable("alarmItem", alarmItem)
                        snoozeTimeBottomSheet.arguments = bundle
                        snoozeTimeBottomSheet.show(
                            (container.context as AppCompatActivity).supportFragmentManager,
                            SnoozeTimeBottomSheet.TAG
                        )
                    }
                    "advanced reminder" -> {
                        val advancedReminderBottomSheet =
                            AdvancedReminderBottomSheet(bottomSheetListener)
                        val bundle = Bundle()
                        bundle.putParcelable("alarmItem", alarmItem)
                        advancedReminderBottomSheet.arguments = bundle
                        advancedReminderBottomSheet.show(
                            (container.context as AppCompatActivity).supportFragmentManager,
                            AdvancedReminderBottomSheet.TAG
                        )
                    }
                    "duration" -> {
                        val durationTimeBottomSheet = DurationTimeBottomSheet(bottomSheetListener)
                        val bundle = Bundle()
                        bundle.putParcelable("alarmItem", alarmItem)
                        durationTimeBottomSheet.arguments = bundle
                        durationTimeBottomSheet.show(
                            (container.context as AppCompatActivity).supportFragmentManager,
                            DurationTimeBottomSheet.TAG
                        )
                    }
                    "repeat" -> {
                        val repeatTimeBottomSheet = RepeatTimeBottomSheet(bottomSheetListener)
                        val bundle = Bundle()
                        bundle.putParcelable("alarmItem", alarmItem)
                        repeatTimeBottomSheet.arguments = bundle
                        repeatTimeBottomSheet.show(
                            (container.context as AppCompatActivity).supportFragmentManager,
                            RepeatTimeBottomSheet.TAG
                        )
                    }
                }
            }
        }

        @BindingAdapter(
            value = ["android:alarmOkButtonViewModel", "android:alarmOkButtonInputItem", "android:alarmOkButtonInputItemUnit", "android:alarmOkButtonChoiceTag"],
            requireAll = true
        )
        @JvmStatic
        fun alarmOkButtonListener(
            okButton: MaterialButton,
            timeSettingViewModel: TimeSettingViewModel,
            numberPicker: NumberPicker,
            unitPicker: NumberPicker,
            choice: String
        ) {
            okButton.setOnClickListener {
                when (choice) {
                    "snooze" -> {
                        timeSettingViewModel.setSnoozeInViewModel(
                            numberPicker.value
                        )
                        Log.d(
                            "TAGTAGTAG",
                            "snooze: ${timeSettingViewModel.snoozeTime.value} "
                        )
                        timeSettingViewModel.setIsSnoozeBottomSheetVisible(true)
                    }
                    "advanced reminder" -> {
                        timeSettingViewModel.setAdvancedDurationInViewModel(
                            numberPicker.value
                        )
                        Log.d(
                            "TAGTAGTAG",
                            "advanced reminder: ${timeSettingViewModel.advancedDuration.value} "
                        )
                        timeSettingViewModel.setIsAdvancedDurationBottomSheetVisible(true)
                    }
                    "duration" -> {
                        timeSettingViewModel.setDurationInViewModel(
                            numberPicker.value
                        )
                        Log.d(
                            "TAGTAGTAG",
                            "duration: ${timeSettingViewModel.duration.value} "
                        )
                        timeSettingViewModel.setIsDurationBottomSheetVisible(true)
                    }
                    "repeat" -> {
                        timeSettingViewModel.setRepeatInViewModel(
                            Rep(
                                numberPicker.value, if (unitPicker.value == 1) {
                                    "Minute"
                                } else {
                                    "Hour"
                                }
                            )
                        )
                        Log.d(
                            "TAGTAGTAG",
                            "duration: ${timeSettingViewModel.repeat.value} "
                        )
                        timeSettingViewModel.setIsRepeatBottomSheetVisible(true)
                    }
                }
            }
        }
    }
}