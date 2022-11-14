package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fit.asta.health.R
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.utils.getImageUrl

@Composable
fun BeforeAndAfterCard(testimonial: NetTestimonial) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(RoundedCornerShape(8.dp)),
        elevation = 10.dp) {
        Column(Modifier.fillMaxWidth()) {
            BeforeAndCardLayout(testimonial)

            Row(Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    FontFamily(Font(R.font.inter_light, FontWeight.Light))
                    Column {
                        Box {
                            Text(text = "❝", fontSize = 20.sp, color = Color(0xFF0277BD))
                        }
                        Text(text = testimonial.testimonial,
                            fontSize = 16.sp,
                            color = Color(0xFF000000),
                            fontWeight = FontWeight.Thin,
                            lineHeight = 24.sp,
                            letterSpacing = 0.5.sp)
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Text(text = "❞", fontSize = 20.sp, color = Color(0xFF0277BD))
                        }
                        UserCard(user = testimonial.user.name,
                            userOrg = testimonial.user.org,
                            userRole = testimonial.user.role,
                            url = testimonial.user.url)
                    }
                }
            }
        }
    }
}

@Composable
fun BeforeAndCardLayout(testimonial: NetTestimonial) {
    Row(Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Surface(shape = RoundedCornerShape(8.dp),
            border = BorderStroke(width = 5.dp, color = Color(0xffE0F1FF)),
            modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(0.dp)) {
                Box(Modifier.padding(2.dp), contentAlignment = Alignment.BottomCenter) {

                    val media = testimonial.media?.get(0)
                    if (media != null) {

                        AsyncImage(model = getImageUrl(media.url),
                            contentDescription = null,
                            Modifier
                                .fillMaxWidth(0.5f)
                                .height(180.dp),
                            contentScale = ContentScale.Crop)
                        Text(text = media.title,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp))
                    }
                }
                Box(Modifier.padding(2.dp), contentAlignment = Alignment.BottomCenter) {

                    val media = testimonial.media?.get(1)
                    if (media != null) {

                        AsyncImage(model = getImageUrl(media.url),
                            contentDescription = null,
                            Modifier
                                .fillMaxWidth(1f)
                                .height(180.dp),
                            contentScale = ContentScale.Crop)
                        Text(text = media.title,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}