package fit.asta.health

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.lifecycle.Observer
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.common.utils.NetworkConnectivity


class SplashScreen : AppCompatActivity() {

    private lateinit var networkConnectivity: NetworkConnectivity
    private val isConnected = mutableStateOf(true)

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

        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun registerConnectivityReceiver() {
        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this, Observer { status ->
            isConnected.value = status
        })
    }
}