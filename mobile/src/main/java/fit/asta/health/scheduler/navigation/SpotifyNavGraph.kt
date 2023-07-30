package fit.asta.health.scheduler.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.scheduler.compose.screen.spotify.SpotifyHomeScreen
import fit.asta.health.scheduler.compose.screen.spotify.SpotifySearchScreen
import fit.asta.health.scheduler.viewmodel.SpotifyViewModel

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
                    SpotifyHomeScreen(
                        loadRecentlyPlayed = spotifyViewModel::getCurrentUserRecentlyPlayedTracks,
                        loadTopMix = spotifyViewModel::getUserTopTracks,
                        recentlyData = spotifyViewModel.userRecentlyPlayedTracks.collectAsState().value,
                        topMixData = spotifyViewModel.userTopTracks.collectAsState().value,
                        playSong = spotifyViewModel::playSpotifySong,
                        navSearch = { navController.navigate(SpotifyNavRoutes.SearchScreen.routes) }
                    )
                }
            )

            // Search Screen
            composable(
                SpotifyNavRoutes.SearchScreen.routes,
                content = {
                    SpotifySearchScreen(
                        searchResult = spotifyViewModel.spotifySearch.collectAsState().value,
                        playSong = spotifyViewModel::playSpotifySong,
                        setSearchQuery = spotifyViewModel::setSearchQueriesAndVariables
                    )
                }
            )
        }
    )
}