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