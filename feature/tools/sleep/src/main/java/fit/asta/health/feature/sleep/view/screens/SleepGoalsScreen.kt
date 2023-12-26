package fit.asta.health.feature.sleep.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.data.sleep.model.network.common.Prc
import fit.asta.health.data.sleep.model.network.goals.SleepGoalResponse
import fit.asta.health.data.sleep.utils.SleepNetworkCall
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.feature.sleep.view.components.SleepCardItems

@Composable
fun SleepGoalsScreen(
    navController: NavController,
    goalsList: SleepNetworkCall<SleepGoalResponse>,
    selectedGoals: Prc?,
    loadData: () -> Unit,
    onGoalSelected: (String, String) -> Unit
) {

    val toolType = "goal"

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loadData()
    }

    when (goalsList) {

        // Initialized State
        is SleepNetworkCall.Initialized -> {
            loadData()
        }

        // loading State
        is SleepNetworkCall.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppCircularProgressIndicator()
            }
        }

        // Success State
        is SleepNetworkCall.Success -> {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                goalsList.data?.data?.let { listData ->
                    items(listData.size) {

                        // Current Item
                        val currentItem = listData[it]

                        // Checking if we have this value already selected or not
                        val sleepValue = selectedGoals?.values?.find { value ->
                            value.name == currentItem.name
                        }

                        // Setting the Modifier as Green if the User has already selected it
                        val modifier = if (sleepValue != null)
                            Modifier.background(Color.Green.copy(alpha = .25f))
                        else
                            Modifier

                        // Showing the Card UI
                        SleepCardItems(
                            modifier = modifier,
                            icon = null,
                            textToShow = currentItem.name
                        ) {
                            onGoalSelected(toolType, listData[it].name)
                            navController.popBackStack()
                        }
                    }
                }
            }
        }

        // failure State
        is SleepNetworkCall.Failure -> {
            Toast.makeText(
                context,
                goalsList.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}