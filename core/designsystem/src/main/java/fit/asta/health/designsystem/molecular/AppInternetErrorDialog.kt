package fit.asta.health.designsystem.molecular

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R


// Default Preview Function
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
        AppInternetErrorDialog()
    }
}


/**
 * This composable function is used to show the Error Screen to the User when there is a Internet
 * Issues or error during the Backend API Calls
 *
 * Check [AppErrorScreen] - For composable function which should be used during issues
 * not related to the Internet
 *
 * @param modifier This is to pass modifications from the Parent Composable to the Child
 * @param title This is the Heading of the issue/error which would be shown as a Heading below
 * the Image
 * @param text This is the description of the Issue/Error which would be shown as a
 * description below the [title]
 * @param imageId This is the Image Id which would be shown in the Dialog
 * @param onTryAgain This function would be executed when the retry button would be clicked
 */
@Composable
fun AppInternetErrorDialog(
    modifier: Modifier = Modifier,
    title: String = "Whoops !!",
    text: String = "No Internet connection found. Check your mobile or Wifi settings and try again!",
    imageId: Int = R.drawable.server_error,
    onTryAgain: () -> Unit = {}
) {

    // This variable says if the dialog is Visible or not
    var isDialogVisible by remember { mutableStateOf(true) }

    // We are animating the entry and Exit of the Dialog Bars
    AnimatedVisibility(isDialogVisible) {
        AppDialog(
            onDismissRequest = {
                isDialogVisible = false
            },

            // Defines that back press and clicking outside won't remove the Dialog
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {

            // Dialog Contents which would be shown inside the Dialog
            AppCard(shape = AppTheme.shape.level1) {
                DialogContent(
                    modifier = modifier,
                    imageId = imageId,
                    title = title,
                    text = text,
                    onTryAgain = onTryAgain
                ) { isDialogVisible = false }
            }
        }
    }
}


/**
 * This function provides the Contents inside the [AppInternetErrorDialog] Composable
 *
 * @param modifier This is for the parent function to pass modifications to the child
 * @param title This is the Heading of the issue/error which would be shown as a Heading below
 * the Image
 * @param text This is the description of the Issue/Error which would be shown as a
 * description below the [title]
 * @param imageId This is the Image Id which would be shown in the Dialog
 * @param onTryAgain This function would be executed when the retry button would be clicked
 * @param onDismiss This function would be called when the user hits the dismiss Button and it would
 * remove the Dialog from the Screen
 */
@Composable
private fun DialogContent(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    imageId: Int,
    onTryAgain: () -> Unit,
    onDismiss: () -> Unit
) {

    Column(
        modifier = modifier.padding(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Local Image
        AppLocalImage(
            painter = painterResource(id = imageId),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(AppTheme.imageSize.level13)
                .fillMaxWidth()
        )

        // Issues Heading Text
        TitleTexts.Level1(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Issues Description Text
        BodyTexts.Level3(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Row containing Cancel and Try Again Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            // Cancel Button
            AppOutlinedButton(
                textToShow = "Cancel",
                modifier = Modifier.weight(1F),
                onClick = onDismiss
            )

            // Try Again Button
            AppTonalButton(
                textToShow = "Try Again",
                modifier = Modifier.weight(1F),
                onClick = onTryAgain
            )
        }
    }
}