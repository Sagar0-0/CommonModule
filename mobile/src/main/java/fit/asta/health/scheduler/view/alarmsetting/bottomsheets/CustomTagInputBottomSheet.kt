package fit.asta.health.scheduler.view.alarmsetting.bottomsheets

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.SchedulerFragmentAddCustomTagBinding
import fit.asta.health.scheduler.viewmodel.AlarmSettingViewModel


@AndroidEntryPoint
class CustomTagInputBottomSheet : BottomSheetDialogFragment() {

    private var _binding: SchedulerFragmentAddCustomTagBinding? = null
    private lateinit var alarmSettingViewModel: AlarmSettingViewModel
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                Log.d(TAG, "onCreateView: $uri")
                // Use the uri to load the image
            }
        }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    companion object {
        const val TAG = "CustomTagInputBS"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SchedulerFragmentAddCustomTagBinding.inflate(inflater, container, false)
        alarmSettingViewModel = ViewModelProvider(this).get(AlarmSettingViewModel::class.java)

//        if (arguments?.getParcelable<AlarmEntity>("alarmItem") != null) {
//            val alarmEntity = arguments?.getParcelable<AlarmEntity>("alarmItem")
//            _binding?.inputAlarmDescription?.setText(alarmEntity?.alarmDescription)
//        }
//
        _binding?.alarmSettingViewModel = alarmSettingViewModel
        _binding?.inputCustomTagNameDescription?.requestFocus()
        _binding?.cancelButton?.setOnClickListener { dismiss() }

        if (dialog is BottomSheetDialog) {
            (dialog as BottomSheetDialog).behavior.skipCollapsed = true
            (dialog as BottomSheetDialog).behavior.state = STATE_EXPANDED
        }

        _binding?.addTagImageButton?.setOnClickListener {
            launcher.launch(
                ImagePicker.with(requireActivity())
                    //...
                    .galleryOnly() // or galleryOnly()
                    .createIntent()
            )
        }

        alarmSettingViewModel.isCustomTagBottomSheetVisible.observe(this) {
            this.dismiss()
        }

//        alarmSettingViewModel.alarmCustomTagName.observe(this) {
//            bottomSheetInterface.onAlarmDescriptionListener(it)
//        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}