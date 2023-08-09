package fit.asta.health.navigation.track.view.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.navigation.track.view.navigation.TrackNavRoute
import fit.asta.health.navigation.track.view.util.TrackOption

/**
 * This Screen shows all the Statistics Options that are there in the App for the user to choose
 * the specific Option they want to see
 *
 * @param navigator Controller which lets us navigate to a different Screen
 * @param setTrackOption This option sets the Track Option which is selected by the user
 */
@Composable
fun TrackMenuScreen(
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