package fit.asta.health.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Preview(
    "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppScreen {
        AppDialogPopUp(
            headingText = "Alert",
            bodyText = "Are you sure you want to delete this Alarm",
            primaryButtonText = "Delete",
            secondaryButtonText = "Cancel",
            onDismiss = {},
            onDone = {}
        )
    }
}

@Composable
fun AppDialogPopUp(
    headingText: String,
    bodyText: String,
    primaryButtonText: String,
    secondaryButtonText: String,
    onDismiss: () -> Unit,
    onDone: () -> Unit,
) {
    AppDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        AppCard(shape = AppTheme.shape.level2) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                HeadingTexts.Level1(text = headingText)
                BodyTexts.Level2(text = bodyText, maxLines = 5)
                Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)) {
                    AppOutlinedButton(
                        textToShow = secondaryButtonText,
                        modifier = Modifier.weight(1F),
                        onClick = onDismiss
                    )
                    AppTonalButton(
                        textToShow = primaryButtonText,
                        modifier = Modifier.weight(1F),
                        onClick = onDone
                    )
                }
            }
        }
    }
}