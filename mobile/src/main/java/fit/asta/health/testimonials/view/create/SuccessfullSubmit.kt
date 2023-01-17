package fit.asta.health.testimonials.view.create

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.R
import fit.asta.health.ui.spacing


class FullScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

        Dialog(
            onDismissRequest = {
                dialogOpen = false
            }, properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // medal icon
                    Icon(
                        painter = painterResource(id = R.drawable.award_svg_150),
                        contentDescription = "Medal icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
//                            .size(size = 150.dp)
                            .defaultMinSize(min(150.dp, 150.dp))
                    )

                    Text(
                        text = "Congratulations!",
                        modifier = Modifier.padding(top = spacing.extraMedium),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Thank you for submitting your Testimonial.\nWe're verifying it before Publishing it to the World ;)",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(
                            top = spacing.medium, start = spacing.medium, end = spacing.medium
                        ),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        lineHeight = 19.6.sp
                    )

                    Button(
                        onClick = {
                            Toast.makeText(context, "Continue Button", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.padding(top = spacing.extraMedium),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Continue Practising",
                            style = MaterialTheme.typography.labelLarge,
                        )
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

