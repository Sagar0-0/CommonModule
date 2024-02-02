package fit.asta.health.feature.testimonials.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        AppSurface {
            TestimonialArtistCard(
                userId = "",
                name = "Kristin Watson",
                role = "CTO",
                organization = "EkoHunt"
            )
        }
    }
}


/**
 * This composable function creates a Row with an Image and another column which contains the name
 * of the artist and then the Role and organization of the Artist.
 *
 * @param modifier This is the default modifier which is used to pass modifications from the parent
 * @param imageUrl This is the url of the image to be rendered in the start of the [Row] Composable.
 * @param name This is the name of the artist which is in the top of the [Column] composable
 * @param role This is the role of the artist which comes under the [name]
 * @param organization This is the organization of the artist which follows the [role]
 */
@Composable
fun TestimonialArtistCard(
    modifier: Modifier = Modifier,
    userId: String,
    name: String,
    role: String,
    organization: String
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Network Image of the artist
        AppNetworkImage(
            modifier = Modifier.size(AppTheme.imageSize.level9),
            model = getImgUrl(url = "/images/$userId/userProfile/$userId"),
            contentDescription = "Testimonials artist's Profile Pic"
        )

        // Contains the artist name , role and organization
        Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0)) {

            // Artist Name
            TitleTexts.Level3(text = name)

            // Artist Role and Organization
            CaptionTexts.Level3(text = "$role, $organization")
        }
    }
}