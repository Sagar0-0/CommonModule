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
import fit.asta.health.databinding.LayoutDialogNumberPickerBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.view.interfaces.TimeActivityBottomSheetListener
import fit.asta.health.scheduler.viewmodel.TimeSettingViewModel

@AndroidEntryPoint
class RepeatTimeBottomSheet(
    private var bottomSheetListener: TimeActivityBottomSheetListener
) : BottomSheetDialogFragment() {

    private var _binding: LayoutDialogNumberPickerBinding? = null
    private lateinit var timeSettingViewModel: TimeSettingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    companion object {
        const val TAG = "RepeatTimeBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutDialogNumberPickerBinding.inflate(inflater, container, false)
        timeSettingViewModel = ViewModelProvider(this)[TimeSettingViewModel::class.java]
        _binding?.choice = "repeat"

        if (arguments?.getParcelable<AlarmEntity>("alarmItem") != null) {
            val alarmEntity = arguments?.getParcelable<AlarmEntity>("alarmItem")
            _binding?.durationUnitPicker?.value =
                if (alarmEntity!!.interval.repeatableInterval.unit.equals("Hour") == true) {
                    setRangeForSpinner(false)
                    2
                } else {
                    setRangeForSpinner(true)
                    1
                }
            _binding?.durationPicker?.value =
                alarmEntity.interval.repeatableInterval.time
        }

        _binding?.viewModel = timeSettingViewModel
        _binding?.cancelButton?.setOnClickListener { dismiss() }

        val data = arrayOf("Minute", "Hour")
        _binding?.durationUnitPicker?.minValue = 1
        _binding?.durationUnitPicker?.maxValue = data.size
        _binding?.durationUnitPicker?.displayedValues = data
        _binding?.durationUnitPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            when (newVal) {
                1 -> setRangeForSpinner(true)
                2 -> setRangeForSpinner(false)
            }
        }
        timeSettingViewModel.isRepeatBottomSheetVisible.observe(this) {
            this.dismiss()
        }
        timeSettingViewModel.repeat.observe(this) {
            Log.d(TAG, "onCreateView::: $it")
            bottomSheetListener.setRepeatTime(it)
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

    private fun setRangeForSpinner(isMinute: Boolean) {
        if (isMinute) {
            _binding!!.durationPicker.minValue = 10
            _binding!!.durationPicker.maxValue = 60
        } else {
            _binding!!.durationPicker.minValue = 1
            _binding!!.durationPicker.maxValue = 6
        }
    }
}