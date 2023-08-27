package fit.asta.health.feature.spotify.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import fit.asta.health.feature.spotify.screens.AlbumDetailScreen
import fit.asta.health.feature.spotify.screens.AstaMusicScreen
import fit.asta.health.feature.spotify.screens.FavouriteScreen
import fit.asta.health.feature.spotify.screens.ProfileScreen
import fit.asta.health.feature.spotify.screens.SearchScreen
import fit.asta.health.feature.spotify.screens.ThirdPartyScreen
import fit.asta.health.feature.spotify.screens.TrackDetailsScreen
import fit.asta.health.feature.spotify.viewmodel.SpotifyViewModelX

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
                content = { AstaMusicScreen() }
            )

            // Favourite Screen
            composable(
                SpotifyNavRoutes.FavouriteScreen.routes,
                content = {

                    val tracksData = spotifyViewModelX.allTracks.collectAsStateWithLifecycle().value
                    val albumData = spotifyViewModelX.allAlbums.collectAsStateWithLifecycle().value

                    FavouriteScreen(
                        tracksData = tracksData,
                        albumData = albumData,
                        setEvent = spotifyViewModelX::uiEventListener,
                        navigator = { navController.navigate(it) }
                    )
                }
            )

            // Third Party Screen
            composable(
                SpotifyNavRoutes.ThirdPartyScreen.routes,
                content = {

                    val displayName = (spotifyViewModelX.currentUserData
                        .collectAsStateWithLifecycle().value as UiState.Success<SpotifyMeModel>)
                        .data.displayName

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

                    val trackNetworkState = spotifyViewModelX.trackDetailsResponse
                        .collectAsStateWithLifecycle().value

                    val trackLocalState = spotifyViewModelX.allTracks
                        .collectAsStateWithLifecycle().value

                    TrackDetailsScreen(
                        trackNetworkState = trackNetworkState,
                        trackLocalState = trackLocalState,
                        setEvent = spotifyViewModelX::uiEventListener
                    )
                }
            )

            // Search Screen
            composable(
                SpotifyNavRoutes.SearchScreen.routes,
                content = {

                    val spotifySearchState = spotifyViewModelX.spotifySearch
                        .collectAsStateWithLifecycle().value

                    SearchScreen(
                        spotifySearchState = spotifySearchState,
                        setEvent = spotifyViewModelX::uiEventListener,
                        navigator = { navController.navigate(it) }
                    )
                }
            )

            // Profile Screen
            composable(
                SpotifyNavRoutes.ProfileScreen.routes,
                content = {

                    val currentUserTracks = spotifyViewModelX.currentUserTracks
                        .collectAsStateWithLifecycle().value

                    val currentUserPlaylist = spotifyViewModelX.currentUserPlaylist
                        .collectAsStateWithLifecycle().value

                    val currentUserArtists = spotifyViewModelX.currentUserFollowingArtist
                        .collectAsStateWithLifecycle().value

                    val currentUserAlbums = spotifyViewModelX.currentUserAlbum
                        .collectAsStateWithLifecycle().value

                    val currentUserShows = spotifyViewModelX.currentUserShow
                        .collectAsStateWithLifecycle().value

                    val currentUserEpisodes = spotifyViewModelX.currentUserEpisode
                        .collectAsStateWithLifecycle().value

                    ProfileScreen(
                        currentUserTracks = currentUserTracks,
                        currentUserPlaylist = currentUserPlaylist,
                        currentUserArtists = currentUserArtists,
                        currentUserAlbums = currentUserAlbums,
                        currentUserShows = currentUserShows,
                        currentUserEpisodes = currentUserEpisodes,
                        setEvent = spotifyViewModelX::uiEventListener
                    )
                }
            )

            // Album Details Screen Showing the Details of a Track
            composable(
                SpotifyNavRoutes.AlbumDetailScreen.routes,
                content = {

                    val albumNetworkResponse = spotifyViewModelX.albumDetailsResponse
                        .collectAsStateWithLifecycle().value

                    val albumLocalResponse = spotifyViewModelX.allAlbums
                        .collectAsStateWithLifecycle().value

                    AlbumDetailScreen(
                        albumNetworkResponse = albumNetworkResponse,
                        albumLocalResponse = albumLocalResponse,
                        setEvent = spotifyViewModelX::uiEventListener
                    )
                }
            )
        }
    )
}