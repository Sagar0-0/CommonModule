package fit.asta.health.feature.exercise.view.video_player

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.data.exercise.model.domain.model.VideoItem
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.player.domain.utils.AppIcons

@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
    state: SnapshotStateList<VideoItem>,
    onMusicEvents: (VideoPlayerEvent) -> Unit,
    navigateToPlayer: () -> Unit,
    onBack: () -> Unit,
) {

    val spacing = AppTheme.spacing

    val lazyListState = rememberLazyListState()

    AppScaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(title = "Exercise Video ", onBack = onBack, onHelp = {/*TODO*/ })
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(spacing.level1))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = lazyListState
            ) {

                item {
                    Spacer(Modifier.height(spacing.level1))
                }

                itemsIndexed(state) { index: Int, item: VideoItem ->
                    VideoTrackItem(
                        song = item,
                        onClick = {
                            onMusicEvents(
                                VideoPlayerEvent.PlaySound(idx = index, uri = item.mediaUri)
                            )
                            navigateToPlayer()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = AppTheme.colors.surface
                    )
                }
            }
        }
    }

}

@Composable
fun VideoTrackItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    song: VideoItem,
    backgroundColor: Color = Color.Transparent
) {
    val spacing = AppTheme.spacing
    val context = LocalContext.current

    val textColor = AppTheme.colors.onSurface


    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(backgroundColor),
        verticalArrangement = Arrangement.spacedBy(spacing.level1)
    ) {
        AppDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.level2)
        ) {
            Column(
                modifier = Modifier.weight(.5f),
                verticalArrangement = Arrangement.spacedBy(spacing.level1),
                horizontalAlignment = Alignment.Start
            ) {
                BodyTexts.Level1(
                    text = song.title,
                    color = textColor,
                )
                TitleTexts.Level2(
                    text = song.artist,
                    color = textColor,
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIcon(
                        painter = painterResource(
                            id = AppIcons.Play.resourceId
                        ),
                        contentDescription = "play",
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = false, radius = 24.dp)
                            ) {
                                onClick()
                            }
                            .size(32.dp)
                            .padding(spacing.level1)
                    )
                    BodyTexts.Level3(
                        text = song.duration,
                        color = textColor
                    )
                }
            }
            AppNetworkImage(
                model = ImageRequest.Builder(context = context)
                    .data(song.artworkUri)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(75.dp)
                    .weight(.5f)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}
