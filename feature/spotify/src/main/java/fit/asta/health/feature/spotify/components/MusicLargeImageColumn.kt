package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import fit.asta.health.data.spotify.model.common.Artist
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts


/**
 * This function draws a Track Detail in the Screen by loading the Image provided from the parent
 * function
 *
 * @param imageUri This variables contains the url of the Image
 * @param headerText This function contains the name of the track
 * @param secondaryTexts This function contains the names of the Artists for this particular track
 * @param onClick This function takes the trackUri and lets the parent decide what to do when it is
 * clicked
 */
@Composable
fun MusicLargeImageColumn(
    imageUri: String?,
    headerText: String,
    secondaryTexts: List<Artist>,
    onClick: () -> Unit
) {

    // Width of the Image of the Track
    val widthOfImage = AppTheme.imageSize.level10

    Column(
        modifier = Modifier
            .padding(AppTheme.spacing.level2)
            .width(widthOfImage)

            // Redirecting the User to Spotify App
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {

        // Showing the Loader or the Image accordingly
        AppNetworkImage(
            model = imageUri,
            modifier = Modifier
                .size(widthOfImage),
            contentDescription = "Track Image",
        )


        // track Name
        BodyTexts.Level3(
            text = headerText,
            modifier = Modifier.width(widthOfImage),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        // Formulating the names of the Artist
        val artistsNames = secondaryTexts
            .map { it.name }
            .toString()
            .filter { it != '[' }
            .filter { it != ']' }

        // Artist Names
        CaptionTexts.Level1(
            text = artistsNames,
            modifier = Modifier
                .width(widthOfImage)
                .alpha(AppTheme.alphaValues.level3),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}