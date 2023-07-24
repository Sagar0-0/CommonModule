package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.Testimonial


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
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = testimonialsDataPage.testimonial,
                    fontSize = 20.sp,
//                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Thin
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "❞",
                        fontFamily = interExtraBoldFontFamily,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                ArtistCard(testimonialsDataPage, interFontFamily)
            }
        }
    }
}

