package fit.asta.health.scheduler.view.bindingadapters

import android.util.Log
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import fit.asta.health.R
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Wk
import fit.asta.health.scheduler.viewmodel.AlarmSettingViewModel

class BottomSheetsBindingAdapter {
    companion object {

        @BindingAdapter(
            value = ["android:alarmLabelInputOkButtonListenerViewModel", "android:alarmLabelInputOkButtonListenerInput"],
            requireAll = true
        )
        @JvmStatic
        fun alarmLabelInputOkButtonListener(
            okButton: MaterialButton,
            alarmSettingViewModel: AlarmSettingViewModel,
            alarmLabelInput: TextInputEditText
        ) {
            okButton.setOnClickListener {
                alarmSettingViewModel.setAlarmLabelInViewModel(
                    alarmLabelInput.text.toString().trim()
                )
                Log.d(
                    "TAGTAGTAG",
                    "alarmLabelInputOkButtonListener: ${alarmSettingViewModel.alarmLabel.value} "
                )
                alarmSettingViewModel.setIsLabelBottomSheetVisible(true)
            }
        }

        @BindingAdapter(
            value = ["android:alarmDescriptionInputOkButtonListenerViewModel", "android:alarmDescriptionInputOkButtonListenerInput"],
            requireAll = true
        )
        @JvmStatic
        fun alarmDescriptionInputOkButtonListener(
            okButton: MaterialButton,
            alarmSettingViewModel: AlarmSettingViewModel,
            alarmInput: TextInputEditText
        ) {
            okButton.setOnClickListener {
                alarmSettingViewModel.setAlarmDescriptionInViewModel(
                    alarmInput.text.toString().trim()
                )
                alarmSettingViewModel.setIsDescriptionBottomSheetVisible(true)
            }
        }

        @BindingAdapter(
            value = ["android:alarmReminderModeOkButtonListenerViewModel", "android:alarmReminderOkButtonListenerInput", "android:alarmReminderOkButtonListenerItem"],
            requireAll = true
        )
        @JvmStatic
        fun alarmReminderModeOkButtonListener(
            okButton: MaterialButton,
            alarmSettingViewModel: AlarmSettingViewModel,
            alarmInput: RadioGroup,
            alarmEntity: AlarmEntity?
        ) {
            if (alarmEntity != null) {
                if (alarmEntity.mode == "Notification") {
                    alarmInput.check(R.id.choiceNotification)
                } else {
                    alarmInput.check(R.id.choiceSplash)
                }
            } else {
                if (alarmInput.tag.toString() == "Notification") {
                    alarmInput.check(R.id.choiceNotification)
                } else {
                    alarmInput.check(R.id.choiceSplash)
                }
            }
            okButton.setOnClickListener {
                if (alarmInput.checkedRadioButtonId == R.id.choiceNotification) {
                    alarmSettingViewModel.setAlarmReminderChoice("Notification")
                    alarmInput.tag = "Notification"
                } else {
                    alarmSettingViewModel.setAlarmReminderChoice("Splash")
                    alarmInput.tag = "Splash"
                }
                alarmSettingViewModel.setIsReminderModeBottomSheetVisible(true)
            }
        }

        @BindingAdapter(
            value = ["android:alarmVibrationOkButtonListenerViewModel", "android:alarmVibrationOkButtonListenerInput", "android:alarmVibrationOkButtonListenerItem"],
            requireAll = true
        )
        @JvmStatic
        fun alarmVibrationOkButtonListener(
            okButton: MaterialButton,
            alarmSettingViewModel: AlarmSettingViewModel,
            slider: Slider,
            alarmEntity: AlarmEntity?
        ) {
            if (alarmEntity != null) {
                slider.value = alarmEntity.vibration.percentage.toFloat()
            } else {
                slider.value = slider.tag.toString().toFloat()
            }
            okButton.setOnClickListener {
                alarmSettingViewModel.setVibrationPoint(slider.value.toInt().toString())
                alarmSettingViewModel.setIsVibrationBottomSheetVisible(true)
            }
        }


        @BindingAdapter(
            value = ["android:alarmWeekOkButtonListenerViewModel", "android:mondayCheckBox", "android:tuesdayCheckBox", "android:wednesdayCheckBox", "android:thursdayCheckBox", "android:fridayCheckBox", "android:saturdayCheckBox", "android:sundayCheckBox", "android:alarmWeekButtonInputItem"],
            requireAll = true
        )
        @JvmStatic
        fun alarmWeekOkButtonListener(
            okButton: MaterialButton,
            alarmSettingViewModel: AlarmSettingViewModel,
            monday: CheckBox,
            tuesday: CheckBox,
            wednesday: CheckBox,
            thursday: CheckBox,
            friday: CheckBox,
            saturday: CheckBox,
            sunday: CheckBox,
            alarmEntity: AlarmEntity?
        ) {
            if (alarmEntity != null) {
                monday.isChecked = alarmEntity.week.monday
                tuesday.isChecked = alarmEntity.week.tuesday
                wednesday.isChecked = alarmEntity.week.wednesday
                thursday.isChecked = alarmEntity.week.thursday
                friday.isChecked = alarmEntity.week.friday
                saturday.isChecked = alarmEntity.week.saturday
                sunday.isChecked = alarmEntity.week.sunday
            } else {
                Log.d(
                    "TAGTAGTAG",
                    "alarmWeekOkButtonListener: ${monday.tag.toString().toBoolean()}"
                )
                monday.isChecked = monday.tag.toString().toBoolean()
                tuesday.isChecked = tuesday.tag.toString().toBoolean()
                wednesday.isChecked = wednesday.tag.toString().toBoolean()
                thursday.isChecked = thursday.tag.toString().toBoolean()
                friday.isChecked = friday.tag.toString().toBoolean()
                saturday.isChecked = saturday.tag.toString().toBoolean()
                sunday.isChecked = sunday.tag.toString().toBoolean()
            }
            okButton.setOnClickListener {
                if (monday.isChecked || tuesday.isChecked || wednesday.isChecked || thursday.isChecked || friday.isChecked || saturday.isChecked || sunday.isChecked) {
                    val wk = Wk(
                        monday = monday.isChecked,
                        tuesday = tuesday.isChecked,
                        wednesday = wednesday.isChecked,
                        thursday = thursday.isChecked,
                        friday = friday.isChecked,
                        saturday = saturday.isChecked,
                        sunday = sunday.isChecked,
                        recurring = true
                    )
                    alarmSettingViewModel.setAlarmWeekInViewModel(
                        wk
                    )
                    alarmEntity!!.week = wk
                } else {
                    val wk = Wk(
                        monday = monday.isChecked,
                        tuesday = tuesday.isChecked,
                        wednesday = wednesday.isChecked,
                        thursday = thursday.isChecked,
                        friday = friday.isChecked,
                        saturday = saturday.isChecked,
                        sunday = sunday.isChecked,
                        recurring = false
                    )
                    alarmSettingViewModel.setAlarmWeekInViewModel(
                        wk
                    )
                    alarmEntity!!.week = wk
                }
                monday.tag = monday.isChecked.toString()
                tuesday.tag = tuesday.isChecked.toString()
                wednesday.tag = wednesday.isChecked.toString()
                thursday.tag = thursday.isChecked.toString()
                friday.tag = friday.isChecked.toString()
                saturday.tag = saturday.isChecked.toString()
                sunday.tag = sunday.isChecked.toString()
                alarmSettingViewModel.setIsWeekBottomSheetVisible(true)
            }
        }
    }
}