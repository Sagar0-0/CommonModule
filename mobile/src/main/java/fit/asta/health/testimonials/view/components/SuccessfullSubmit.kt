package fit.asta.health.testimonials.view.components

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.R


class FullScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                FullScreenDialog()
            }
        }
    }
}

@Preview
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FullScreenDialog() {

    // store the dialog open or close state
    var dialogOpen by remember {
        mutableStateOf(false)
    }

    val context: Context = LocalContext.current.applicationContext

    if (dialogOpen) {

        Dialog(onDismissRequest = {
            dialogOpen = false
        }, properties = DialogProperties(usePlatformDefaultWidth = false // experimental
        )) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    // medal icon
                    Icon(painter = painterResource(id = R.drawable.award_svg_150),
                        contentDescription = "Medal icon",
                        tint = Color(0xFF008CFF),
                        modifier = Modifier.size(size = 150.dp))

                    Text(text = "Congratulations!",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 26.dp),
                        fontWeight = FontWeight.Bold)

                    Text(text = "Thank you for submitting your Testimonial.\nWe're verifying it before Publishing it to the World ;)",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        fontFamily = FontFamily(Font(resId = R.font.roboto_regular,
                            weight = FontWeight.Normal)),
                        textAlign = TextAlign.Center,
                        color = Color(0xff132839),
                        lineHeight = 19.6.sp)

                    Button(onClick = {
                        Toast.makeText(context, "Continue Button", Toast.LENGTH_SHORT).show()
                    },
                        modifier = Modifier.padding(top = 20.dp),
                        shape = RoundedCornerShape(percent = 25)) {
                        Text(text = "Continue Practising",
                            fontFamily = FontFamily(Font(resId = R.font.roboto_medium,
                                weight = FontWeight.Medium)),
                            fontSize = 18.sp)
                    }
                }
            }
        }
    }

    Button(onClick = {
        dialogOpen = true
    }) {
        Text(text = "OPEN DIALOG")
    }

}

