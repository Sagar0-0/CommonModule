package fit.asta.health.thirdparty.spotify.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


/**
 * This function draws a Artists in the Screen by loading the Image provided from the parent
 * function
 *
 * @param imageUri This variables contains the url of the Image
 * @param artistName This function contains the name of the Artist
 * @param artistsUri This contains the URI which can be used to redirect the User to spotify app and
 * play the song
 * @param onClick This function takes the [artistsUri] and lets the parent decide what to do when it is
 * clicked
 */
@Composable
fun MusicArtistsUI(
    imageUri: String?,
    artistName: String,
    artistsUri: String,
    onClick: (trackUri: String) -> Unit
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
                onClick(artistsUri)
            }
    ) {

        // Showing the Loader or the Image accordingly
        Box(
            modifier = Modifier
                .size(widthOfImage),
            contentAlignment = Alignment.Center
        ) {

            // Circular Progress Bar
            if (painter.state.painter == null)
                CircularProgressIndicator()

            // Artists Image
            Image(
                painter = painter,
                contentDescription = "Artist Image",
                modifier = Modifier
                    .size(widthOfImage)
                    .clip(CircleShape)
            )
        }

        Spacer(Modifier.height(8.dp))

        // Artists Name
        Text(
            text = artistName,

            modifier = Modifier
                .width(widthOfImage),

            maxLines = 2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}