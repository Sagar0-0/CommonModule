package fit.asta.health.testimonials.view.list

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.components.UserCard
import fit.asta.health.common.ui.theme.*
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.player.jetpack_video.media.Media
import fit.asta.health.player.jetpack_video.media.MediaState
import fit.asta.health.player.jetpack_video.media.ResizeMode
import fit.asta.health.player.jetpack_video.media.rememberMediaState
import fit.asta.health.player.jetpack_video.video.ControllerType
import fit.asta.health.player.jetpack_video.video.component.PlayerControlViewController
import fit.asta.health.player.jetpack_video.video.component.RememberPlayer
import fit.asta.health.player.jetpack_video.video.component.SimpleController
import fit.asta.health.tools.exercise.view.video.VideoEvent
import fit.asta.health.tools.exercise.view.video.VideoState


@Composable
fun TestimonialsVideoCard(testimonial: Testimonial, player: Player) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(cardElevation.small),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {

            PlayVideoLayout(testimonial, player)

            UserCard(
                user = testimonial.user.name,
                userOrg = testimonial.user.org,
                userRole = testimonial.user.role,
                url = testimonial.user.url
            )
        }
    }

}

@Composable
fun PlayVideoLayout(testimonial: Testimonial, player: Player) {

    val tstVideoMedia = testimonial.media
    val state = rememberMediaState(player = player)

    Row(
        Modifier.fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Surface(
                shape = MaterialTheme.shapes.medium, border = BorderStroke(
                    width = 5.dp, color = MaterialTheme.colorScheme.primaryContainer
                ), modifier = Modifier.fillMaxWidth()
            ) {
                if (tstVideoMedia.isNotEmpty()) {
                    tstVideoMedia.forEach {
                        VideoView(
                            videoUri = getImageUrl(url = it.url),
                            state = state,
                            player = player
                        )
                    }
                } else {
                    Text(text = "MEDIA FILE NOT FOUND", modifier = Modifier.align(Alignment.Center))
                }

            }
        }
    }

    Spacer(modifier = Modifier.height(spacing.medium))
}

@Composable
fun PlayButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .clip(CircleShape)
            .size(iconButtonSize.extraLarge1)
            .background(color = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.asana_play_img),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(iconSize.mediumSmall)
        )
    }
}

@Composable
fun VideoView(
    videoUri: String,
    player: Player,
    uiState: VideoState = VideoState(
        controllerType = ControllerType.Simple,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    ),
    state: MediaState
) {

    player.apply {
        setMediaItem(
            androidx.media3.common.MediaItem.Builder()
                .setUri(videoUri.toUri()).setMediaMetadata(
                    MediaMetadata.Builder()
                        .setArtworkUri("https://img2.asta.fit/tags/Breathing+Tag.png".toUri())
                        .build()
                )
                .build()
        )
        playWhenReady = false
        prepare()
        stop()
    }
    RememberPlayer(
        onPause = {
            player.stop()
        },
        onStart = {
            player.play()
        }
    )

    Box(
        modifier = Modifier
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Media(
            state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(Color.Black),
            surfaceType = uiState.surfaceType,
            resizeMode = uiState.resizeMode,
            keepContentOnPlayerReset = uiState.keepContentOnPlayerReset,
            useArtwork = uiState.useArtwork,
            showBuffering = uiState.showBuffering,
            buffering = {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            errorMessage = { error ->
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        error.message ?: "",
                        modifier = Modifier
                            .background(Color(0x80808080), RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            },
            controllerHideOnTouch = uiState.controllerHideOnTouchType,
            controllerAutoShow = uiState.controllerAutoShow,
            controller = when (uiState.controllerType) {
                ControllerType.None -> null
                ControllerType.Simple -> @Composable { state1 ->
                    SimpleController(state1, Modifier.fillMaxSize())
                }

                ControllerType.PlayerControlView -> @Composable { state1 ->
                    PlayerControlViewController(state1, Modifier.fillMaxSize())
                }
            }
        )
    }
}