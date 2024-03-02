package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.resources.strings.R

@Composable
fun AlertDialogPopUp(
    content: String,
    actionButton: String,
    onDismiss: () -> Unit,
    onDone: () -> Unit
) {
    AppDialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        AppCard(
//            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    LargeTexts.Level3(text = stringResource(id = R.string.alert))
                }
                BodyTexts.Level1(text = content)
                Row {
                    AppOutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        BodyTexts.Level1(text = stringResource(id = R.string.cancel))
                    }
                    AppTonalButton(
                        textToShow = actionButton,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                        onClick = onDone
                    )
                }
            }
        }
    }
}