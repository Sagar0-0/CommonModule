package fit.asta.health.thirdparty.spotify.view.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import fit.asta.health.thirdparty.spotify.viewmodel.SpotifyViewModelX

/**
 * This is the navigation Host function for the Music Feature
 *
 * @param navController This is the navController for the Tracking Screens
 * @param spotifyViewModelX This is the View Model for all the Spotify Screen
 */
@Composable
fun SpotifyNavGraph(
    navController: NavHostController,
    spotifyViewModelX: SpotifyViewModelX
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
                content = {
                    FavouriteScreen(
                        spotifyViewModelX = spotifyViewModelX,
                        navController = navController
                    )
                }
            )

            // Third Party Screen
            composable(
                SpotifyNavRoutes.ThirdPartyScreen.routes,
                content = {

                    val displayName = spotifyViewModelX.currentUserData
                        .collectAsStateWithLifecycle().value.data?.displayName

                    val recentlyPlayedData = spotifyViewModelX.userRecentlyPlayedTracks
                        .collectAsStateWithLifecycle().value

                    val recommendedTracks = spotifyViewModelX.recommendationTracks
                        .collectAsStateWithLifecycle().value

                    val topTracks = spotifyViewModelX.userTopTracks
                        .collectAsStateWithLifecycle().value

                    val topArtists = spotifyViewModelX.userTopArtists
                        .collectAsStateWithLifecycle().value

                    ThirdPartyScreen(
                        displayName = displayName,
                        recentlyPlayed = recentlyPlayedData,
                        recommendedData = recommendedTracks,
                        topTracksData = topTracks,
                        topArtistsData = topArtists,
                        setEvent = spotifyViewModelX::uiEventListener,
                        navigator = { navController.navigate(it) },
                    )
                }
            )

            // Track Details Screen Showing the Details of a Track
            composable(
                SpotifyNavRoutes.TrackDetailScreen.routes,
                content = {
                    TrackDetailsScreen(
                        spotifyViewModelX = spotifyViewModelX
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
                content = { ProfileScreen(spotifyViewModelX = spotifyViewModelX) }
            )

            // Album Details Screen Showing the Details of a Track
            composable(
                SpotifyNavRoutes.AlbumDetailScreen.routes,
                content = {
                    AlbumDetailScreen(
                        spotifyViewModelX = spotifyViewModelX
                    )
                }
            )
        }
    )
}