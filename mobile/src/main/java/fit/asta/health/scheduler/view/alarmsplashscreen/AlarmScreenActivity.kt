package fit.asta.health.scheduler.view.alarmsplashscreen

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.scheduler.compose.screen.alarmscreen.AlarmScreen
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.util.SerializableAndParcelable.parcelable
import fit.asta.health.scheduler.util.SerializableAndParcelable.serializable
import fit.asta.health.scheduler.viewmodel.AlarmScreenViewModel

@AndroidEntryPoint
class AlarmScreenActivity : AppCompatActivity() {

//    lateinit var binding: SchedulerAlarmScreenActivityBinding
//    lateinit var viewModel: AlarmViewModel
    lateinit var alarmScreenViewModel: AlarmScreenViewModel
    var alarmEntity: AlarmEntity? = null
    var variantInterval: Stat? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SchedulerAlarmScreenActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        Constants.changeStatusBarColor(R.color.black, window, this)
//        Constants.setShowWhenLocked(window, this)
//
//        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
//
//        val bundle = intent.getBundleExtra(Constants.BUNDLE_ALARM_OBJECT)
//        if (bundle != null) alarmEntity =
//            bundle.getSerializable(Constants.ARG_ALARM_OBJET) as AlarmEntity
//
//        val bundleForVariantInterval =
//            intent.getBundleExtra(Constants.BUNDLE_VARIANT_INTERVAL_OBJECT)
//        if (bundleForVariantInterval != null) {
//            alarmEntity =
//                bundleForVariantInterval.getSerializable(Constants.ARG_VARIANT_INTERVAL_ALARM_OBJECT) as AlarmEntity?
//            variantInterval =
//                bundleForVariantInterval.getParcelable(Constants.ARG_VARIANT_INTERVAL_OBJECT) as Stat?
//
//        }
//
//        binding.viewModel = viewModel
//        binding.alarmItem = alarmEntity
//        binding.variantIntervalItem = variantInterval
//        Log.d("TAGTAGTAG", "onCreate: $alarmEntity")
//    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Constants.changeStatusBarColor(R.color.black, window, this)
        Constants.setShowWhenLocked(window, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Constants.changeStatusBarColor(R.color.black, window, this)
        Constants.setShowWhenLocked(window, this)
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val alarmScreenViewModel=hiltViewModel<AlarmScreenViewModel>()
                val uiState=alarmScreenViewModel.alarmUiState.value
               AlarmScreen(uiState = uiState, event = alarmScreenViewModel::event )
            }
        }
        alarmScreenViewModel = ViewModelProvider(this)[AlarmScreenViewModel::class.java]

        val bundle = intent.getBundleExtra(Constants.BUNDLE_ALARM_OBJECT)
        val bundleForVariantInterval = intent.getBundleExtra(Constants.BUNDLE_VARIANT_INTERVAL_OBJECT)
        if (bundleForVariantInterval != null) {
            alarmEntity = bundleForVariantInterval.serializable(Constants.ARG_VARIANT_INTERVAL_ALARM_OBJECT)
            variantInterval = bundleForVariantInterval.parcelable(Constants.ARG_VARIANT_INTERVAL_OBJECT)
        }
        if (bundle != null) {
                alarmEntity = bundle.serializable(Constants.ARG_ALARM_OBJET)
        }
            Log.d("TAGTAG", " alarm screen activity onCreate: ")
            alarmScreenViewModel.setAlarmAndVariant(alarmEntity,variantInterval)
    }

}

@Composable
fun MyApp(context: @Composable () -> Unit) {
    AppTheme {
        context()
    }
}
