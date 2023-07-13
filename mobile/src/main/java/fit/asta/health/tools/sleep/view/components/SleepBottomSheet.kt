package fit.asta.health.tools.sleep.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import fit.asta.health.R
import fit.asta.health.common.ui.components.ButtonWithColor
import fit.asta.health.common.ui.components.CardItem
import fit.asta.health.common.ui.theme.spacing

/**
 * This is the UI which will be displayed when the Bottom Sheet is expanded
 *
 * @param scaffoldState this defines the State of the Scaffold
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepBottomSheet(
    scaffoldState: SheetState
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
                    // TODO
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
                name = "Music ",
                type = "calm",
                id = R.drawable.baseline_music_note_24
            ) {
                // TODO
            }
            CardItem(
                modifier = Modifier.weight(0.5f),
                name = "Goal",
                type = "De-Stress",
                id = R.drawable.goal,
                onClick = {
                    // TODO
                }
            )
        }


        AnimatedVisibility(visible = scaffoldState.currentValue == SheetValue.Expanded) {

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                verticalArrangement = Arrangement.spacedBy(spacing.medium),
                columns = GridCells.Fixed(2)
            ) {

                item {
                    CardItem(
                        name = "Factors",
                        type = "Sleep Factors",
                        id = R.drawable.sleep_factors
                    ) {
                        // TODO
                    }
                }
                item {
                    CardItem(
                        name = "Jet Lag",
                        type = "Check Tips",
                        id = R.drawable.jet_plane
                    ) {
                        // TODO
                    }
                }
                item {
                    CardItem(
                        name = "Sleep Stories",
                        type = "Moon Light",
                        id = R.drawable.sleep_stories
                    ) {
                        // TODO
                    }
                }
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
    }
}