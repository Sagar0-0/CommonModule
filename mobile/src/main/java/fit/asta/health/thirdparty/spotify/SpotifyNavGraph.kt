package fit.asta.health.thirdparty.spotify

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.thirdparty.spotify.view.screens.MainScreen
import fit.asta.health.thirdparty.spotify.view.screens.TrackDetailsScreen
import fit.asta.health.thirdparty.spotify.viewmodel.FavouriteViewModelX
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

/**
 * This is the navigation Host function for the Music Feature
 *
 * @param navController This is the navController for the Tracking Screens
 * @param spotifyViewModelX This is the View Model for all the Spotify Screen
 * @param favouriteViewModelX This is the viewModel containing the local repo functions
 */
@Composable
fun SpotifyNavGraph(
    navController: NavHostController,
    spotifyViewModelX: SpotifyViewModelX,
    favouriteViewModelX: FavouriteViewModelX
) {

    NavHost(
        navController = navController,
        startDestination = SpotifyNavRoutes.MainScreen.routes,
        builder = {

            // Main Screen Contains all the 3 Tabs to choose from
            composable(
                SpotifyNavRoutes.MainScreen.routes,
                content = {
                    MainScreen(
                        navController = navController,
                        spotifyViewModelX = spotifyViewModelX,
                        favouriteViewModelX
                    )
                }
            )

            // Track Details Screen Showing the Details of a Track
            composable(
                SpotifyNavRoutes.TrackDetailScreen.routes,
                content = {
                    TrackDetailsScreen(
                        spotifyViewModelX = spotifyViewModelX,
                        favouriteViewModelX = favouriteViewModelX
                    )
                }
            )
        }
    )
}