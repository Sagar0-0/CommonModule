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
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun ArtistCard(testimonialsDataPages: Testimonial) {

    val imageUrl = getImgUrl(url = testimonialsDataPages.user.url)

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppNetworkImage(
            model = imageUrl,
            contentDescription = "Tst Profile Pic",
            modifier = Modifier.size(AppTheme.imageSize.level9)
        )
        Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
        Column(
            horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleTexts.Level2(text = testimonialsDataPages.user.name)
            BodyTexts.Level2(
                text = "${testimonialsDataPages.user.role},${testimonialsDataPages.user.org}",
                textAlign = TextAlign.Center
            )
        }
    }
}