package fit.asta.health.navigation.track.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.viewmodel.compose.viewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.navigation.track.view.components.TrackTopTabBar
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

// Preview Composable Function
@Preview(
    "Light",
    heightDp = 2000
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 2000
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            TrackDetailScreen(
                trackViewModel = viewModel()
            )
        }
    }
}

/**
 * This function shows all the statistics
 *
 * @param trackViewModel This is the [TrackViewModel] class object which contains all the
 * business logic of the Tracking Screens
 */
@Composable
fun TrackDetailScreen(
    trackViewModel: TrackViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(
                    ColorUtils.blendARGB(
                        MaterialTheme.colorScheme.surface.toArgb(),
                        MaterialTheme.colorScheme.onSurface.toArgb(),
                        0.08f
                    )
                )
            )
    ) {

        // This is the Item which is selected in the Top Tab Bar Layout
        val selectedItem = remember { mutableIntStateOf(0) }

        // This Function makes the Tab Layout UI
        TrackTopTabBar(
            tabList = listOf(
                "DAY",
                "WEEK",
                "MONTH",
                "ALL"
            ),
            selectedItem = selectedItem.intValue,
            selectedColor = Color.Blue,
            unselectedColor = Color.Gray
        ) {

            // Changing the Current Selected Item according to the User Interactions
            selectedItem.intValue = it
        }

        // Checking which tab option is selected by the User and showing the UI Accordingly
        when (selectedItem.intValue) {
            0 -> {
//                trackViewModel.getWaterDetails()
                trackViewModel.getStepsDetails()
                TrackStatisticsUI(trackViewModel = trackViewModel)
            }

            1 -> {
                TrackStatisticsUI(trackViewModel = trackViewModel)
            }

            2 -> {
                TrackStatisticsUI(trackViewModel = trackViewModel)
            }

            3 -> {
                TrackStatisticsUI(trackViewModel = trackViewModel)
            }
        }
    }
}