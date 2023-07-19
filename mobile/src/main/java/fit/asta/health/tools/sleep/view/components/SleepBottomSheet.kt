package fit.asta.health.tools.sleep.view.components

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
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.common.ui.components.ButtonWithColor
import fit.asta.health.common.ui.components.CardItem
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.sleep.model.network.common.Prc
import fit.asta.health.tools.sleep.view.navigation.SleepToolNavRoutes

/**
 * This is the UI which will be displayed when the Bottom Sheet is expanded
 *
 * @param scaffoldState this defines the State of the Scaffold
 * @param navController This is the navigation Controller which helps to switch to a different Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepBottomSheet(
    scaffoldState: SheetState,
    navController: NavController,
    bottomSheetData: List<Prc>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Practise Text
        Text(
            text = "Practise",

            fontSize = 24.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )

        // Contains all the circular Buttons
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            item {
                CircularImageAndText(
                    image = R.drawable.dreamcatcher,
                    text = "Dream"
                )
            }

            item {
                CircularImageAndText(
                    image = R.drawable.kids,
                    text = "Kids"
                )
            }

            item {
                CircularImageAndText(
                    image = R.drawable.favorite,
                    text = "Love"
                )
            }
            item {
                CircularImageAndText(
                    text = "More"
                ) {
                    navController.navigate(SleepToolNavRoutes.SleepDisturbanceRoute.routes)
                }
            }
        }

        // Contains the First two Rows
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            CardItem(
                modifier = Modifier.weight(0.5f),
                name = "Music",
                type = "calm",
                id = R.drawable.baseline_music_note_24
            ) {
                // TODO
            }
            CardItem(
                modifier = Modifier.weight(0.5f),
                name = bottomSheetData[0].ttl,
                type = bottomSheetData[0].values[0].value,
                id = R.drawable.goal,
                onClick = {
                    navController.navigate(SleepToolNavRoutes.SleepGoalsRoute.routes)
                }
            )
        }


        AnimatedVisibility(visible = scaffoldState.currentValue == SheetValue.Expanded) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(spacing.small),
                    verticalArrangement = Arrangement.spacedBy(spacing.medium),
                    columns = GridCells.Fixed(2)
                ) {

                    item {
                        CardItem(
                            name = bottomSheetData[1].ttl,
                            type = bottomSheetData[1].values[0].value,
                            id = R.drawable.sleep_factors
                        ) {
                            navController.navigate(SleepToolNavRoutes.SleepFactorRoute.routes)
                        }
                    }
                    item {
                        CardItem(
                            name = bottomSheetData[2].ttl,
                            type = bottomSheetData[2].values[0].value,
                            id = R.drawable.jet_plane
                        ) {
                            navController.navigate(SleepToolNavRoutes.SleepJetLagTipsRoute.routes)
                        }
                    }
                    item {
                        CardItem(
                            name = bottomSheetData[3].ttl,
                            type = bottomSheetData[3].values[0].value,
                            id = R.drawable.sleep_stories
                        ) {
                            // TODO
                        }
                    }
                }

                WeatherCard()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) {
                // TODO
            }

            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = Color.Blue,
                text = "START"
            ) {
                // TODO
            }
        }

        AnimatedVisibility(visible = scaffoldState.currentValue != SheetValue.Expanded) {
            Spacer(modifier = Modifier.height(200.dp))
        }
    }
}