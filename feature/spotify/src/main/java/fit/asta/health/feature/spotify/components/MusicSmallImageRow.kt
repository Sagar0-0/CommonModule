package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
    // request for the image and load when it is fetched from the internet
    val painter = rememberAsyncImagePainter(imageUri)

    // Parent Composable of the Playlist UI
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()

            // Redirecting the User to Spotify App playlist
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Showing the Loader or the Image accordingly
        Box(contentAlignment = Alignment.Center) {

            // Circular Progress Bar
            if (painter.state.painter == null) CircularProgressIndicator()

            // Playlist Image
            Image(
                painter = painter,
                contentDescription = "Playlist Image",
                modifier = Modifier.size(64.dp)
            )
        }

        // showing the playlist type and the owner name to the user
        Column(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            // playlist Name
            Text(
                text = name,

                maxLines = 2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis
            )

            // Secondary Text
            if (secondaryText.isNotEmpty())
                Text(
                    text = secondaryText,

                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
        }
    }
}