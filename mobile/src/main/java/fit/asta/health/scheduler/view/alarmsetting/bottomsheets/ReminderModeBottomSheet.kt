package fit.asta.health.scheduler.view.alarmsetting.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.SchedulerFragmentReminderModeBottomSheetBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.view.interfaces.BottomSheetInterface
import fit.asta.health.scheduler.viewmodel.AlarmSettingViewModel

@AndroidEntryPoint
class ReminderModeBottomSheet(
    private var bottomSheetInterface: BottomSheetInterface
) : BottomSheetDialogFragment() {

    private var _binding: SchedulerFragmentReminderModeBottomSheetBinding? = null
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
        _binding = SchedulerFragmentReminderModeBottomSheetBinding.inflate(inflater, container, false)
        alarmSettingViewModel = ViewModelProvider(this).get(AlarmSettingViewModel::class.java)
        _binding?.alarmSettingViewModel = alarmSettingViewModel

        if (arguments?.getParcelable<AlarmEntity>("alarmItem") != null) {
            val alarmEntity = arguments?.getParcelable<AlarmEntity>("alarmItem")
            _binding?.alarmItem = alarmEntity
        }

        _binding?.cancelButton?.setOnClickListener { dismiss() }
        alarmSettingViewModel.isReminderModeBottomSheetVisible.observe(this) {
            this.dismiss()
        }
        alarmSettingViewModel.alarmRemainderChoice.observe(this) {
            bottomSheetInterface.onAlarmReminderModeListener(it)
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