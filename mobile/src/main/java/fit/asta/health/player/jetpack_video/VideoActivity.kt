package fit.asta.health.player.jetpack_video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.player.jetpack_video.video.FullscreenToggle
import fit.asta.health.player.jetpack_video.viewmodle.PlayerViewModel

@AndroidEntryPoint
class VideoActivity : ComponentActivity() {

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, VideoActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
                    systemUiController.setNavigationBarColor(Color.Transparent, darkIcons = true)
                }
                val navController = rememberNavController()
                val viewModel = hiltViewModel<PlayerViewModel>()
                NavHost(navController = navController, startDestination = "fullscreen-toggle") {
                    composable("fullscreen-toggle") {
                        val uiState by viewModel.uiState
                        val videoList by viewModel.videoList.collectAsState()
                        FullscreenToggle(
                            uiState = uiState,
                            videos = videoList,
                            player = viewModel.player(),
                            event = viewModel::event,
                            onBack = { navController.popBackStack() }
                        )
                    }

                }
            }
        }
    }
}
