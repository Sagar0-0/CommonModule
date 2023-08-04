package fit.asta.health.scheduler.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

                    val recentlyData = spotifyViewModel.userRecentlyPlayedTracks
                        .collectAsStateWithLifecycle().value

                    val topMixData = spotifyViewModel.userTopTracks
                        .collectAsStateWithLifecycle().value

                    val favouriteTracks = spotifyViewModel.allTracks
                        .collectAsStateWithLifecycle().value

                    val favouriteAlbums = spotifyViewModel.allAlbums
                        .collectAsStateWithLifecycle().value

                    SpotifyHomeScreen(
                        recentlyData = recentlyData,
                        topMixData = topMixData,
                        favouriteTracks = favouriteTracks,
                        favouriteAlbums = favouriteAlbums,
                        loadRecentlyPlayed = spotifyViewModel::getCurrentUserRecentlyPlayedTracks,
                        loadTopMix = spotifyViewModel::getUserTopTracks,
                        loadFavouriteTracks = spotifyViewModel::getAllTracks,
                        loadFavouriteAlbums = spotifyViewModel::getAllAlbums,
                        playSong = spotifyViewModel::playSpotifySong,
                        navSearch = { navController.navigate(SpotifyNavRoutes.SearchScreen.routes) },
                        onApplyClick = spotifyViewModel::onApplyClick
                    )
                }
            )

            // Search Screen
            composable(
                SpotifyNavRoutes.SearchScreen.routes,
                content = {

                    val searchResult =
                        spotifyViewModel.spotifySearch.collectAsStateWithLifecycle().value

                    SpotifySearchScreen(
                        searchResult = searchResult,
                        playSong = spotifyViewModel::playSpotifySong,
                        setSearchQuery = spotifyViewModel::setSearchQueriesAndVariables,
                        onApplyClick = spotifyViewModel::onApplyClick,
                        onSearch = spotifyViewModel::getSpotifySearchResult
                    )
                }
            )
        }
    )
}