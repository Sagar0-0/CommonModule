package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDialog
import fit.asta.health.designsystem.components.generic.AppTexts
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
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    AppTexts.DisplaySmall(text = stringResource(id = R.string.alert))
                }
                AppTexts.BodyLarge(text = content)
                Row {
                    AppButtons.AppOutlinedButton(
                        onClick = onDismiss,
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        AppTexts.BodyLarge(text = stringResource(id = R.string.cancel))
                    }
                    AppButtons.AppStandardButton(
                        onClick = onDone,
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        AppTexts.BodyLarge(text = actionButton)
                    }
                }
            }
        }
    }
}