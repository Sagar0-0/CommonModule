package fit.asta.health.testimonials.view.list

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.components.UserCard
import fit.asta.health.testimonials.view.theme.aspectRatio
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.testimonials.view.theme.iconButtonSize
import fit.asta.health.testimonials.view.theme.iconSize
import fit.asta.health.ui.spacing
import fit.asta.health.utils.getImageUrl


@Composable
fun TestimonialsVideoCard(testimonial: Testimonial) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .clip(MaterialTheme.shapes.medium), elevation = cardElevation.small
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {

            PlayVideoLayout(testimonial)

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
fun PlayVideoLayout(testimonial: Testimonial) {

    Row(
        Modifier.fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Surface(
                shape = MaterialTheme.shapes.medium, border = BorderStroke(
                    width = 5.dp, color = MaterialTheme.colorScheme.primaryContainer
                ), modifier = Modifier.fillMaxWidth()
            ) {
                testimonial.media.forEach {
                    VideoView(videoUri = getImageUrl(url = it.url))
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
fun VideoView(videoUri: String) {

    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current).build().also { exoPlayer ->
        val mediaItem = MediaItem.Builder().setUri(Uri.parse(videoUri)).build()
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.stop()
    }

    val lifeCycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(
        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio.medium)
        )
    ) {

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                ON_PAUSE -> {
                    exoPlayer.pause()
                }
                ON_RESUME -> {
                    exoPlayer.play()
                }
                ON_CREATE -> {

                }
                ON_START -> {

                }
                ON_STOP -> {
                    exoPlayer.stop()
                }
                ON_DESTROY -> {
                    exoPlayer.stop()
                }
                ON_ANY -> {

                }
            }
        }

        val lifeCycle = lifeCycleOwner.value.lifecycle
        lifeCycle.addObserver(observer)

        onDispose {
            lifeCycle.removeObserver(observer)
            exoPlayer.stop()
            exoPlayer.release()
        }

    }

}