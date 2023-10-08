package fit.asta.health.designsystem.components.generic

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R


@Preview(
    "Light Referral", heightDp = 1100
)
@Preview(
    name = "Dark Referral",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1100
)
@Composable
fun AppErrorScreen(
    modifier: Modifier = Modifier,
    primaryIssue: String = "Whoops!!",
    desc: String = "Second Internet connection was found. Check your connection or try again.",
    btnTxt: String = "Try Again",
    imgID: Int = R.drawable.server_error,
    isInternetError: Boolean = true,
    onTryAgain: () -> Unit = {},
) {
    val openFullDialogCustom = remember { mutableStateOf(true) }
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NoInternetScreen(
                modifier,
                openFullDialogCustom,
                onTryAgain,
                primaryIssue,
                desc,
                btnTxt,
                imgID,
                isInternetError
            )
        }
    }
}

@Composable
private fun NoInternetScreen(
    modifier: Modifier = Modifier,
    openFullDialogCustom: MutableState<Boolean>,
    onTryAgain: () -> Unit,
    primaryIssue: String,
    desc: String,
    btnTxt: String,
    imgID: Int,
    isInternetError: Boolean,
) {
    if (isInternetError) {
        AppDialog(onDismissRequest = {
            openFullDialogCustom.value = false
        }) {
            Surface(
                color = AppTheme.colors.primaryContainer, shape = AppTheme.shape.level2
            ) {
                ErrorScreen(
                    imgID = imgID,
                    primaryIssue = primaryIssue,
                    desc = desc,
                    btnTxt = btnTxt,
                    onTryAgain = onTryAgain
                )
            }
        }
    } else {
        AppCard(
            modifier = modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = AppTheme.elevation.level4),
            colors = CardDefaults.cardColors(containerColor = AppTheme.colors.primaryContainer),
            shape = AppTheme.shape.level2
        ) {
            ErrorScreen(
                imgID = imgID,
                primaryIssue = primaryIssue,
                desc = desc,
                btnTxt = btnTxt,
                onTryAgain = onTryAgain,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    imgID: Int,
    primaryIssue: String,
    desc: String,
    btnTxt: String,
    onTryAgain: () -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AppLocalImage(
            painter = painterResource(id = imgID),
            contentDescription = primaryIssue,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
        TitleTexts.Level1(
            text = primaryIssue,
            color = AppTheme.colors.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = AppTheme.spacing.level3)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        BodyTexts.Level3(
            text = desc, textAlign = TextAlign.Center, modifier = Modifier
                .padding(
                    top = AppTheme.spacing.level2,
                    start = AppTheme.spacing.level2,
                    end = AppTheme.spacing.level2
                )
                .fillMaxWidth(), color = AppTheme.colors.primary
        )
        //.........................Spacer
        Spacer(modifier = Modifier.height(AppTheme.spacing.level4))
        GradientButton(
            nameButton = btnTxt, onClick = onTryAgain
        )
    }

}
//}

//...........................................................................
@Composable
fun GradientButton(
    nameButton: String,
    onClick: () -> Unit,
) {
    AppFilledButton(
        textToShow = nameButton, modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = AppTheme.spacing.level5,
                end = AppTheme.spacing.level5,
                bottom = AppTheme.spacing.level5
            ), onClick = onClick, shape = RoundedCornerShape(AppTheme.spacing.level3)
    )
}


/**[AppErrorMsgCard] composable function is a UI component that displays an error message along with
 *  an associated image in a card format.
 * @param message A required String parameter that represents the error message to be displayed.
 * @param imageVector A required ImageVector parameter that represents the vector image to be displayed along with the error message.
 * */

@Composable
fun AppErrorMsgCard(message: String, imageVector: ImageVector) {
    AppCard(
        modifier = Modifier
            .padding(AppTheme.spacing.level3)
            .fillMaxWidth()
            .wrapContentHeight(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppDefaultIcon(
                    imageVector = imageVector,
                    contentDescription = "ErrorMessage Occurred while fetching Tst List",
                    tint = AppTheme.colors.surface,
                    modifier = Modifier.size(AppTheme.imageSize.level6)
                )
                HeadingTexts.Level4(text = message, color = AppTheme.colors.onError)
            }
        },
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.error)
    )
}