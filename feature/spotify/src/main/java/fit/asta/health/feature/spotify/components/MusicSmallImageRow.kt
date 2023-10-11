package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

/**
 * This function draws the Playlist Item Container in the screen
 *
 * @param imageUri This is the uri from where we can fetch the image
 * @param name This is the name of the playlist
 * @param onClick This function is executed when the Composable is clicked
 * @param secondaryText This is the 2nd Text in the UI
 */
@Composable
fun MusicSmallImageRow(
    imageUri: String?,
    name: String,
    secondaryText: String,
    onClick: () -> Unit
) {

    // Parent Composable of the Playlist UI
    Row(
        modifier = Modifier
            .padding(vertical = AppTheme.spacing.level2)
            .fillMaxWidth()

            // Redirecting the User to Spotify App playlist
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Showing the Loader or the Image accordingly
        AppNetworkImage(
            model = imageUri,
            contentDescription = "Playlist Image",
            modifier = Modifier.size(AppTheme.imageSize.level8)
        )

        // showing the playlist type and the owner name to the user
        Column(
            modifier = Modifier.padding(horizontal = AppTheme.spacing.level2),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            // playlist Name
            BodyTexts.Level3(text = name, maxLines = 2)

            // Secondary Text
            if (secondaryText.isNotEmpty())
                CaptionTexts.Level3(
                    text = secondaryText,
                    modifier = Modifier.alpha(AppTheme.alphaValues.level3)
                )
        }
    }
}