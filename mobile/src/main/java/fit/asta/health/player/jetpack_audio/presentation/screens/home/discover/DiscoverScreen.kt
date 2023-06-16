package fit.asta.health.player.jetpack_audio.presentation.screens.home.discover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.presentation.components.music_item.TrackListItem
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LocalSpacing


@ExperimentalFoundationApi
@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel(),
    navigateToPlayer: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val spacing = LocalSpacing.current

    val lazyListState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(spacing.spaceSmall))

        AnimatedVisibility(
            visible = state.isLoading,
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(all = spacing.spaceLarge)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(spacing.spaceLarge),
                    color = MaterialTheme.colors.primary
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = lazyListState
        ) {

            item {
                Spacer(Modifier.height(spacing.spaceSmall))
            }

            itemsIndexed(state.selectedAlbum) { index: Int, item: Song ->
                TrackListItem(
                    song = item,
                    onClick = { isRunning ->
                        if (!isRunning)
                            viewModel.onEvent(
                                DiscoverEvents.PlaySound(
                                    isRunning = false,
                                    playWhenReady = false,
                                    idx = index
                                )
                            )
                        navigateToPlayer()
                    },
                    playPauseTrack = { isRunning, playWhenReady ->
                        viewModel.onEvent(
                            DiscoverEvents.PlaySound(
                                isRunning,
                                playWhenReady,
                                index
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.surface
                )
            }
        }
    }
}