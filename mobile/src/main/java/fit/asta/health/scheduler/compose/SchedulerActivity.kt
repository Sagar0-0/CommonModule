package fit.asta.health.scheduler.compose

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.navigation.SchedulerNavigation
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SchedulerActivity : AppCompatActivity() {

    private var alarmEntity: AlarmEntity? = null
    private lateinit var schedulerViewModel: SchedulerViewModel

    companion object {

        fun launch(context: Context) {
            Log.d("sj", "launch: Start ${context}")
            val intent = Intent(context, SchedulerActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    SchedulerNavigation(
                        navController = rememberNavController(),
                        hiltViewModel<SchedulerViewModel>()
                    )
                }
            }
        }
        schedulerViewModel = ViewModelProvider(this)[SchedulerViewModel::class.java]

        if (intent.getParcelableExtra<AlarmEntity>("alarmItem") != null) {
            alarmEntity = intent.getParcelableExtra("alarmItem")
            schedulerViewModel.setAlarmEntityIntent(alarmEntity)
        }
    }

}

@Composable
fun MyApp(context: @Composable () -> Unit) {
    AppTheme {
        context()
    }
}