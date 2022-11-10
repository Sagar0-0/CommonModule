package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fit.asta.health.R


@Composable
fun TestimonialsCardLayout(
    cardTitle: String,
    cardTst: String,
    user: String,
    userOrg: String,
    userRole: String,
    model: String,
) {

    val domainName = stringResource(id = R.string.media_url)

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

                    ArtistCard2(user, userOrg, userRole, model = "$domainName$model")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ArtistCard2(user: String, userOrg: String, userRole: String, model: String) {


    Box(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Box {
                AsyncImage(model = model,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(72.dp), contentScale = ContentScale.Crop)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = user, fontSize = 16.sp, color = Color.Black)

                Text(text = "$userRole, $userOrg", fontSize = 12.sp, color = Color(0xff8694A9))
            }
        }
    }
}