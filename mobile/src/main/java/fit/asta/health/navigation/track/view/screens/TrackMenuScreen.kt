package fit.asta.health.navigation.track.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.navigation.track.TrackNavRoute
import fit.asta.health.navigation.track.TrackingOptions
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

// Preview Composable Function
@Preview(
    "Light"
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            TrackMenuScreen(
                navController = rememberNavController(),
                trackViewModel = viewModel()
            )
        }
    }
}

/**
 * This Screen shows all the Statistics Options that are there in the App for the user to choose
 * the specific Option they want to see
 *
 * @param navController Controller which lets us navigate to a different Screen
 * @param trackViewModel This is the [TrackViewModel] class object which contains all the
 * business logic for the Tracking Feature
 */
@Composable
fun TrackMenuScreen(
    navController: NavController,
    trackViewModel: TrackViewModel
) {

    // Constant Dimensions used in the Screen
    val cardSize = 180.dp
    val imageSize = 54.dp

    // All the Available Tracking Options in the App
    val trackingOptionsList = TrackingOptions.trackingOptionsList

    LazyVerticalGrid(
        columns = GridCells.Adaptive(cardSize)
    ) {

        items(trackingOptionsList.size) { index ->

            // Current Tracking Option
            val currentTrackingOption = trackingOptionsList[index]

            Card(
                modifier = Modifier
                    .size(cardSize)
                    .padding(8.dp)
                    .clickable {
                        trackViewModel.changeTrackingOption(currentTrackingOption)
                        navController.navigate(TrackNavRoute.Detail.route)
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = currentTrackingOption.displayImage),
                        contentDescription = currentTrackingOption.displayText,
                        modifier = Modifier
                            .size(imageSize)
                    )
                    Text(
                        text = currentTrackingOption.displayText,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}