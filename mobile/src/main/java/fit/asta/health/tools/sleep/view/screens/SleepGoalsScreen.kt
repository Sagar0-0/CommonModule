package fit.asta.health.tools.sleep.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fit.asta.health.tools.sleep.viewmodel.SleepToolViewModel

@Composable
fun SleepGoalsScreen(
    navController: NavController,
    sleepToolViewModel: SleepToolViewModel
) {
    Text(text = "Sleep Goals Screen")
}