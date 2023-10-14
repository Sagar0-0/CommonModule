package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts


/**
 * This function draws a Artists in the Screen by loading the Image provided from the parent
 * function
 *
 * @param imageUri This variables contains the url of the Image
 * @param artistName This function contains the name of the Artist
 * @param onClick This function lets the parent decide what to do when it is
 * clicked
 */
@Composable
fun MusicArtistsUI(
    imageUri: String?,
    artistName: String,
    onClick: () -> Unit
) {

    // Width of the Image of the Track
    val widthOfImage = AppTheme.imageSize.level11

    Column(
        modifier = Modifier
            .padding(AppTheme.spacing.level1)
            .width(widthOfImage)

            // Redirecting the User to Spotify App
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {

        // Showing the Loader or the Image accordingly
        AppNetworkImage(
            model = imageUri,
            modifier = Modifier
                .size(widthOfImage)
                .clip(CircleShape),
            contentDescription = "Artist Image"
        )

        // Artists Name
        BodyTexts.Level3(
            text = artistName,
            modifier = Modifier.width(widthOfImage),
            textAlign = TextAlign.Center
        )
    }
}