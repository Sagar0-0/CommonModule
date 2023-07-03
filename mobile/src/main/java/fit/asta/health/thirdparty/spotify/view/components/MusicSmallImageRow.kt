package fit.asta.health.thirdparty.spotify.view.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

/**
 * This function draws the Playlist Item Container in the screen
 *
 * @param imageUri This is the uri from where we can fetch the image
 * @param name This is the name of the playlist
 * @param itemUri This is the URI of the playlist which redirects it to the spotify App
 * @param type This is the type of playlist
 * @param owner This is the name of the owner of the playlist
 */
@Composable
fun MusicSmallImageRow(
    imageUri: String?,
    name: String,
    itemUri: String,
    type: String,
    owner: String
) {
    // request for the image and load when it is fetched from the internet
    val painter = rememberAsyncImagePainter(imageUri)

    // Current Activity of the function so that we can move to the spotify app
    val activity = LocalContext.current as Activity

    // Parent Composable of the Playlist UI
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()

            // Redirecting the User to Spotify App playlist
            .clickable {
                val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(itemUri))
                activity.startActivity(spotifyIntent)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Showing the Loader or the Image accordingly
        Box(contentAlignment = Alignment.Center) {

            // Circular Progress Bar
            if (painter.state.painter == null)
                CircularProgressIndicator()

            // Playlist Image
            Image(
                painter = painter,
                contentDescription = "Playlist Image",
                modifier = Modifier
                    .size(64.dp)
            )
        }

        // showing the playlist type and the owner name to the user
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp),
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
                fontSize = 14.sp
            )

            // Playlist Type + Owner Name
            Text(
                text = "$type • $owner",

                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
                fontSize = 12.sp
            )
        }
    }
}