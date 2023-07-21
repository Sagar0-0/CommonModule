package fit.asta.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.AuthActivity
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.NetworkConnectivity
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.main.ui.MainActivity
import fit.asta.health.onboarding.OnBoardingScreenActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class SplashScreen : ComponentActivity() {

    private lateinit var networkConnectivity: NetworkConnectivity
    private val isConnected = mutableStateOf(true)
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showSplashScreen()

        startMain()
    }

    private fun showSplashScreen() {
        registerConnectivityReceiver()
        setContent {
            AppTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                            .paint(
                                painter = painterResource(id = R.drawable.splash_screen),
                                alignment = Alignment.Center
                            )
                    ) {
                        if (!isConnected.value) {
                            val msg = stringResource(id = R.string.OFFLINE_STATUS)
                            LaunchedEffect(Unit) {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = msg,
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    private fun startMain() {
        if (!PrefUtils.getOnboardingShownStatus(this)) {
            OnBoardingScreenActivity.launch(this)
        } else {
            if (!authViewModel.isAuthenticated()) {
                AuthActivity.launch(this)
            } else {
                MainActivity.launch(this)
            }
        }
    }

    private fun registerConnectivityReceiver() {
        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this) { status ->
            isConnected.value = status
        }
    }
}