package fit.asta.health.feature.spotify.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.feature.spotify.components.MusicLargeImageColumn
import fit.asta.health.feature.spotify.events.SpotifyUiEvent
import fit.asta.health.resources.strings.R

/**
 * This screen shows the details of the Albums choose by the user and gives the options to either
 * add this to his favourites or open the Album in spotify
 *
 * @param albumNetworkResponse This contains the state of the Network Data of the Album
 * @param albumLocalResponse This contains the state of the Local Data of the Album
 * @param setEvent This function is used to send the ui events from the UI to the View Model layer
 */
@Composable
fun AlbumDetailScreen(
    albumNetworkResponse: UiState<Album>,
    albumLocalResponse: UiState<List<Album>>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadAlbumDetails)
        setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
    }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // This function checks for the Network Response from the API
        when (albumNetworkResponse) {

            is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.NetworkIO.LoadAlbumDetails)
            }

            is UiState.Loading -> {
                AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
            }

            is UiState.Success -> {
                // This function checks for the Local Response from Local Database
                LocalAlbumHandler(
                    albumNetworkResponse = albumNetworkResponse,
                    albumLocalResponse = albumLocalResponse,
                    setEvent = setEvent
                )
            }

            is UiState.ErrorMessage -> {
                AppInternetErrorDialog(
                    issueDescription = albumNetworkResponse.resId.toStringFromResId()
                ) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadAlbumDetails)
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun LocalAlbumHandler(
    albumNetworkResponse: UiState.Success<Album>,
    albumLocalResponse: UiState<List<Album>>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    // context is stored to Show the Toast
    val context = LocalContext.current

    when (albumLocalResponse) {

        // Idle State
        is UiState.Idle -> {
            setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
        }

        // Loading State
        is UiState.Loading -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
        }

        // Success State
        is UiState.Success -> {

            // Parsing the Network Album Data
            albumNetworkResponse.data.let { networkAlbumData ->

                // This variable stores if the Local Database contains this Album or not
                val isPresent = albumLocalResponse.data.contains(networkAlbumData)

                // Showing the Album Data at the Screen
                MusicLargeImageColumn(
                    imageUri = networkAlbumData.images.firstOrNull()?.url,
                    headerText = networkAlbumData.name,
                    secondaryTexts = networkAlbumData.artists
                ) {}

                // Notification
                val notification: String
                val buttonText: String
                if (!isPresent) {
                    notification = stringResource(id = R.string.added)
                    buttonText = stringResource(id = R.string.add_to_favourites)
                } else {
                    notification = stringResource(id = R.string.deleted)
                    buttonText = stringResource(id = R.string.delete_from_favourites)
                }

                // Add to Favourites Button
                AppFilledButton(
                    textToShow = buttonText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = AppTheme.spacing.level4,
                            bottom = AppTheme.spacing.level0,
                            start = AppTheme.spacing.level1,
                            end = AppTheme.spacing.level1
                        )
                ) {

                    // Checking if the Album is already present or not
                    if (!isPresent)
                        setEvent(SpotifyUiEvent.LocalIO.InsertAlbum(networkAlbumData))
                    else
                        setEvent(SpotifyUiEvent.LocalIO.DeleteAlbum(networkAlbumData))

                    Toast.makeText(context, notification, Toast.LENGTH_SHORT).show()
                }

                // Play on Spotify Button
                AppFilledButton(
                    textToShow = stringResource(id = R.string.play_using_spotify),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = AppTheme.spacing.level0,
                            start = AppTheme.spacing.level1,
                            end = AppTheme.spacing.level1
                        )
                ) {
                    setEvent(SpotifyUiEvent.HelperEvent.PlaySong(networkAlbumData.uri))
                }
            }
        }

        // ErrorMessage State
        is UiState.ErrorMessage -> {
            AppInternetErrorDialog(issueDescription = albumLocalResponse.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.LocalIO.LoadAllAlbums)
            }
        }

        else -> {}
    }
}