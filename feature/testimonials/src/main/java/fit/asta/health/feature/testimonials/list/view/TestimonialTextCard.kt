package fit.asta.health.feature.testimonials.list.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.feature.testimonials.components.TstTxtLayout

@Composable
fun TstViewTxtLayout(tstTxtMedia: fit.asta.health.data.testimonials.model.Testimonial) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {
        AppTexts.TitleMedium(
            text = tstTxtMedia.title, color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        TstTxtLayout(testimonialsData = tstTxtMedia)
    }
}

