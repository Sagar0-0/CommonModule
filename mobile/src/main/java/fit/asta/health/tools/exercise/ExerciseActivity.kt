package fit.asta.health.tools.exercise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.tools.exercise.nav.DanceNavigation
import fit.asta.health.tools.exercise.viewmodel.ExerciseViewModel
@AndroidEntryPoint
class ExerciseActivity : ComponentActivity() {

    companion object {

        fun launch(context: Context, activity: String) {
            val intent = Intent(context, ExerciseActivity::class.java)
            intent.apply {
                putExtra("activity", activity)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val viewModel= hiltViewModel<ExerciseViewModel>()
                viewModel.setScreen(value =intent.extras?.getString("activity") ?: "dance" )
                DanceNavigation(
                    navController = rememberNavController(),
                    activity = intent.extras?.getString("activity") ?: "dance",
                    viewModel = viewModel
                )
            }
        }
    }
}
