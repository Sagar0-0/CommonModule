package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.feedback.view.SubmitButton

@Preview
@Composable
fun SuccessfulCard(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {

    Box(contentAlignment = Alignment.TopCenter) {

        Card(modifier = modifier
            .padding(top = 50.dp)
            .height(252.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)) {
            Column(Modifier
                .fillMaxWidth()
                .padding(top = 86.dp)) {

                Row(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Thank You!",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff132839),
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Your feedback has been submitted",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff8694A9),
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(32.dp))

                SubmitButton(text = "Continue", onClick = onClick)

                Spacer(modifier = Modifier.height(16.dp))

            }
        }



        Box(modifier = Modifier
            .clip(shape = CircleShape)
            .size(100.dp)
            .background(color = Color(0xff00BC08)), contentAlignment = Alignment.Center) {
            Icon(painter = painterResource(id = R.drawable.ic_tick),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(80.dp))
        }


    }


}