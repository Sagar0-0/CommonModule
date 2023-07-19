package fit.asta.health.tools.breathing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.tools.breathing.nav.BreathingNavigation

@AndroidEntryPoint
class BreathingActivity: ComponentActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, BreathingActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                BreathingNavigation(
                    navController = rememberNavController(),
                    viewModel = hiltViewModel()
                )
            }
        }
    }
}