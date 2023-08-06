package fit.asta.health.navigation.track.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import fit.asta.health.navigation.track.view.components.TrackTopTabBar
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

@Composable
fun TrackWaterScreen(
    waterTrackData: TrackingNetworkCall<WaterResponse>,
    trackViewModel: TrackViewModel
) {

    // This is the Item which is selected in the Top Tab Bar Layout
    val selectedItem = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {

        // Checking which tab option is selected by the User and showing the UI Accordingly
        fetchDataFunctionHelper(selectedItem.intValue, trackViewModel)

    }

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

        // This Function makes the Tab Layout UI
        TrackTopTabBar(selectedItem = selectedItem.intValue) {

            // Changing the Current Selected Item according to the User Interactions
            selectedItem.intValue = it

            // Checking which tab option is selected by the User and showing the UI Accordingly
            fetchDataFunctionHelper(selectedItem.intValue, trackViewModel)
        }
    }
}

private fun fetchDataFunctionHelper(selectedItem: Int, trackViewModel: TrackViewModel) {
    when (selectedItem) {
        0 -> trackViewModel.getWaterDetails(status = "daily")
        1 -> trackViewModel.getWaterDetails(status = "weekly")
        2 -> trackViewModel.getWaterDetails(status = "monthly")
        3 -> trackViewModel.getWaterDetails(status = "yearly")
    }
}