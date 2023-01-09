package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.Testimonial

@Composable
fun ArtistCard(
    testimonialsDataPages: Testimonial,
    interFontFamily: FontFamily,
) {

    val domainName = stringResource(id = R.string.media_url)

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(width = 80.dp, height = 80.dp)) {
                AsyncImage(
                    model = "$domainName${testimonialsDataPages.user.url}",
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween) {
                testimonialsDataPages.user.let {
                    Text(
                        text = it.name,
                        fontFamily = interFontFamily,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                testimonialsDataPages.user.let {
                    Text(
                        text = "${it.role},${it.org}",
                        fontFamily = interFontFamily,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}