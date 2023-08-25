package fit.asta.health.testimonials.ui.list.view

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
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.theme.aspectRatio
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.navigation.home.view.component.TstTxtLayout
import fit.asta.health.testimonials.data.model.Media
import fit.asta.health.testimonials.data.model.Testimonial

@Composable
fun TstViewImgLayout(tstImageMedia: Testimonial) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {
        BeforeAndCardLayout(tstImageMedia.media)
        Spacer(modifier = Modifier.height(spacing.medium))
        TstTxtLayout(tstImageMedia)
    }
}

@Composable
fun BeforeAndCardLayout(tstImageMedia: List<Media>) {

    if (tstImageMedia.isNotEmpty()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
            tstImageMedia.forEach {
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp, color = MaterialTheme.colorScheme.surface
                        )
                        .aspectRatio(aspectRatio.square)
                        .weight(1f)
                ) {
                    AppDefServerImg(
                        model = getImgUrl(url = it.url),
                        contentDescription = "Before and After Images",
                        modifier = Modifier.aspectRatio(aspectRatio.square)
                    )
                }
            }
        }
    } else {
        AppTexts.TitleSmall(text = "NO MEDIA FILE PRESENT", color = MaterialTheme.colorScheme.error)
    }

}