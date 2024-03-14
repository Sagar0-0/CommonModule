package fit.asta.health.feature.walking.ui.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.ProgressBarInt
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.other.HandleBackPress
import fit.asta.health.feature.walking.ui.component.StepsProgressCard
import fit.asta.health.resources.drawables.R
import fit.asta.health.resources.strings.R as StringR

@Composable
fun StepsActivityScreen(
    state: ProgressState,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onStop: () -> Unit
) {
    var showPauseCustomDialogWithResult by remember { mutableStateOf(false) }
    var showStopCustomDialogWithResult by remember { mutableStateOf(false) }

    AppCard(
        modifier = Modifier.fillMaxSize(),
        shape = AppTheme.shape.level1
    ) {
        Box {
            AppNetworkImage(
                modifier = Modifier
                    .aspectRatio(AppTheme.aspectRatio.square)
                    .clip(AppTheme.shape.level1),
                model = getImageUrl(url = "/tags/Walking+Tag.png"),
                contentDescription = "cardTitle",
                contentScale = ContentScale.Crop,
            )
        }
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressBarInt(
                    modifier = Modifier.fillMaxWidth(),
                    targetDistance = state.targetDistance,
                    progress = (state.distanceTravelled.toFloat() / state.targetDistance),
                    name = "Progress",
                    postfix = "Km"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StepsProgressCard(
                        modifier = Modifier.weight(.3f),
                        title = "Steps",
                        titleValue = "${state.stepsTaken}",
                        id = R.drawable.runing
                    )

                    StepsProgressCard(
                        modifier = Modifier.weight(.3f),
                        title = "H:Min",
                        titleValue = formatDuration(state.duration),
                        id = R.drawable.clock
                    )
                    StepsProgressCard(
                        modifier = Modifier.weight(.3f),
                        title = "Calories",
                        titleValue = "${state.calorieBurned}",
                        id = R.drawable.calories
                    )
                }
                Row {
                    val str = if (state.state) {
                        StringR.string.pause.toStringFromResId()
                    } else StringR.string.resume.toStringFromResId()
                    AppFilledButton(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(8.dp),
                        textToShow = str
                    ) {
                        if (state.state) {
                            showPauseCustomDialogWithResult = true
                        } else {
                            onResume()
                        }
                    }
                    AppFilledButton(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(8.dp),
                        textToShow = StringR.string.finish.toStringFromResId()
                    ) {
                        showStopCustomDialogWithResult = true
                    }
                }
            }
        }
    }
    HandleBackPress {
        showStopCustomDialogWithResult = !showStopCustomDialogWithResult
    }
    if (showStopCustomDialogWithResult) {
        ShowCustomConfirmationDialog(
            onDismiss = {
                showStopCustomDialogWithResult = !showStopCustomDialogWithResult
            },
            onNegativeClick = {
                onStop()
                showStopCustomDialogWithResult = !showStopCustomDialogWithResult
            },
            onPositiveClick = {
                showStopCustomDialogWithResult = !showStopCustomDialogWithResult
            },
            dialogData = DialogData(
                dialogTitle = "That's too short!",
                dialogDesc = "You need to take a minimum 125 steps to convert your steps into impact.",
                negTitle = StringR.string.finish.toStringFromResId(),
                posTitle = StringR.string.continue_subs.toStringFromResId()
            )
        )
    }
    if (showPauseCustomDialogWithResult) {
        ShowCustomConfirmationDialog(
            onDismiss = {
                showPauseCustomDialogWithResult = !showPauseCustomDialogWithResult
            },
            onNegativeClick = {
                onPause()
                showPauseCustomDialogWithResult = !showPauseCustomDialogWithResult
            },
            onPositiveClick = {
                showPauseCustomDialogWithResult = !showPauseCustomDialogWithResult
            },
            dialogData = DialogData(
                dialogTitle = "Pause workout?",
                dialogDesc = "Are you sure you want to pause workout?",
                negTitle = StringR.string.pause.toStringFromResId(),
                posTitle = StringR.string.continue_subs.toStringFromResId()
            )
        )
    }
}