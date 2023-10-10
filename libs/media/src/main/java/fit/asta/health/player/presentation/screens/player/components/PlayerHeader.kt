package fit.asta.health.player.presentation.screens.player.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
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
        AppTextButton(
            onClick = onBackPress,

            ) {
            AppIcon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back_button)
            )
        }

        Row(
            modifier = Modifier.clip(RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppTextButton(
                textToShow = "Audio",
                onClick = onAudioVideo,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (!audioVideo) Color.LightGray
                    else AppTheme.colors.primary.copy(alpha = .5f),
                    contentColor = AppTheme.colors.primary
                )
            )
            AppTextButton(
                onClick = onAudioVideo,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (audioVideo) Color.LightGray
                    else AppTheme.colors.primary.copy(alpha = .5f),
                    contentColor = AppTheme.colors.primary
                ),
                textToShow = "Video"
            )
        }

        AppIconButton(
            onClick = more,
        ) {
            AppIcon(
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