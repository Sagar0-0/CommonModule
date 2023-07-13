package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fit.asta.health.R
import fit.asta.health.common.ui.theme.Gradient1NoInternet
import fit.asta.health.common.ui.theme.Gradient2NoInternet

@Composable
fun ErrorScreenLayout(
    onTryAgain: () -> Unit = {},
    primaryIssue: String = "Whoops!!",
    desc: String = "Second Internet connection was found. Check your connection or try again.",
    btnTxt: String = "Try Again",
    imgID: Int = R.drawable.server_error,
) {

    val openFullDialogCustom = remember { mutableStateOf(true) }

    //...............................................................................
    //Full screen Custom Dialog Sample\
    NoInternetScreen(
        openFullDialogCustom, onTryAgain, primaryIssue, desc, btnTxt, imgID
    )

}

@Composable
private fun NoInternetScreen(
    openFullDialogCustom: MutableState<Boolean>,
    onTryAgain: () -> Unit,
    primaryIssue: String,
    desc: String,
    btnTxt: String,
    imgID: Int,
) {

    Dialog(onDismissRequest = {
        openFullDialogCustom.value = false
    }) {
        Surface {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
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
                    roundedCornerShape = RoundedCornerShape(
                        topStart = 30.dp, bottomEnd = 30.dp
                    ),
                    onClick = onTryAgain
                )
            }
        }
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