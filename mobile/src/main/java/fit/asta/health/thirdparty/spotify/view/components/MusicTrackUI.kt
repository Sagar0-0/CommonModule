package fit.asta.health.thirdparty.spotify.view.components

import android.content.res.Configuration
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
        ) {}
    }
}

/**
 * This function draws a Track Detail in the Screen by loading the Image provided from the parent
 * function
 *
 * @param imageUri This variables contains the url of the Image
 * @param headerText This function contains the name of the track
 * @param secondaryTexts This function contains the names of the Artists for this particular track
 * @param uri This contains the URI which can be used to redirect the User to spotify app and
 * play the song
 * @param onClick This function takes the trackUri and lets the parent decide what to do when it is
 * clicked
 */
@Composable
fun MusicTrack(
    imageUri: String?,
    headerText: String,
    secondaryTexts: List<ArtistX>,
    uri: String?,
    onClick: (trackUri: String?) -> Unit
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
                onClick(uri)
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

            // Track Image
            Image(
                painter = painter,
                contentDescription = "Track Image",
                modifier = Modifier
                    .size(widthOfImage)
            )
        }

        Spacer(Modifier.height(8.dp))

        // track Name
        Text(
            text = headerText,

            modifier = Modifier
                .width(widthOfImage),

            maxLines = 1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        // Formulating the names of the Artist
        val artistsNames = secondaryTexts
            .map { it.name }
            .toString()
            .filter { it != '[' }
            .filter { it != ']' }


        val textToShow = if (artistsNames.length > 20)
            "${artistsNames.substring(0, 18)}..."
        else
            artistsNames

        // Artist Names
        Text(
            text = textToShow,

            modifier = Modifier
                .width(widthOfImage),

            maxLines = 1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
            fontSize = 14.sp
        )
    }
}