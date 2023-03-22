package fit.asta.health.scheduler.view.alarmsetting.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.SchedulerFragmentRepeatInputBottomSheetBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.view.interfaces.BottomSheetInterface
import fit.asta.health.scheduler.viewmodel.AlarmSettingViewModel

@AndroidEntryPoint
class RepeatBottomSheet(
    private var bottomSheetInterface: BottomSheetInterface
) : BottomSheetDialogFragment() {

    private var _binding: SchedulerFragmentRepeatInputBottomSheetBinding? = null
    private lateinit var alarmSettingViewModel: AlarmSettingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    companion object {
        const val TAG = "RepeatBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SchedulerFragmentRepeatInputBottomSheetBinding.inflate(inflater, container, false)
        alarmSettingViewModel = ViewModelProvider(this).get(AlarmSettingViewModel::class.java)
        _binding?.alarmSettingViewModel = alarmSettingViewModel

        if (arguments?.getParcelable<AlarmEntity>("alarmItem") != null) {
            val alarmEntity = arguments?.getParcelable<AlarmEntity>("alarmItem")
            _binding?.alarmItem = alarmEntity
        }

        _binding?.cancelButton?.setOnClickListener { dismiss() }
        alarmSettingViewModel.isWeekBottomSheetVisible.observe(this) {
            this.dismiss()
        }
        alarmSettingViewModel.alarmWeek.observe(this) {
            bottomSheetInterface.onAlarmWeekListener(it)
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