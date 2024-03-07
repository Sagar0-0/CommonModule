package fit.asta.health.feature.scheduler.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.scheduler.ui.screen.alarmscreen.AlarmEvent
import fit.asta.health.feature.scheduler.ui.screen.alarmscreen.AlarmScreen
import fit.asta.health.feature.scheduler.ui.viewmodel.AlarmScreenViewModel
import fit.asta.health.feature.scheduler.util.Constants

@AndroidEntryPoint
class AlarmScreenActivity : AppCompatActivity() {
    private lateinit var alarmScreenViewModel: AlarmScreenViewModel
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        Constants.changeStatusBarColor(Color.BLACK, window, this)
        Constants.setShowWhenLocked(window, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
//        Constants.changeStatusBarColor(Color.BLACK, window, this)
        Constants.setShowWhenLocked(window, this)
        super.onCreate(savedInstanceState)

        //Enabling edge to edge for fullscreen theme
        //enableEdgeToEdge()

        setContent {
            AppTheme {
                val alarmScreenViewModel = hiltViewModel<AlarmScreenViewModel>()
                val uiState by alarmScreenViewModel.alarmUiState.collectAsStateWithLifecycle()
                AlarmScreen(uiState = uiState, event = { uiEvent ->
                    when (uiEvent) {
                        is AlarmEvent.onSwipedLeft -> {
                            alarmScreenViewModel.snooze(uiEvent.context)
                        }

                        is AlarmEvent.onSwipedRight -> {
                            alarmScreenViewModel.stop(uiEvent.context)
                        }
                    }
                })
            }
        }
        alarmScreenViewModel = ViewModelProvider(this)[AlarmScreenViewModel::class.java]

        val id = intent.getLongExtra("id", -1)
        alarmScreenViewModel.setAlarmUi(id)
    }

}

