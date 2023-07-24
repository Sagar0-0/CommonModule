package fit.asta.health.player.jetpack_audio.presentation.components.music_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.player.jetpack_audio.domain.data.Song
import fit.asta.health.player.jetpack_audio.domain.utils.AppIcons
import fit.asta.health.player.jetpack_audio.domain.utils.asFormattedString
import fit.asta.health.player.jetpack_audio.presentation.ui.theme.LocalSpacing


@Composable
fun TrackListItem(
    modifier: Modifier = Modifier,
    viewModel: SongItemViewModel = hiltViewModel(),
    onClick: (Boolean) -> Unit,
    song: Song,
    playPauseTrack: (Boolean, Boolean) -> Unit,
    backgroundColor: Color = Color.Transparent
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val musicState by viewModel.musicState.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val isRunning = musicState.currentSong.id == song.id
    musicState.playbackState.name

    val textColor = if (isRunning)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.onSurface

    ConstraintLayout(
        modifier = modifier
            .clickable { onClick(isRunning) }
            .background(backgroundColor)
    ) {
        val (
            divider, trackTitle, trackDuration, image, playIcon,
            titleShort, addPlaylist, overflow
        ) = createRefs()

        Divider(
            modifier = Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(song.artworkUri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    end.linkTo(parent.end, spacing.spaceMedium)
                    top.linkTo(parent.top, spacing.spaceMedium)
                }
        )

        Text(
            text = song.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            modifier = Modifier.constrainAs(trackTitle) {
                linkTo(
                    start = parent.start,
                    end = image.start,
                    startMargin = spacing.spaceMedium,
                    endMargin = spacing.spaceMedium,
                    bias = 0f
                )
                top.linkTo(parent.top, spacing.spaceMedium)
                height = Dimension.preferredWrapContent
                width = Dimension.preferredWrapContent
            }
        )

        val titleImageBarrier = createBottomBarrier(trackTitle, image)

        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.titleSmall,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(titleShort) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        startMargin = spacing.spaceMedium,
                        endMargin = spacing.spaceMedium,
                        bias = 0f
                    )
                    top.linkTo(trackTitle.bottom, spacing.spaceSmall)
                    height = Dimension.preferredWrapContent
                    width = Dimension.preferredWrapContent
                }
            )
        }
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
                .semantics { role = Role.Button }
                .constrainAs(playIcon) {
                    start.linkTo(parent.start, spacing.spaceMedium)
                    top.linkTo(titleImageBarrier, spacing.spaceSmall)
                    bottom.linkTo(parent.bottom, spacing.spaceSmall)
                }
        )
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            Text(
                text = if (isRunning)
                    currentPosition.asFormattedString()
                else
                    song.duration.asFormattedString(),
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(trackDuration) {
                    centerVerticallyTo(playIcon)
                    linkTo(
                        start = playIcon.end,
                        startMargin = spacing.spaceMediumSmall,
                        end = addPlaylist.start,
                        endMargin = spacing.spaceMedium,
                        bias = 0f
                    )
                    width = Dimension.preferredWrapContent
                }
            )
        }
    }
}