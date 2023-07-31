package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.common.ui.components.generic.AppDefCard
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.testimonials.model.domain.Testimonial

@Composable
fun TstTxtCard(testimonialsDataPage: Testimonial) {
    AppDefCard(content = {
        Column(modifier = Modifier.padding(all = spacing.medium)) {
            AppTexts.BodyLarge(
                cardTitle = "❝", color = MaterialTheme.colorScheme.primary
            )
            AppTexts.HeadlineSmall(
                cardTitle = testimonialsDataPage.testimonial, textAlign = TextAlign.Center
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                AppTexts.BodyLarge(cardTitle = "❞", color = MaterialTheme.colorScheme.primary)
            }
            ArtistCard(testimonialsDataPage)
        }
    }, modifier = Modifier.fillMaxSize())
}