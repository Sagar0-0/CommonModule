package fit.asta.health.player.presentation.screens.player.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.resources.strings.R


@Composable
fun PlayerHeader(
    audioVideo: Boolean,
    onBackPress: () -> Unit,
    onAudioVideo: () -> Unit = {},
    more: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        IconButton(
            onClick = onBackPress,

            ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back_button)
            )
        }

        Row(
            modifier = Modifier.clip(RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppButtons.AppTextButton(
                onClick = onAudioVideo,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (!audioVideo) Color.Green
                    else MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                AppTexts.BodyMedium(text = "Audio")
            }
            AppButtons.AppTextButton(
                onClick = onAudioVideo,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (audioVideo) Color.Green
                    else MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                AppTexts.BodyMedium(text = "Video")
            }
        }

        IconButton(
            onClick = more,
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more)
            )
        }

    }
}

@Preview
@Composable
fun PreviewBar() {
    PlayerHeader(onBackPress = { /*TODO*/ }, onAudioVideo = {}, audioVideo = false) {

    }
}