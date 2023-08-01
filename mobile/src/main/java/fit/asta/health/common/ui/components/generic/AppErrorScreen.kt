package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fit.asta.health.R
import fit.asta.health.common.ui.theme.Gradient1NoInternet
import fit.asta.health.common.ui.theme.Gradient2NoInternet
import fit.asta.health.common.ui.theme.imageHeight
import fit.asta.health.common.ui.theme.spacing

@Preview
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

    //...............................................................................
    //Full screen Custom Dialog Sample\
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
        Dialog(onDismissRequest = {
            openFullDialogCustom.value = false
        }) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp)
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

        Card(
            modifier = modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(8.dp)
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

        Image(
            painter = painterResource(id = imgID),
            contentDescription = primaryIssue,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),

            )

        Spacer(modifier = Modifier.height(20.dp))
        //.........................Text: title
        Text(
            text = primaryIssue,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        //.........................Text : description
        Text(
            text = desc,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 10.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            letterSpacing = 1.sp,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        //.........................Spacer
        Spacer(modifier = Modifier.height(24.dp))

        val cornerRadius = 16.dp
        val gradientColor = listOf(Gradient1NoInternet, Gradient2NoInternet)
        GradientButton(
            gradientColors = gradientColor,
            cornerRadius = cornerRadius,
            nameButton = btnTxt,
            roundedCornerShape = RoundedCornerShape(16.dp),
            onClick = onTryAgain
        )
    }

}
//}

//...........................................................................
@Composable
fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape,
    onClick: () -> Unit,
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, bottom = 32.dp),
        onClick = onClick,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton, fontSize = 20.sp, color = Color.White
            )
        }


    }
}


/**[AppErrorMsgCard] composable function is a UI component that displays an error message along with
 *  an associated image in a card format.
 * @param message A required String parameter that represents the error message to be displayed.
 * @param imageVector A required ImageVector parameter that represents the vector image to be displayed along with the error message.
 * */

@Composable
fun AppErrorMsgCard(message: String, imageVector: ImageVector) {
    AppDefCard(
        modifier = Modifier
            .padding(spacing.medium)
            .fillMaxWidth()
            .wrapContentHeight(), content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppDefaultIcon(
                    imageVector = imageVector,
                    contentDescription = "Error Occurred while fetching Tst List",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(imageHeight.small)
                )
                AppTexts.HeadlineSmall(text = message, color = MaterialTheme.colorScheme.onError)
            }
        }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error)
    )
}




