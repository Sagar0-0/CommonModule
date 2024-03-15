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
import fit.asta.health.data.spotify.remote.model.common.Track
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.feature.spotify.components.MusicLargeImageColumn
import fit.asta.health.feature.spotify.events.SpotifyUiEvent
import fit.asta.health.resources.strings.R


/**
 * This screen shows the details of the Tracks choose by the user and gives the options to either
 * add this to his favourites or open the track in spotify
 *
 * @param trackNetworkState This contains the state of the Track Api call from the network
 * @param trackLocalState This contains the state of the Track fetching done from the local database
 * @param setEvent This function is used to send events from the UI to the View Model Layer
 */
@Composable
fun TrackDetailsScreen(
    trackNetworkState: UiState<Track>,
    trackLocalState: UiState<List<Track>>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(SpotifyUiEvent.NetworkIO.LoadTrackDetails)
        setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
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
        when (trackNetworkState) {

            is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.NetworkIO.LoadTrackDetails)
            }

            is UiState.Loading -> {
                AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
            }

            is UiState.Success -> {

                // This function checks for the Local Response from Local Database
                LocalTrackHandler(
                    trackNetworkState = trackNetworkState,
                    trackLocalState = trackLocalState,
                    setEvent = setEvent
                )
            }

            is UiState.ErrorMessage -> {
                AppInternetErrorDialog(
                    text = trackNetworkState.resId.toStringFromResId()
                ) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadTrackDetails)
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun LocalTrackHandler(
    trackNetworkState: UiState.Success<Track>,
    trackLocalState: UiState<List<Track>>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    // context is stored to Show the Toast
    val context = LocalContext.current

    // This function checks for the Local Response from Local Database
    when (trackLocalState) {

        // Idle State
        is UiState.Idle -> {
//            setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
        }

        // Loading State
        is UiState.Loading -> {
            AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
        }

        // Success State
        is UiState.Success -> {

            // Parsing the Network Track Data
            trackNetworkState.data.let { networkTrack ->

                // This variable stores if the Local Database contains this Track or not
                val isPresent = trackLocalState.data.contains(networkTrack)

                MusicLargeImageColumn(
                    imageUri = networkTrack.album.images.firstOrNull()?.url,
                    headerText = networkTrack.name,
                    secondaryTexts = networkTrack.artists
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


                // Add and Remove Favourites Button
                AppFilledButton(
                    textToShow = buttonText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = AppTheme.spacing.level4,
                            bottom = AppTheme.spacing.level1,
                            start = AppTheme.spacing.level1,
                            end = AppTheme.spacing.level1
                        )
                ) {
                    if (!isPresent)
                        setEvent(SpotifyUiEvent.LocalIO.InsertTrack(networkTrack))
                    else
                        setEvent(SpotifyUiEvent.LocalIO.DeleteTrack(networkTrack))

                    Toast.makeText(context, notification, Toast.LENGTH_SHORT).show()
                }

                // Play on Spotify Button
                AppFilledButton(
                    textToShow = stringResource(id = R.string.play_using_spotify),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.level1)
                ) {
                    setEvent(SpotifyUiEvent.HelperEvent.PlaySong(networkTrack.uri))
                }
            }
        }

        // ErrorMessage State
        is UiState.ErrorMessage -> {
            AppInternetErrorDialog(text = trackLocalState.resId.toStringFromResId()) {
                setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
            }
        }

        else -> {}
    }
}