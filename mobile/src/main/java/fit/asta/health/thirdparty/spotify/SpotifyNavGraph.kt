package fit.asta.health.thirdparty.spotify

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.thirdparty.spotify.view.screens.AlbumDetailScreen
import fit.asta.health.thirdparty.spotify.view.screens.AstaMusicScreen
import fit.asta.health.thirdparty.spotify.view.screens.FavouriteScreen
import fit.asta.health.thirdparty.spotify.view.screens.ProfileScreen
import fit.asta.health.thirdparty.spotify.view.screens.SearchScreen
import fit.asta.health.thirdparty.spotify.view.screens.ThirdPartyScreen
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
        startDestination = SpotifyNavRoutes.AstaMusicScreen.routes,
        builder = {

            // Asta Music Screen
            composable(
                SpotifyNavRoutes.AstaMusicScreen.routes,
                content = { AstaMusicScreen(spotifyViewModelX = spotifyViewModelX) }
            )

            // Favourite Screen
            composable(
                SpotifyNavRoutes.FavouriteScreen.routes,
                content = { FavouriteScreen(favouriteViewModelX = favouriteViewModelX) }
            )

            // Third Party Screen
            composable(
                SpotifyNavRoutes.ThirdPartyScreen.routes,
                content = {
                    ThirdPartyScreen(
                        spotifyViewModelX = spotifyViewModelX,
                        navController = navController
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

            // Search Screen
            composable(
                SpotifyNavRoutes.SearchScreen.routes,
                content = {
                    SearchScreen(
                        navController = navController,
                        spotifyViewModelX = spotifyViewModelX
                    )
                }
            )

            // Profile Screen
            composable(
                SpotifyNavRoutes.ProfileScreen.routes,
                content = {
                    ProfileScreen(
                        navController = navController,
                        spotifyViewModelX = spotifyViewModelX
                    )
                }
            )

            // Album Details Screen Showing the Details of a Track
            composable(
                SpotifyNavRoutes.AlbumDetailScreen.routes,
                content = {
                    AlbumDetailScreen(
                        spotifyViewModelX = spotifyViewModelX,
                        favouriteViewModelX = favouriteViewModelX
                    )
                }
            )
        }
    )
}