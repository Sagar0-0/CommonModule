package fit.asta.health.feedback.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R


@Preview
@Composable
fun WelcomeCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row {
                Box {
                    Image(painter = painterResource(id = R.drawable.feedback1),
                        contentDescription = null,
                        modifier = Modifier.width(150.dp), contentScale = ContentScale.Crop)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Your Feedback will help us to serve you better",
                        color = Color(0xff132839),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Your feedback is important to us. We read every feedback we get and take it seriously.",
                        fontSize = 14.sp,
                        color = Color(0xff999999),
                        textAlign = TextAlign.Left)
                }
            }
        }
    }
}