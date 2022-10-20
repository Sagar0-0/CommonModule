package fit.asta.health.navigation.home.view.component

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
import fit.asta.health.navigation.home.model.domain.Testimonial

@Composable
fun TestimonialTextCard(
    interExtraBoldFontFamily: FontFamily,
    testimonialsDataPage: Testimonial,
    interFontFamily: FontFamily,
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
                    text = testimonialsDataPage.text,
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
                ArtistCard(testimonialsDataPage, interFontFamily)
            }
        }
    }
}