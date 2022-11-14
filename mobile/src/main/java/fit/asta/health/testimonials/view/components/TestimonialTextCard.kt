package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R


@Composable
fun TestimonialTextCard(
    cardTitle: String,
    cardTst: String,
    user: String,
    userOrg: String,
    userRole: String,
    url: String,
) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(RoundedCornerShape(8.dp)),
        elevation = 10.dp) {

        Column(Modifier.fillMaxWidth()) {
            Row(Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = cardTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    color = Color(0xff132839))
            }

            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                FontFamily(Font(R.font.inter_light, FontWeight.Light))

                Column {
                    Box {
                        Text(text = "❝", fontSize = 20.sp, color = Color(0xFF0277BD))
                    }
                    Text(text = cardTst,
                        fontSize = 16.sp,
                        color = Color(0xff404040),
                        fontWeight = FontWeight.Thin,
                        lineHeight = 24.sp,
                        letterSpacing = 0.5.sp)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(text = "❞", fontSize = 20.sp, color = Color(0xFF0277BD))
                    }

                    UserCard(user, userOrg, userRole, url = url)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

