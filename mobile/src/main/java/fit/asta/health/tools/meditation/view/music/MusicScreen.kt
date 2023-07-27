package fit.asta.health.tools.meditation.view.music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.components.AppTopBar
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.AppIcons
import fit.asta.health.player.jetpack_audio.domain.utils.asFormattedString
import fit.asta.health.player.jetpack_audio.exo_player.common.MusicState
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LocalSpacing


@Composable
fun MusicScreen(
    modifier: Modifier = Modifier,
    state: MusicViewState,
    musicState: MusicState,
    currentPosition: Long,
    onMusicEvents: (MusicEvents) -> Unit,
    navigateToPlayer: () -> Unit,
    onBack: () -> Unit,
) {

    val spacing = LocalSpacing.current

    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AppTopBar(text = "Music ",
                onBackPressed = onBack,
                actionItems = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_physique),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
            })
        },
        containerColor =MaterialTheme.colorScheme.onSurface ,
    ) {
        Column(
            modifier = modifier.padding(it).fillMaxSize()
        ) {
            Spacer(Modifier.height(spacing.spaceSmall))
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
                    TrackItem(
                        musicState = musicState,
                        currentPosition = currentPosition,
                        song = item,
                        onClick = { isRunning ->
                            if (!isRunning)
                                onMusicEvents(
                                    MusicEvents.PlaySound(
                                        isRunning = false,
                                        playWhenReady = false,
                                        idx = index
                                    )
                                )
                            navigateToPlayer()
                        },
                        playPauseTrack = { isRunning, playWhenReady ->
                            onMusicEvents(
                                MusicEvents.PlaySound(
                                    isRunning,
                                    playWhenReady,
                                    index
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }

}

@Composable
fun TrackItem(
    modifier: Modifier = Modifier,
    musicState: MusicState,
    currentPosition: Long,
    onClick: (Boolean) -> Unit,
    song: Song,
    playPauseTrack: (Boolean, Boolean) -> Unit,
    backgroundColor: Color = Color.Transparent
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val isRunning = musicState.currentSong.id == song.id

    val textColor = if (isRunning) Color.Magenta
    else MaterialTheme.colorScheme.onSurface


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(isRunning) }
            .background(backgroundColor),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            Column(
                modifier = Modifier.weight(.5f),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = song.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                )
                Text(
                    text = song.artist,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isRunning && musicState.playWhenReady)
                                AppIcons.Pause.resourceId
                            else
                                AppIcons.Play.resourceId
                        ),
                        contentDescription = stringResource(id = R.string.play),
                        tint = if (isRunning) textColor else LocalContentColor.current,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = false, radius = 24.dp)
                            ) {
                                playPauseTrack(isRunning, musicState.playWhenReady)
                            }
                            .size(32.dp)
                            .padding(spacing.spaceSmall)
                    )
                    Text(
                        text = if (isRunning)
                            currentPosition.asFormattedString()
                        else
                            song.duration.asFormattedString(),
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(getImageUrl(url = song.artworkUri.toString()))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(75.dp)
                    .weight(.5f)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}