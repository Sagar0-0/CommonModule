package fit.asta.health.tools.walking.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.other.HandleBackPress
import fit.asta.health.designsystem.molecular.texts.TitleTexts
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Column {
                        TitleTexts.Level2(text = state.stepsTaken.toString())
                        TitleTexts.Level2(text = "steps")
                    }
                    Column {
                        TitleTexts.Level2(text = state.calorieBurned.toString())
                        TitleTexts.Level2(text = "calorie")
                    }

                    Column {
                        TitleTexts.Level2(text = state.dailyGoal.toString())
                        TitleTexts.Level2(text = "dailyGoal")
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Column {
                        TitleTexts.Level2(text = state.distanceTravelled.toString())
                        TitleTexts.Level2(text = "distance")
                    }
                    Column {
                        TitleTexts.Level2(text = state.duration.toString())
                        TitleTexts.Level2(text = "duration")
                    }
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