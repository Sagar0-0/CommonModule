package fit.asta.health.feature.testimonials.components

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.dashedBorder
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.testimonials.utils.getOneUrl
import fit.asta.health.player.media.Media
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.media.rememberMediaState
import fit.asta.health.player.presentation.ControllerType
import fit.asta.health.player.presentation.component.VideoState
import fit.asta.health.player.presentation.component.rememberManagedExoPlayer


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        AppSurface {
            TestimonialUploadVideo(media = listOf(), onVideoSelected = {}) {

            }
        }
    }
}


/**
 * This function creates the UI to show the Video Upload Option and the Video after it is uploaded.
 *
 * @param media This variable contains the media file which are uploaded
 * @param onVideoSelected This function is invoked when the user selects a Video
 * @param onVideoDeleted This function is invoked when the user deletes a Video File
 */
@Composable
fun TestimonialUploadVideo(
    media: List<Media>,
    onVideoSelected: (Uri?) -> Unit,
    onVideoDeleted: () -> Unit
) {

    // Video Launcher
    val videoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            onVideoSelected(uri)
        }

    // Parent Composable
    Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {

        // Upload Text
        TitleTexts.Level2(text = "Upload Video")

        Box(
            modifier = Modifier
                .background(AppTheme.colors.tertiaryContainer)
                .dashedBorder(
                    width = 1.dp,
                    radius = AppTheme.spacing.level1,
                    color = AppTheme.colors.onSurface
                )
                .clickable { videoLauncher.launch("video/*") }
                .aspectRatio(AppTheme.aspectRatio.square)
                .clip(AppTheme.shape.level1),
            contentAlignment = Alignment.Center
        ) {
            if (media[0].url.isEmpty() && media[0].localUrl == null) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Upload Icon
                    AppIcon(
                        imageVector = Icons.Filled.UploadFile,
                        contentDescription = "Upload Image",
                        tint = AppTheme.colors.onTertiaryContainer
                    )

                    // Choose File Text
                    BodyTexts.Level2(
                        text = "Browse to Choose",
                        color = AppTheme.colors.onTertiaryContainer
                    )

                    // Says the type of the File
                    HeadingTexts.Level3(text = media[0].title)
                }

            } else {

                // Video UI and player
                VideoPlayerUI(
                    modifier = Modifier.align(Alignment.Center),
                    mediaItem = MediaItem
                        .Builder()
                        .setUri(getOneUrl(media[0].localUrl, media[0].url))
                        .build()
                )

                // Delete Button
                AppIconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    imageVector = Icons.Filled.Delete,
                    iconTint = AppTheme.colors.error,
                    onClick = onVideoDeleted
                )
            }
        }
    }
}


/**
 * This function is used to play the video.
 *
 * @param modifier Default to be passed from the parent composable
 * @param mediaItem This is the Url or URI of the media to be played
 * @param videoState This keeps track of the state of the VideoState
 */
@Composable
private fun VideoPlayerUI(
    modifier: Modifier = Modifier,
    mediaItem: MediaItem,
    videoState: VideoState = VideoState(
        controllerType = ControllerType.None,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    )
) {

    // Player Object
    val player by rememberManagedExoPlayer()

    // State of the player
    val state = rememberMediaState(player = player)

    // Initialising the player and starting it
    LaunchedEffect(player) {
        player?.run {
            setMediaItem(mediaItem)
            playWhenReady = false
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
            play()
        }
    }

    // This is the UI to be shown while the Video is being Played
    Media(
        modifier = modifier,
        state = state,
        surfaceType = videoState.surfaceType,
        resizeMode = videoState.resizeMode,
        keepContentOnPlayerReset = videoState.keepContentOnPlayerReset,
        useArtwork = videoState.useArtwork,
        showBuffering = videoState.showBuffering,
        buffering = {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                AppCircularProgressIndicator()
            }
        },
        errorMessage = { error ->
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                BodyTexts.Level1(text = error.message ?: "", color = AppTheme.colors.error)
            }
        },
        controllerHideOnTouch = videoState.controllerHideOnTouchType,
        controllerAutoShow = videoState.controllerAutoShow,
        controller = null
    )
}