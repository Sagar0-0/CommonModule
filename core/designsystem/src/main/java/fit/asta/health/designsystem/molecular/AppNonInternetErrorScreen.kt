package fit.asta.health.designsystem.molecular

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.cards.AppCard
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
        AppNonInternetErrorScreen()
    }
}


/**
 * This composable function is used to show the Error Screen to the User when the error or issues is
 * not an Internet Issue or error.
 *
 * Check [AppInternetErrorDialog] - For composable function which should be used during Internet
 * error use
 *
 * @param modifier This is to pass modifications from the Parent Composable to the Child
 * @param issueHeading This is the Heading of the issue/error which would be shown as a Heading below
 * the Image
 * @param issueDescription This is the description of the Issue/Error which would be shown as a
 * description below the [issueHeading]
 * @param imageId This is the Image Id which would be shown in the Dialog
 * @param onTryAgain This function would be executed when the retry button would be clicked
 */
@Composable
fun AppNonInternetErrorScreen(
    modifier: Modifier = Modifier,
    issueHeading: String = "Whoops !!",
    issueDescription: String = "Second Internet connection was found. Check your connection or try again.",
    imageId: Int = R.drawable.server_error,
    onTryAgain: () -> Unit = {}
) {
    ErrorContent(
        imageId = imageId,
        issueHeading = issueHeading,
        issueDescription = issueDescription,
        onTryAgain = onTryAgain,
        modifier = modifier
    )
}


/**
 * This function provides the Contents inside the [AppNonInternetErrorScreen] Composable
 *
 * @param modifier This is for the parent function to pass modifications to the child
 * @param issueHeading This is the Heading of the issue/error which would be shown as a Heading below
 * the Image
 * @param issueDescription This is the description of the Issue/Error which would be shown as a
 * description below the [issueHeading]
 * @param imageId This is the Image Id which would be shown in the Dialog
 * @param onTryAgain This function would be executed when the retry button would be clicked
 */
@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    issueHeading: String,
    issueDescription: String,
    imageId: Int,
    onTryAgain: () -> Unit
) {

    AppCard(shape = AppTheme.shape.level1) {

        // Box is kept to vertically center the Column and its contents
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(AppTheme.spacing.level2),
            contentAlignment = Alignment.Center
        ) {

            // Column is kept to show the Image, issueHeading, issues Desc and the retry Option
            Column(
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
                    text = issueHeading,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Issues Description Text
                BodyTexts.Level3(
                    text = issueDescription,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Try Again Button
                AppTonalButton(
                    textToShow = "Try Again",
                    modifier = Modifier.fillMaxWidth(1F),
                    onClick = onTryAgain
                )
            }
        }
    }
}