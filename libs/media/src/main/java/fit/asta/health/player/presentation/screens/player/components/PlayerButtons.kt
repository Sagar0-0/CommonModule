package fit.asta.health.player.presentation.screens.player.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.player.domain.utils.AppIcons
import fit.asta.health.resources.strings.R


@Composable
fun PlayerButtons(
    modifier: Modifier = Modifier,
    playWhenReady: Boolean,
    play: () -> Unit,
    pause: () -> Unit,
    replay10: () -> Unit,
    forward10: () -> Unit,
    next: () -> Unit,
    previous: () -> Unit,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppIconButton(
            onClick = previous,
        ) {
            AppIcon(
                imageVector = Icons.Filled.SkipPrevious,
                modifier = Modifier.size(sideButtonSize),
                contentDescription = stringResource(id = R.string.previous)
            )
        }
        AppIconButton(
            onClick = replay10,
        ) {
            AppIcon(
                imageVector = Icons.Filled.Replay10,
                modifier = Modifier.size(sideButtonSize),
                contentDescription = stringResource(id = R.string.replay10)
            )
        }
        Crossfade(
            targetState = playWhenReady, animationSpec = spring(),
            label = ""
        ) { targetPlayWhenReady ->
            if (targetPlayWhenReady) {
                AppIconButton(
                    onClick = pause,
                ) {
                    AppIcon(
                        painter = painterResource(id = AppIcons.Pause.resourceId),
                        modifier = Modifier.size(playerButtonSize),
                        contentDescription = stringResource(id = R.string.play)
                    )
                }
            } else {
                AppIconButton(
                    onClick = play,
                ) {
                    AppIcon(
                        painter = painterResource(id = AppIcons.Play.resourceId),
                        modifier = Modifier.size(playerButtonSize),
                        contentDescription = stringResource(id = R.string.play)
                    )
                }
            }
        }
        AppIconButton(
            onClick = forward10
        ) {
            AppIcon(
                imageVector = Icons.Filled.Forward10,
                modifier = Modifier.size(sideButtonSize),
                contentDescription = stringResource(id = R.string.forward10)
            )
        }
        AppIconButton(
            onClick = next
        ) {
            AppIcon(
                imageVector = Icons.Filled.SkipNext,
                modifier = Modifier.size(sideButtonSize),
                contentDescription = stringResource(id = R.string.next)
            )
        }
    }
}