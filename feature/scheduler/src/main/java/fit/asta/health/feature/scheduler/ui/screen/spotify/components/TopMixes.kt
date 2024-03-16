package fit.asta.health.feature.scheduler.ui.screen.spotify.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.remote.model.search.TrackList
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.scheduler.ui.components.SpotifyMusicItem
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.feature.scheduler.ui.screen.spotify.SpotifyUiEvent
import fit.asta.health.resources.strings.R

 fun LazyListScope.topMixes(
    topMixData: UiState<TrackList>,
    setEvent: (SpotifyUiEvent) -> Unit
) {
    item {
        HeadingTexts.Level3(text = stringResource(R.string.top_mixes))
    }

    // Showing the user's top mix Data
    when (topMixData) {

        // Idle State
        is UiState.Idle -> {
//                setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)
        }

        // Loading State
        is UiState.Loading -> {
            item {
                AppDotTypingAnimation()
            }
        }

        // Success State
        is UiState.Success -> {
            topMixData.data.trackList.let { trackList ->
                items(trackList.size) {

                    val currentItem = trackList[it]

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
        }

        // ErrorMessage State
        is UiState.ErrorMessage -> {
            item {
                AppInternetErrorDialog(text = topMixData.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.NetworkIO.LoadUserTopTracks)
                }
            }
        }

        else -> {}
    }
}