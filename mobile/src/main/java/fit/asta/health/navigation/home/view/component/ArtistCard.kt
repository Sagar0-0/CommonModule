package fit.asta.health.navigation.home.view.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppDefServerImg
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.testimonials.model.domain.Testimonial

@Composable
fun ArtistCard(testimonialsDataPages: Testimonial) {

    val domainName = stringResource(id = R.string.media_url)

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppDefServerImg(
            model = "$domainName${testimonialsDataPages.user.url}",
            contentDescription = "Tst Profile Pic",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(spacing.medium))
        Column(
            horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppTexts.TitleMedium(cardTitle = testimonialsDataPages.user.name)
            AppTexts.BodyMedium(cardTitle = "${testimonialsDataPages.user.role},${testimonialsDataPages.user.org}")
        }
    }

}