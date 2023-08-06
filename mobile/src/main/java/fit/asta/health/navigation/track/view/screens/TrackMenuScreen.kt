package fit.asta.health.navigation.track.view.screens


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    AppButtons.AppOutlinedButton(
        onClick = {
            setTrackOption(TrackOption.WaterOption)
            navigator(TrackNavRoute.Detail.route)
        }
    ) {
        Text(text = "Click Me")
    }
}