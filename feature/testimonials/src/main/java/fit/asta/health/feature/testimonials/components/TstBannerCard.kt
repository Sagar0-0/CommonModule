package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.AppTheme

@Composable
fun TstBannerCard(testimonialsData: Testimonial) {
    AppCard(content = {
        TstTxtLayout(testimonialsData, modifier = Modifier.padding(all = AppTheme.spacing.level3))
    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun TstTxtLayout(
    testimonialsData: Testimonial, modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AppTexts.HeadlineLarge(
            text = "❝", color = MaterialTheme.colorScheme.primary
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            AppTexts.BodyMedium(text = testimonialsData.testimonial)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            AppTexts.HeadlineLarge(text = "❞", color = MaterialTheme.colorScheme.primary)
        }
        ArtistCard(testimonialsData)
    }
}