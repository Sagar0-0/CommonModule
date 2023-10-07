package fit.asta.health.feature.testimonials.list.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.testimonials.components.TstTxtLayout

@Composable
fun TstViewTxtLayout(tstTxtMedia: Testimonial) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level3)
    ) {
        TitleTexts.Level2(text = tstTxtMedia.title, color = AppTheme.colors.onPrimaryContainer)
        TstTxtLayout(testimonialsData = tstTxtMedia)
    }
}

