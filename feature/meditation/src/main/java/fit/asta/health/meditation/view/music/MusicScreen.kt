package fit.asta.health.meditation.view.music

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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
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
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.player.audio.common.MusicState
import fit.asta.health.player.domain.model.Song
import fit.asta.health.player.domain.utils.AppIcons
import fit.asta.health.player.domain.utils.asFormattedString
import fit.asta.health.resources.strings.R


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

    val spacing = AppTheme.spacing

    val lazyListState = rememberLazyListState()

    AppScaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(title = "Music ",
                onBack = onBack,
                onHelp = {/*TODO*/ }
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(spacing.level2))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = lazyListState
            ) {

                item {
                    Spacer(Modifier.height(spacing.level2))
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
    val spacing = AppTheme.spacing
    val context = LocalContext.current
    val isRunning = musicState.currentSong.id == song.id

    val textColor = if (isRunning) Color.Magenta
    else MaterialTheme.colorScheme.onSurface


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(isRunning) }
            .background(backgroundColor),
        verticalArrangement = Arrangement.spacedBy(spacing.level2)
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
            horizontalArrangement = Arrangement.spacedBy(spacing.level3)
        ) {
            Column(
                modifier = Modifier.weight(.5f),
                verticalArrangement = Arrangement.spacedBy(spacing.level2),
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
                            .padding(spacing.level2)
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
                    .data(getImgUrl(url = song.artworkUri.toString()))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(75.dp)
                    .weight(.5f)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}