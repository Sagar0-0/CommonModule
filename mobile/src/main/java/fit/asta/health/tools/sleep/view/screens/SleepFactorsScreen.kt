package fit.asta.health.tools.sleep.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.tools.sleep.model.network.common.Prc
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.utils.SleepNetworkCall
import fit.asta.health.tools.sleep.view.components.SleepCardItems

@Composable
fun SleepFactorsScreen(
    navController: NavController,
    sleepFactorState: SleepNetworkCall<SleepDisturbanceResponse>,
    selectedFactors: Prc?,
    loadDataFunction: () -> Unit,
    onFactorSelected: (String, String) -> Unit
) {

    val toolType = "factors"
    val context = LocalContext.current

    // Fetching the Data from the Server
    LaunchedEffect(Unit) {
        loadDataFunction()
    }

    // Handling the State of the Api Call
    when (sleepFactorState) {

        // Initialized State
        is SleepNetworkCall.Initialized -> {
            loadDataFunction()
        }

        // loading State
        is SleepNetworkCall.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Success State
        is SleepNetworkCall.Success -> {

            // List of Items or we can say factors data
            val itemList = sleepFactorState.data?.sleepData

            // Checking if the itemList is null or not
            if (itemList != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                        Text(
                            text = "Tap to add/remove the reasons for sleep factors",

                            fontSize = 20.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily.SansSerif
                        )
                    }

                    item {

                        // Checking if we have this value already selected or not
                        val sleepValue = selectedFactors?.values?.find { value ->
                            value.name == "Sleep Factors"
                        }

                        // Setting the Modifier as Green if the User has already selected it
                        val modifier = if (sleepValue != null)
                            Modifier.background(Color.Green.copy(alpha = .25f))
                        else
                            Modifier

                        SleepCardItems(
                            modifier = modifier,
                            textToShow = "Sleep Factors"
                        ) {
                            onFactorSelected(toolType, "Sleep Factors")
                            navController.popBackStack()
                        }
                    }

                    itemList.propertyData?.let { list ->
                        items(list.size) {

                            // Checking if we have this value already selected or not
                            val sleepValue = selectedFactors?.values?.find { value ->
                                value.name == list[it].name
                            }

                            // Setting the Modifier as Green if the User has already selected it
                            val modifier = if (sleepValue != null)
                                Modifier.background(Color.Green.copy(alpha = .25f))
                            else
                                Modifier

                            val icon = when (list[it].name) {
                                "Sleep Factors" -> R.drawable.sleep_factors
                                "Alcohol" -> R.drawable.alcohol
                                "Jet Lag" -> R.drawable.jet_plane
                                "Caffine" -> R.drawable.coffee
                                "Smoking" -> R.drawable.smoking
                                "Pain" -> R.drawable.pain
                                "Workout" -> R.drawable.workout
                                else -> R.drawable.sleep_factors
                            }

                            // Showing the Card UI
                            SleepCardItems(
                                modifier = modifier,
                                icon = icon,
                                textToShow = list[it].name
                            ) {
                                onFactorSelected(toolType, list[it].name)
                                navController.popBackStack()
                            }
                        }
                    }

                    itemList.customPropertyData?.let { list ->
                        items(list.size) {
                            SleepCardItems(textToShow = list[it].name) {
                                onFactorSelected(toolType, list[it].name)
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }

        // failure State
        is SleepNetworkCall.Failure -> {
            Toast.makeText(
                context,
                sleepFactorState.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}