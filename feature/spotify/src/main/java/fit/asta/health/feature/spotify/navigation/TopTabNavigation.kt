package fit.asta.health.feature.spotify.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fit.asta.health.feature.spotify.components.MusicTopTabBar
import fit.asta.health.feature.spotify.viewmodel.SpotifyViewModelX

@Composable
fun TopTabNavigation(
    spotifyViewModelX: SpotifyViewModelX,
    navController: NavHostController
) {

    val optionList = listOf(
        Pair("Asta Music", SpotifyNavRoutes.AstaMusicScreen.routes),
        Pair("Favourite", SpotifyNavRoutes.FavouriteScreen.routes),
        Pair("Third Party", SpotifyNavRoutes.ThirdPartyScreen.routes)
    )

    // This Variable keeps track of the Latest BackStack Entry
    val backStackEntry = navController.currentBackStackEntryAsState()

    // Checking if the Current Route and the Item is Same so that the item can be decorated differently
    val selectedItem = optionList.indexOfFirst {
        it.second == backStackEntry.value?.destination?.route
    }

    // This Function makes the Tab Layout UI
    MusicTopTabBar(
        tabList = optionList.map { it.first },
        selectedItem = selectedItem,
        selectedColor = MaterialTheme.colorScheme.primary,
        unselectedColor = MaterialTheme.colorScheme.secondary,
        onNewTabClicked = {

            // Changing the Current Selected Item according to the User Interactions
            navController.navigate(optionList[it].second)
        }
    ) {

        // Initializing the NavGraph
        SpotifyNavGraph(
            navController = navController,
            spotifyViewModelX = spotifyViewModelX
        )
    }
}