package fit.asta.health.feature.scheduler.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.feature.scheduler.ui.screen.spotify.SpotifyHomeScreen
import fit.asta.health.feature.scheduler.ui.screen.spotify.SpotifySearchScreen
import fit.asta.health.feature.scheduler.ui.viewmodel.SpotifyViewModel

@Composable
fun SpotifyNavGraph(
    navController: NavHostController,
    spotifyViewModel: SpotifyViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SpotifyNavRoutes.HomeScreen.routes,
        builder = {

            // Home Screen
            composable(
                SpotifyNavRoutes.HomeScreen.routes,
                content = {

                    val recentlyData = spotifyViewModel.userRecentlyPlayedTracks
                        .collectAsStateWithLifecycle().value

                    val topMixData = spotifyViewModel.userTopTracks
                        .collectAsStateWithLifecycle().value

                    val likedSongs = spotifyViewModel.currentUserSavedSongs
                        .collectAsStateWithLifecycle().value

                    val favouriteTracks = spotifyViewModel.allTracks
                        .collectAsStateWithLifecycle().value

                    val favouriteAlbums = spotifyViewModel.allAlbums
                        .collectAsStateWithLifecycle().value

                    SpotifyHomeScreen(
                        recentlyData = recentlyData,
                        topMixData = topMixData,
                        likedSongs = likedSongs,
                        favouriteTracks = favouriteTracks,
                        favouriteAlbums = favouriteAlbums,
                        setEvent = spotifyViewModel::eventHelper,
                        navSearch = { navController.navigate(SpotifyNavRoutes.SearchScreen.routes) }
                    )
                }
            )

            // Search Screen
            composable(
                SpotifyNavRoutes.SearchScreen.routes,
                content = {

                    val searchResult = spotifyViewModel.spotifySearch
                        .collectAsStateWithLifecycle().value

                    SpotifySearchScreen(
                        searchResult = searchResult,
                        setEvent = spotifyViewModel::eventHelper
                    )
                }
            )
        }
    )
}