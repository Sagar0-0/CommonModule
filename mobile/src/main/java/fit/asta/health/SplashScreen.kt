package fit.asta.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.AuthActivity
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.utils.NetworkConnectivity
import fit.asta.health.common.utils.PrefUtils
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
                val snackBarHostState = remember { SnackbarHostState() }
                AppScaffold(
                    snackBarHostState = snackBarHostState,
                    content = { innerPadding ->
                        OfflineSnackBar(
                            innerPaddingValues = innerPadding,
                            isConnected = isConnected,
                            snackBarHostState = snackBarHostState
                        )
                    })
            }
        }
    }

    private fun startMain() {

        if (!authViewModel.isAuthenticated()) {
            if (!PrefUtils.getOnboardingShownStatus(this)) {
                OnBoardingScreenActivity.launch(this)
            } else {
                AuthActivity.launch(this)
            }
        } else {
            MainActivity.launch(this)
        }
    }

    private fun registerConnectivityReceiver() {
        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this) { status ->
            isConnected.value = status
        }
    }
}


@Composable
fun OfflineSnackBar(
    innerPaddingValues: PaddingValues,
    isConnected: MutableState<Boolean>,
    snackBarHostState: SnackbarHostState,
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
            .paint(
                painter = painterResource(id = R.drawable.splash_screen),
                alignment = Alignment.Center
            )
    ) {
        if (!isConnected.value) {
            val msg = stringResource(id = R.string.OFFLINE_STATUS)
            LaunchedEffect(Unit) {
                snackBarHostState.showSnackbar(
                    message = msg, duration = SnackbarDuration.Indefinite
                )
            }
        }
    }
}