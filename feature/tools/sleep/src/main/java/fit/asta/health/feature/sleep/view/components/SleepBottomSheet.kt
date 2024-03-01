package fit.asta.health.feature.sleep.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.data.sleep.model.db.SleepData
import fit.asta.health.data.sleep.model.network.common.Prc
import fit.asta.health.data.sleep.utils.SleepNetworkCall
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.CardItem
import fit.asta.health.feature.sleep.view.navigation.SleepToolNavRoutes
import fit.asta.health.resources.drawables.R

/**
 * This is the UI which will be displayed when the Bottom Sheet is expanded
 *
 * @param scaffoldState this defines the State of the Scaffold
 * @param navController This is the navigation Controller which helps to switch to a different Screen
 * @param bottomSheetData This variable contains the bottom sheet Data which needs to be shown in UI
 * @param selectedDisturbances This variable contains the selected Disturbances
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepBottomSheet(
    animatedState : Boolean,
    navController: NavController,
    bottomSheetData: List<Prc>?,
    selectedDisturbances: Prc?,
    timerStatus: SleepNetworkCall<List<SleepData>>,
    onStartStopClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Showing the Row of the Sleep Disturbance
        SleepDisturbanceRowUI(
            navController = navController,
            selectedDisturbances = selectedDisturbances
        )

        AnimatedVisibility(visible = animatedState) {
            if (bottomSheetData != null) {
                SleepBottomSheetOptionUI(
                    navController = navController,
                    bottomSheetData = bottomSheetData
                )
            }
        }

        SleepBottomSheetButtonRow(timerStatus = timerStatus.data) {
            onStartStopClick()
        }

//        AnimatedVisibility(visible = !animatedState) {
//            Spacer(modifier = Modifier.height(200.dp))
//        }
    }
}


@Composable
private fun SleepBottomSheetOptionUI(
    navController: NavController,
    bottomSheetData: List<Prc>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            columns = GridCells.Fixed(2)
        ) {

            item {
                CardItem(
                    name = "Music",
                    type = "calm",
                    id = R.drawable.baseline_music_note_24
                ) {
                    // TODO
                }
            }

            items(bottomSheetData.size) {

                // Current Item and Current Icon
                val currentItem = bottomSheetData[it]
                val currentIcon = when (currentItem.ttl) {
                    "goal" -> R.drawable.goal
                    "factors" -> R.drawable.sleep_factors
                    "Jet Lag" -> R.drawable.jet_plane
                    "target" -> R.drawable.targets
                    else -> R.drawable.goal
                }

                val type = if (!currentItem.values.isNullOrEmpty())
                    currentItem.values!!.map { valueList ->
                        valueList.value
                    }.toString().filterNot { ch ->
                        ch == '[' || ch == ']'
                    }
                else
                    "None"

                CardItem(
                    name = currentItem.ttl,
                    type = type,
                    id = currentIcon
                ) {
                    when (currentItem.ttl) {

                        "goal" -> {
                            navController.navigate(
                                SleepToolNavRoutes.SleepGoalsRoute.routes
                            )
                        }

                        "factors" -> {
                            navController.navigate(
                                SleepToolNavRoutes.SleepFactorRoute.routes
                            )
                        }

                        "disturbance" -> {
                            navController.navigate(
                                SleepToolNavRoutes.SleepDisturbanceRoute.routes
                            )
                        }

                        "Jet Lag" -> {
                            navController.navigate(
                                SleepToolNavRoutes.SleepJetLagTipsRoute.routes
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
        WeatherCard()
    }
}


@Composable
private fun SleepDisturbanceRowUI(
    navController: NavController,
    selectedDisturbances: Prc?
) {

    // Contains all the circular Buttons
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Checking if the selected Disturbance is empty or not
        selectedDisturbances?.values?.let { disturbanceList ->
            items(disturbanceList.size) {

                // Current Items
                val currentItem = selectedDisturbances.values!![it].name

                val currentIcon = when (currentItem) {
                    "Dream" -> R.drawable.dreamcatcher
                    "Kids" -> R.drawable.kids
                    "Love" -> R.drawable.favorite
                    "Water" -> R.drawable.water_glass
                    "Toilet" -> R.drawable.toilet
                    else -> R.drawable.dreamcatcher
                }

                // Drawing the Circular Image with text
                CircularImageAndText(
                    text = currentItem,
                    image = currentIcon
                )
            }
        }

        // More Options comes here
        item {
            CircularImageAndText(
                text = "More"
            ) {
                navController.navigate(SleepToolNavRoutes.SleepDisturbanceRoute.routes)
            }
        }
    }
}


@Composable
private fun SleepBottomSheetButtonRow(
    timerStatus: List<SleepData>?,
    onStartStopClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        ButtonWithColor(
            modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
        ) {
            // TODO
        }

        ButtonWithColor(
            modifier = Modifier.weight(0.5f),
            color = if (timerStatus.isNullOrEmpty()) Color.Green else Color.Red,
            text = if (timerStatus.isNullOrEmpty()) "Start" else "Stop"
        ) {
            onStartStopClick()
        }
    }
}