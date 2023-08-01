package fit.asta.health.testimonials.view.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.view.component.TstTxtLayout
import fit.asta.health.testimonials.model.domain.Testimonial

@Composable
fun TstViewTxtLayout(tstTxtMedia: Testimonial) {
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

