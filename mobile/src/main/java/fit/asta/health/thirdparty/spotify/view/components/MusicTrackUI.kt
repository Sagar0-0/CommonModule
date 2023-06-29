package fit.asta.health.thirdparty.spotify.view.components

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.thirdparty.spotify.model.net.common.ArtistX

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        MusicTrack(
            "",
            "",
            listOf(),
            ""
        )
    }
}

/**
 * This function draws a Track Detail in the Screen by loading the Image provided from the parent
 * function
 *
 * @param imageUri This variables contains the url of the Image
 * @param trackName This function contains the name of the track
 * @param trackArtists This function contains the names of the Artists for this particular track
 * @param trackUri This contains the URI which can be used to redirect the User to spotify app and
 * play the song
 */
@Composable
fun MusicTrack(
    imageUri: String,
    trackName: String,
    trackArtists: List<ArtistX>,
    trackUri: String
) {

    // Width of the Image of the Track
    val widthOfImage = 144.dp

    // request for the image and load when it is fetched from the internet
    val painter = rememberAsyncImagePainter(imageUri)

    // Current Activity of the function so that we can move to the spotify app
    val activity = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(widthOfImage)

            // Redirecting the User to Spotify App
            .clickable {
                val spotifyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trackUri))
                activity.startActivity(spotifyIntent)
            }
    ) {

        // Track Image
        Image(
            painter = painter,
            contentDescription = "Track Image",
            modifier = Modifier
                .size(widthOfImage)
        )

        // Circular Progress Bar
        if (painter.state.painter == null) {
            Box(
                modifier = Modifier
                    .size(widthOfImage),
                contentAlignment = Alignment.Center,
                content = { CircularProgressIndicator() }
            )
        }

        Spacer(Modifier.height(8.dp))

        // track Name
        Text(
            text = trackName,

            modifier = Modifier
                .width(widthOfImage),

            maxLines = 1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        // Formulating the names of the Artist
        val artistsNames = trackArtists
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
            fontSize = 14.sp
        )
    }
}