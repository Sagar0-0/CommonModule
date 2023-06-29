package fit.asta.health.thirdparty.spotify.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.thirdparty.spotify.view.components.MusicTopTabBar
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

// Preview Composable Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            MainScreen(
                navController = rememberNavController(),
                spotifyViewModelX = hiltViewModel()
            )
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController,
    spotifyViewModelX: SpotifyViewModelX
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // This is the Item which is selected in the Top Tab Bar Layout
        val selectedItem = remember { mutableIntStateOf(0) }

        // This Function makes the Tab Layout UI
        MusicTopTabBar(
            tabList = listOf(
                "Asta Music",
                "Favourite",
                "Third Party"
            ),
            selectedItem = selectedItem.intValue,
            selectedColor = MaterialTheme.colorScheme.primary,
            unselectedColor = MaterialTheme.colorScheme.secondary
        ) {

            // Changing the Current Selected Item according to the User Interactions
            selectedItem.intValue = it
        }

        // Checking which UI to show according to the user Selection
        when (selectedItem.intValue) {
            0 -> {
                AstaMusicScreen(spotifyViewModelX = spotifyViewModelX)
            }

            1 -> {
                FavouriteScreen()
            }

            2 -> {
                ThirdPartyScreen(spotifyViewModelX = spotifyViewModelX)
            }
        }
    }
}