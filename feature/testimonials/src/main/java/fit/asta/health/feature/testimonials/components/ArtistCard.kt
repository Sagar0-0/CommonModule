package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.AppTheme

@Composable
fun ArtistCard(testimonialsDataPages: Testimonial) {

    val imageUrl = getImgUrl(url = testimonialsDataPages.user.url)

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppDefServerImg(
            model = imageUrl,
            contentDescription = "Tst Profile Pic",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
        Column(
            horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppTexts.TitleMedium(text = testimonialsDataPages.user.name)
            AppTexts.BodyMedium(
                text = "${testimonialsDataPages.user.role},${testimonialsDataPages.user.org}",
                textAlign = TextAlign.Center
            )
        }
    }

}