package fit.asta.health.tools.walking.view

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.tools.walking.nav.StepsCounterNavigation
import fit.asta.health.tools.walking.view.component.WalkingBottomSheet
import fit.asta.health.tools.walking.view.home.HomeUIState
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import fit.asta.health.tools.walking.work.CountStepsService
import fit.asta.health.tools.walking.work.StepWorker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
@OptIn(ExperimentalCoroutinesApi::class)
class WalkingActivity : ComponentActivity() {

    private lateinit var mService: CountStepsService
    private lateinit var stepCountFlow: StateFlow<HomeUIState>
    private val viewModel: WalkingViewModel by viewModels()


    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as CountStepsService.LocalBinder
            mService = binder.getService()
            stepCountFlow = binder.getStepCountFlow()
            lifecycleScope.launch {
                stepCountFlow.collect { stepCount ->
                    viewModel.changeUi(stepCount)
                }
            }

        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, WalkingActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val PHYSICAL_ACTIVITY = 123
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            //ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), PHYSICAL_ACTIVITY
                )
            }
        }

        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                StepsCounterNavigation(
                    navController = rememberNavController(),
                    hiltViewModel<WalkingViewModel>()
                )
            }
        }
//        setupWorker(this)
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService.
        Intent(this, CountStepsService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }
}

@Composable
fun MyApp(context: @Composable () -> Unit) {
    AppTheme {
        context()
    }
}

@Preview
@Composable
fun WalkingToolHomeScreen() {

    AppScaffold(topBar = {
        AppTopBarWithHelp(
            title = "Step Counter",
            onBack = { /*TODO*/ },
            onHelp = { /*TODO*/ }
        )
    }, content = {
        WalkingBottomSheet(paddingValues = it)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(Modifier.fillMaxSize()) {
        WalkingToolHomeScreen()
    }

}

fun setupWorker(context: Context) {
    // Calculate the time until 11 PM
    val currentTime = System.currentTimeMillis()
    val elevenPm = LocalTime.of(23, 0)
    val elevenPmMillis = LocalDateTime.of(LocalDate.now(), elevenPm).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val initialDelay = elevenPmMillis - currentTime
    val constraint = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val workRequest = OneTimeWorkRequestBuilder<StepWorker>()
        .setConstraints(constraint)
        .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
        .setBackoffCriteria(BackoffPolicy.LINEAR, 30, TimeUnit.SECONDS)
        .build()
    WorkManager.getInstance(context).enqueueUniqueWork(
        "worker_for_upload_data", ExistingWorkPolicy.REPLACE, workRequest
    )
}
