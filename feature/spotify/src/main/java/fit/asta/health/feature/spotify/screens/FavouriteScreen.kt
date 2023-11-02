package fit.asta.health.feature.spotify.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.spotify.components.MusicLargeImageColumn
import fit.asta.health.feature.spotify.events.SpotifyUiEvent
import fit.asta.health.feature.spotify.navigation.SpotifyNavRoutes
import fit.asta.health.resources.strings.R

/**
 * This function contains the UI of the Favourite Screen
 *
 * @param tracksData This contains the state of all the TrackData in the local database
 * @param albumData This contains the state of all the albumData in the local Database
 * @param setEvent This function is used to send events from the UI to View Model layer
 * @param navigator THis function is used to handle all the navigation
 */
@Composable
fun FavouriteScreen(
    tracksData: UiState<List<Track>>,
    albumData: UiState<List<Album>>,
    setEvent: (SpotifyUiEvent) -> Unit,
    navigator: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
        setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
    }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
    ) {

        // Track Text
        HeadingTexts.Level1(
            text = stringResource(id = R.string.tracks),
            modifier = Modifier.padding(AppTheme.spacing.level1),
        )

        // This Draws all the Track Cards
        when (tracksData) {

            // Idle State
            is UiState.Idle -> {
                setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
            }

            // Loading State
            is UiState.Loading -> {
                AppDotTypingAnimation(
                    modifier = Modifier
                        .height(AppTheme.boxSize.level9)
                        .fillMaxWidth()
                        .testTag("Favourite Screen")
                )
            }

            // success State
            is UiState.Success -> {
                LazyRow(
                    modifier = Modifier
                        .height(AppTheme.boxSize.level9)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    items(tracksData.data.size) {

                        // current Item
                        val currentItem = tracksData.data[it]

                        MusicLargeImageColumn(
                            imageUri = currentItem.album.images.firstOrNull()?.url,
                            headerText = currentItem.name,
                            secondaryTexts = currentItem.artists
                        ) {

                            // Navigating to the Track Details Screen
                            setEvent(SpotifyUiEvent.HelperEvent.SetTrackId(currentItem.id))
                            navigator(SpotifyNavRoutes.TrackDetailScreen.routes)
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                AppInternetErrorDialog(issueDescription = tracksData.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
                }
            }

            else -> {}
        }

        // Albums Text
        HeadingTexts.Level1(
            text = stringResource(id = R.string.albums),
            modifier = Modifier
                .padding(
                    top = AppTheme.spacing.level3,
                    bottom = AppTheme.spacing.level1,
                    start = AppTheme.spacing.level1,
                    end = AppTheme.spacing.level1
                ),
        )

        // This Draws all the Albums Cards
        when (albumData) {

            // Idle State
            is UiState.Idle -> {
                setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
            }

            // Loading State
            is UiState.Loading -> {
                AppDotTypingAnimation(
                    modifier = Modifier
                        .height(AppTheme.boxSize.level9)
                        .fillMaxWidth()
                        .testTag("Favourite Screen")
                )
            }

            // Success State
            is UiState.Success -> {
                LazyRow(
                    modifier = Modifier
                        .height(AppTheme.boxSize.level9)
                        .width(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    items(albumData.data.size) {

                        // Current Item
                        val currentItem = albumData.data[it]
                        MusicLargeImageColumn(
                            imageUri = currentItem.images.firstOrNull()?.url,
                            headerText = currentItem.name,
                            secondaryTexts = currentItem.artists
                        ) {

                            // Navigating the Album Details Screen to get the Album Details
                            setEvent(SpotifyUiEvent.HelperEvent.SetAlbumId(currentItem.id))
                            navigator(SpotifyNavRoutes.AlbumDetailScreen.routes)
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                AppInternetErrorDialog(issueDescription = albumData.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
                }
            }

            else -> {}
        }
    }
}