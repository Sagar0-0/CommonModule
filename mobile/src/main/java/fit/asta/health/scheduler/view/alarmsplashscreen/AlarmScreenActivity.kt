package fit.asta.health.scheduler.view.alarmsplashscreen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.ActivityAlarmScreenBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.viewmodel.AlarmViewModel

@AndroidEntryPoint
class AlarmScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityAlarmScreenBinding
    lateinit var viewModel: AlarmViewModel
    var alarmEntity: AlarmEntity? = null
    var variantInterval: Stat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants.changeStatusBarColor(R.color.black, window, this)
        Constants.setShowWhenLocked(window, this)

        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        val bundle = intent.getBundleExtra(Constants.BUNDLE_ALARM_OBJECT)
        if (bundle != null) alarmEntity =
            bundle.getSerializable(Constants.ARG_ALARM_OBJET) as AlarmEntity

        val bundleForVariantInterval =
            intent.getBundleExtra(Constants.BUNDLE_VARIANT_INTERVAL_OBJECT)
        if (bundleForVariantInterval != null) {
            alarmEntity =
                bundleForVariantInterval.getSerializable(Constants.ARG_VARIANT_INTERVAL_ALARM_OBJECT) as AlarmEntity?
            variantInterval =
                bundleForVariantInterval.getParcelable(Constants.ARG_VARIANT_INTERVAL_OBJECT) as Stat?

        }

        binding.viewModel = viewModel
        binding.alarmItem = alarmEntity
        binding.variantIntervalItem = variantInterval
        Log.d("TAGTAGTAG", "onCreate: $alarmEntity")
    }
}