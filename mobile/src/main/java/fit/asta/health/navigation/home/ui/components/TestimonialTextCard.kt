package fit.asta.health.navigation.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.navigation.home.model.dummy.TestimonialsData

@Composable
fun TestimonialTextCard(
    interExtraBoldFontFamily: FontFamily,
    sliderDataPages: TestimonialsData,
    interFontFamily: FontFamily
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))

    ) {
        Box(
            modifier = Modifier
                .padding(all = 16.dp)
        ) {
            FontFamily(
                Font(R.font.inter_light, FontWeight.Light)
            )
            Column {
                Box {
                    Text(
                        text = "❝",
                        fontFamily = interExtraBoldFontFamily,
                        fontSize = 20.sp,
                        color = Color(0xFF0277BD)
                    )
                }
                Text(
                    text = sliderDataPages.clientDescription,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Thin
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "❞",
                        fontFamily = interExtraBoldFontFamily,
                        fontSize = 20.sp,
                        color = Color(0xFF0277BD)
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.size(width = 80.dp, height = 80.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.testimonial_person1),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = sliderDataPages.clientName,
                                fontFamily = interFontFamily,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                text = sliderDataPages.clientJob,
                                fontFamily = interFontFamily,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}