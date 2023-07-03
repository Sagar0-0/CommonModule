package fit.asta.health.thirdparty.spotify.view.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

/**
 * This function draws the Small Track UI like which we have in User Most Recently Played Tracks UI
 *
 * @param imageUri This is the image Uri from where the image needs to be downloaded
 * @param name This is the name of the track
 * @param uri THis is the uri where the user needs to be redirected when they choose a track
 */
@Composable
fun MusicPlayableSmallCards(
    imageUri: String?,
    name: String,
    uri: String
) {

    // request for the image and load when it is fetched from the internet
    val painter = rememberAsyncImagePainter(imageUri)

    // Current Activity of the function so that we can move to the spotify app
    val activity = LocalContext.current as Activity

    // Parent Composable of the small Tracks UI
    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp / 2 - 20.dp)

            // Redirecting the User to Spotify App
            .clickable {
                val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                activity.startActivity(spotifyIntent)
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Track Image
            Image(
                painter = painter,
                contentDescription = "Track Image",
            )

            // Circular Progress Bar
            if (painter.state.painter == null) {
                Box(
                    contentAlignment = Alignment.Center,
                    content = { CircularProgressIndicator() }
                )
            }

            val textToShow = if (name.length > 40)
                "${name.substring(0, 38)}..."
            else
                name

            // track Name
            Text(
                text = textToShow,

                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .padding(start = 8.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),

                maxLines = 2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}