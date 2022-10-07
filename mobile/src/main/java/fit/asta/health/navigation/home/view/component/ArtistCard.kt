package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.navigation.home.model.dummy.TestimonialsData

@Composable
fun ArtistCard(
    sliderDataPages: TestimonialsData,
    interFontFamily: FontFamily
) {
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