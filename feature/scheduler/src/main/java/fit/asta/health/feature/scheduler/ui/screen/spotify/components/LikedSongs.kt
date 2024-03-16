package fit.asta.health.feature.scheduler.ui.screen.spotify.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.remote.model.saved.SpotifyLikedSongsResponse
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.scheduler.ui.components.SpotifyMusicItem
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.feature.scheduler.ui.screen.spotify.SpotifyUiEvent
import fit.asta.health.resources.strings.R

fun LazyListScope.likedSongs(
    likedSongs: UiState<SpotifyLikedSongsResponse>,
    setEvent: (SpotifyUiEvent) -> Unit
) {
    item {
        HeadingTexts.Level3(text = stringResource(R.string.liked_songs))
    }

    // Showing the user's Spotify Liked Songs
    when (likedSongs) {

        // Idle state
        is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.NetworkIO.LoadLikedSongs)
        }

        // Loading State
        is UiState.Loading -> {
            item {
                AppDotTypingAnimation()
            }
        }

        is UiState.Success -> {
            items(likedSongs.data.trackList.size) {

                // Current Item
                val currentItem = likedSongs.data.trackList[it]

                val textToShow = currentItem.track.artists
                    .map { artist -> artist.name }
                    .toString()
                    .filterNot { char ->
                        char == '[' || char == ']'
                    }.trim()

                SpotifyMusicItem(
                    imageUri = currentItem.track.album.images.firstOrNull()?.url,
                    name = currentItem.track.name,
                    secondaryText = textToShow,
                    onCardClick = {
                        setEvent(
                            SpotifyUiEvent.HelperEvent.PlaySpotifySong(
                                currentItem.track.uri
                            )
                        )
                    }
                ) {
                    setEvent(
                        SpotifyUiEvent.HelperEvent.OnApplyClick(
                            ToneUiState(
                                name = currentItem.track.name,
                                type = 1,
                                uri = currentItem.track.uri
                            )
                        )
                    )
                }
            }
        }

        // ErrorMessage State
        is UiState.ErrorMessage -> {
            item {
                AppInternetErrorDialog(text = likedSongs.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.LocalIO.LoadAllTracks)
                }
            }
        }

        else -> {}
    }
}