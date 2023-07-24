package fit.asta.health.player.jetpack_audio

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.player.jetpack_audio.presentation.screens.Screens
import fit.asta.health.player.jetpack_audio.presentation.screens.home.HomeScreen
import fit.asta.health.player.jetpack_audio.presentation.screens.player.PlayerScreen
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LOULATheme
import fit.asta.health.player.jetpack_audio.presentation.utils.DevicePosture

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class AudioPlayerActivity : ComponentActivity() {

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, AudioPlayerActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    @SuppressLint(
        "UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Flow of [DevicePosture] that emits every time there's a change in the windowLayoutInfo
         */

        setContent {
            LOULATheme {
                val navController = rememberAnimatedNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
                    AnimatedNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = Screens.Home.route
                    ) {
                        composable(route = Screens.Home.route) {
                            HomeScreen(navigateToPlayer = {
                                navController.navigate(
                                    Screens.Player.route
                                )
                            })
                        }

                        composable(
                            route = Screens.Player.route
                        ) {
                            PlayerScreen(onBackPressed = { navController.popBackStack() },
                                addToPlayList = { /*TODO*/ },
                                more = { /*TODO*/ })
                        }
                    }
                }
            }
        }
    }
}
