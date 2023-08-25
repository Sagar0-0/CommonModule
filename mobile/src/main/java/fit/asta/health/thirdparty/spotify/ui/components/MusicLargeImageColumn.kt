package fit.asta.health.thirdparty.spotify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.thirdparty.spotify.data.model.common.Artist


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
    val widthOfImage = 144.dp

    // request for the image and load when it is fetched from the internet
    val painter = rememberAsyncImagePainter(imageUri)

    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(widthOfImage)

            // Redirecting the User to Spotify App
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(spacing.extraSmall)
    ) {

        // Showing the Loader or the Image accordingly
        Box(
            modifier = Modifier
                .size(widthOfImage),
            contentAlignment = Alignment.Center
        ) {

            // Circular Progress Bar
            if (painter.state.painter == null)
                LoadingAnimation()

            // Track Image
            Image(
                painter = painter,
                contentDescription = "Track Image",
                modifier = Modifier
                    .size(widthOfImage)
            )
        }

        // track Name
        Text(
            text = headerText,

            modifier = Modifier
                .width(widthOfImage),

            maxLines = 1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis
        )

        // Formulating the names of the Artist
        val artistsNames = secondaryTexts
            .map { it.name }
            .toString()
            .filter { it != '[' }
            .filter { it != ']' }

        // Artist Names
        Text(
            text = artistsNames,

            modifier = Modifier
                .width(widthOfImage),

            maxLines = 1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}