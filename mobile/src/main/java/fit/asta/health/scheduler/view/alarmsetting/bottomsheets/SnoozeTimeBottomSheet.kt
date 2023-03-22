package fit.asta.health.scheduler.view.alarmsetting.bottomsheets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.SchedulerLayoutDialogNumberPickerBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.view.interfaces.TimeActivityBottomSheetListener
import fit.asta.health.scheduler.viewmodel.TimeSettingViewModel

@AndroidEntryPoint
class SnoozeTimeBottomSheet(
    private var bottomSheetListener: TimeActivityBottomSheetListener
) : BottomSheetDialogFragment() {

    private var _binding: SchedulerLayoutDialogNumberPickerBinding? = null
    private lateinit var timeSettingViewModel: TimeSettingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    companion object {
        const val TAG = "SnoozeTimeBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SchedulerLayoutDialogNumberPickerBinding.inflate(inflater, container, false)
        timeSettingViewModel = ViewModelProvider(this)[TimeSettingViewModel::class.java]
        _binding?.choice = "snooze"

        if (arguments?.getParcelable<AlarmEntity>("alarmItem") != null) {
            val alarmEntity = arguments?.getParcelable<AlarmEntity>("alarmItem")
            _binding?.durationPicker?.value = alarmEntity?.interval?.snoozeTime!!
        }

        _binding?.viewModel = timeSettingViewModel
        _binding?.cancelButton?.setOnClickListener { dismiss() }

        val data = arrayOf("Minute")
        _binding?.durationUnitPicker?.minValue = 1
        _binding?.durationUnitPicker?.maxValue = data.size
        _binding?.durationUnitPicker?.displayedValues = data
        timeSettingViewModel.isSnoozeBottomSheetVisible.observe(this) {
            this.dismiss()
        }
        timeSettingViewModel.snoozeTime.observe(this) {
            Log.d(TAG, "onCreateView::: $it")
            bottomSheetListener.setSnoozeTime(it)
        }
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}