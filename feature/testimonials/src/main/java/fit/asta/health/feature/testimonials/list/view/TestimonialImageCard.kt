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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.feature.testimonials.components.TstTxtLayout

@Composable
fun TstViewImgLayout(tstImageMedia: Testimonial) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(AstaThemeX.appSpacing.medium)
    ) {
        BeforeAndCardLayout(tstImageMedia.media)
        Spacer(modifier = Modifier.height(AstaThemeX.appSpacing.medium))
        TstTxtLayout(tstImageMedia)
    }
}

@Composable
fun BeforeAndCardLayout(tstImageMedia: List<Media>) {

    if (tstImageMedia.isNotEmpty()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(AstaThemeX.appSpacing.small)) {
            tstImageMedia.forEach {
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp, color = MaterialTheme.colorScheme.surface
                        )
                        .aspectRatio(AstaThemeX.appAspectRatio.square)
                        .weight(1f)
                ) {
                    AppDefServerImg(
                        model = getImgUrl(url = it.url),
                        contentDescription = "Before and After Images",
                        modifier = Modifier.aspectRatio(AstaThemeX.appAspectRatio.square)
                    )
                }
            }
        }
    } else {
        AppTexts.TitleSmall(text = "NO MEDIA FILE PRESENT", color = MaterialTheme.colorScheme.error)
    }

}