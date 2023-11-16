package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun TstBannerCard(testimonialsData: Testimonial) {
    AppCard(content = {
        TstTxtLayout(testimonialsData, modifier = Modifier.padding(all = AppTheme.spacing.level2))
    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun TstTxtLayout(
    testimonialsData: Testimonial, modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HeadingTexts.Level1(text = "❝")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            BodyTexts.Level1(text = testimonialsData.testimonial)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            HeadingTexts.Level1(text = "❞")
        }
        TestimonialArtistCard(
            imageUrl = testimonialsData.user.url,
            name = testimonialsData.user.name,
            organization = testimonialsData.user.org,
            role = testimonialsData.user.role
        )
    }
}