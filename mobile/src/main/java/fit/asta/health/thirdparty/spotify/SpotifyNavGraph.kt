package fit.asta.health.thirdparty.spotify

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.thirdparty.spotify.view.screens.MainScreen
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyAuthViewModelX

/**
 * This is the navigation Host function for the Music Feature
 *
 * @param navController This is the navController for the Tracking Screens
 * @param spotifyAuthViewModelX This is the View Model for all the Spotify Screen
 */
@Composable
fun SpotifyNavGraph(
    navController: NavHostController,
    spotifyAuthViewModelX: SpotifyAuthViewModelX
) {

    NavHost(
        navController = navController,
        startDestination = SpotifyNavRoutes.MainScreen.routes,
        builder = {

            composable(
                SpotifyNavRoutes.MainScreen.routes,
                content = {
                    MainScreen(
                        navController = navController,
                        spotifyAuthViewModelX = spotifyAuthViewModelX
                    )
                }
            )
        }
    )
}