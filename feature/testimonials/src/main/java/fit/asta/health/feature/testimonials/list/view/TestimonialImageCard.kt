package fit.asta.health.feature.testimonials.list.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.testimonials.components.TstTxtLayout

@Composable
fun TstViewImgLayout(tstImageMedia: Testimonial) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level3)
    ) {
        BeforeAndCardLayout(tstImageMedia.media)
        Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
        TstTxtLayout(tstImageMedia)
    }
}

@Composable
fun BeforeAndCardLayout(tstImageMedia: List<Media>) {

    if (tstImageMedia.isNotEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            tstImageMedia.forEach {
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp, color = AppTheme.colors.surface
                        )
                        .aspectRatio(AppTheme.aspectRatio.square)
                        .weight(1f)
                ) {
                    AppNetworkImage(
                        model = getImgUrl(url = it.url),
                        contentDescription = "Before and After Images",
                        modifier = Modifier.aspectRatio(AppTheme.aspectRatio.square)
                    )
                }
            }
        }
    } else {
        TitleTexts.Level3(text = "NO MEDIA FILE PRESENT", color = AppTheme.colors.error)
    }
}