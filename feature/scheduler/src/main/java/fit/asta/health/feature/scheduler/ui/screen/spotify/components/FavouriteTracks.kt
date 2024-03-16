package fit.asta.health.feature.scheduler.ui.screen.spotify.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.remote.model.common.Track
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.scheduler.ui.components.SpotifyMusicItem
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.feature.scheduler.ui.screen.spotify.SpotifyUiEvent
import fit.asta.health.resources.strings.R

fun LazyListScope.favouriteTracks(
    favouriteTracks: UiState<List<Track>>,
    setEvent: (SpotifyUiEvent) -> Unit
) {
    item {
        HeadingTexts.Level3(text = stringResource(R.string.favourite_tracks))
    }

    // Showing the users favourite tracks
    when (favouriteTracks) {

        // Idle state
        is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
        }

        // Loading State
        is UiState.Loading -> {
            item {
                AppDotTypingAnimation()
            }
        }

        // Success State
        is UiState.Success -> {

            items(favouriteTracks.data.size) {
                // Current Item
                val currentItem = favouriteTracks.data[it]

                val textToShow = currentItem.artists
                    .map { artist -> artist.name }
                    .toString()
                    .filterNot { char ->
                        char == '[' || char == ']'
                    }.trim()

                SpotifyMusicItem(
                    imageUri = currentItem.album.images.firstOrNull()?.url,
                    name = currentItem.name,
                    secondaryText = textToShow,
                    onCardClick = {
                        setEvent(
                            SpotifyUiEvent.HelperEvent.PlaySpotifySong(
                                currentItem.uri
                            )
                        )
                    }
                ) {
                    setEvent(
                        SpotifyUiEvent.HelperEvent.OnApplyClick(
                            ToneUiState(
                                name = currentItem.name,
                                type = 1,
                                uri = currentItem.uri
                            )
                        )
                    )
                }
            }
        }

        // ErrorMessage State
        is UiState.ErrorMessage -> {
            item {
                AppInternetErrorDialog(
                    text = favouriteTracks.resId.toStringFromResId()
                ) {
                    setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
                }
            }
        }

        else -> {}
    }
}