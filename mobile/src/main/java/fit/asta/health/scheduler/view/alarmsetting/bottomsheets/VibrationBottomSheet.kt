package fit.asta.health.scheduler.view.alarmsetting.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.FragmentVibrationSliderBottomSheetBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.view.interfaces.BottomSheetInterface
import fit.asta.health.scheduler.viewmodel.AlarmSettingViewModel

@AndroidEntryPoint
class VibrationBottomSheet(
    private var bottomSheetInterface: BottomSheetInterface
) : BottomSheetDialogFragment() {

    private var _binding: FragmentVibrationSliderBottomSheetBinding? = null
    private lateinit var alarmSettingViewModel: AlarmSettingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    companion object {
        const val TAG = "RemainderModeBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVibrationSliderBottomSheetBinding.inflate(inflater, container, false)
        alarmSettingViewModel = ViewModelProvider(this).get(AlarmSettingViewModel::class.java)
        _binding?.alarmSettingViewModel = alarmSettingViewModel
        _binding?.cancelButton?.setOnClickListener { dismiss() }
        if (arguments?.getParcelable<AlarmEntity>("alarmItem") != null) {
            val alarmEntity = arguments?.getParcelable<AlarmEntity>("alarmItem")
            _binding?.alarmItem = alarmEntity
        }
        alarmSettingViewModel.isVibrationBottomSheetVisible.observe(this) {
            this.dismiss()
        }
        alarmSettingViewModel.alarmVibrationPoint.observe(this) {
            bottomSheetInterface.onAlarmVibrationModeListener(it)
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