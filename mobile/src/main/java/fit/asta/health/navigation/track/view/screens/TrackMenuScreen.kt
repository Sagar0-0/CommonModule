package fit.asta.health.navigation.track.view.screens


import android.util.Log.d
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.navigation.track.model.net.menu.HomeMenuResponse
import fit.asta.health.navigation.track.view.navigation.TrackNavRoute
import fit.asta.health.navigation.track.view.util.TrackOption
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall

/**
 * This Screen shows all the Statistics Options that are there in the App for the user to choose
 * the specific Option they want to see
 *
 * @param homeMenuState This variable contains the state of the Home Menu Api call
 * @param loadHomeData This is the function which fetches the data from the server
 * @param navigator Controller which lets us navigate to a different Screen
 * @param setTrackOption This option sets the Track Option which is selected by the user
 */
@Composable
fun TrackMenuScreenControl(
    homeMenuState: TrackingNetworkCall<HomeMenuResponse>,
    loadHomeData: () -> Unit,
    setTrackOption: (TrackOption) -> Unit,
    navigator: (String) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loadHomeData()
    }

    when (homeMenuState) {
        is TrackingNetworkCall.Initialized -> {}

        is TrackingNetworkCall.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        is TrackingNetworkCall.Success -> {
            if (homeMenuState.data != null)
                TrackMenuSuccessScreen(
                    homeMenuState = homeMenuState,
                    setTrackOption = setTrackOption,
                    navigator = navigator
                )
            else {
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
            }
        }

        is TrackingNetworkCall.Failure -> {
            d("Home Menu Screen", homeMenuState.message.toString())
            Toast.makeText(context, homeMenuState.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun TrackMenuSuccessScreen(
    homeMenuState: TrackingNetworkCall<HomeMenuResponse>,
    setTrackOption: (TrackOption) -> Unit,
    navigator: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            AppButtons.AppOutlinedButton(
                onClick = {
                    setTrackOption(TrackOption.WaterOption)
                    navigator(TrackNavRoute.WaterTrackDetail.route)
                }
            ) {
                Text(text = "Water Option")
            }
        }

        item {
            AppButtons.AppOutlinedButton(
                onClick = {
                    setTrackOption(TrackOption.StepsOption)
                    navigator(TrackNavRoute.StepsTrackDetail.route)
                }
            ) {
                Text(text = "Steps Option")
            }
        }

        item {
            AppButtons.AppOutlinedButton(
                onClick = {
                    setTrackOption(TrackOption.BreathingOption)
                    navigator(TrackNavRoute.BreathingTrackDetail.route)
                }
            ) {
                Text(text = "Breathing Option")
            }
        }

        item {
            AppButtons.AppOutlinedButton(
                onClick = {
                    setTrackOption(TrackOption.SleepOption)
                    navigator(TrackNavRoute.SleepTrackDetail.route)
                }
            ) {
                Text(text = "Sleep Option")
            }
        }

        item {
            AppButtons.AppOutlinedButton(
                onClick = {
                    setTrackOption(TrackOption.SunlightOption)
                    navigator(TrackNavRoute.SunlightTrackDetail.route)
                }
            ) {
                Text(text = "Sunlight Option")
            }
        }

        item {
            AppButtons.AppOutlinedButton(
                onClick = {
                    setTrackOption(TrackOption.MeditationOption)
                    navigator(TrackNavRoute.MeditationTrackDetail.route)
                }
            ) {
                Text(text = "Meditation Option")
            }
        }
    }
}