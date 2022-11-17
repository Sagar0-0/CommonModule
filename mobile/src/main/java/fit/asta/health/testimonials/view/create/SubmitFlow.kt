package fit.asta.health.testimonials.view.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fit.asta.health.R
import fit.asta.health.testimonials.view.components.SuccessfulCard
import fit.asta.health.tools.sunlight.view.components.BottomSheetButton

@Composable
fun CustomDialogWithResultExample(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    btnTitle: String,
    btnWarn: String,
    btn1Title: String,
    btn2Title: String,
) {
    Dialog(onDismissRequest = onDismiss) {
        DialogContent(onNegativeClick = onNegativeClick,
            onPositiveClick = onPositiveClick,
            btnTitle = btnTitle,
            btnWarn = btnWarn,
            btn1Title = btn1Title,
            btn2Title = btn2Title)
    }
}


@Composable
fun OnSuccessfulSubmit(
    onDismiss: () -> Unit,
    onPositiveClick: () -> Unit,
) {

    Dialog(onDismissRequest = onDismiss) {
        SuccessfulCard(onClick = onPositiveClick)
    }

}

@Composable
fun DialogContent(
    modifier: Modifier = Modifier,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    btnTitle: String,
    btnWarn: String,
    btn1Title: String,
    btn2Title: String,
) {
    Card(shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Column(modifier.background(Color.White)) {

            Spacer(modifier = Modifier.height(32.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_notifications),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = Color(0xff0277BD)),
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth(),

                    )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                androidx.compose.material3.Text(text = btnTitle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
                horizontalArrangement = Arrangement.Center) {
                androidx.compose.material3.Text(
                    text = btnWarn,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Box(Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    BottomSheetButton(title = btn1Title,
                        colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.height(53.dp),
                        onClick = onNegativeClick)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    BottomSheetButton(title = btn2Title,
                        modifier.height(53.dp),
                        onClick = onPositiveClick)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}