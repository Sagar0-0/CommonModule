package fit.asta.health.tools.sleep.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.common.ui.components.ProgressBarInt
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.sleep.viewmodel.SleepToolViewModel

@Composable
fun SleepHomeScreen(
    navController: NavController,
    sleepToolViewModel: SleepToolViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = 8f,
                    progress = 5f,
                    name = "Recommended",
                    postfix = "Hrs"
                )
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = 7f,
                    progress = 4f,
                    name = "Goal",
                    postfix = "Hrs"
                )
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = 6f,
                    progress = 4f,
                    name = "Remaining",
                    postfix = "Hrs"
                )
            }
        }
    }
}