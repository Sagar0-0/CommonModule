package fit.asta.health.thirdparty.spotify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.designsystem.components.generic.LoadingAnimation

/**
 * This function draws the Small Track UI like which we have in User Most Recently Played Tracks UI
 *
 * @param imageUri This is the image Uri from where the image needs to be downloaded
 * @param name This is the name of the track
 * @param onClick This Function is executed when the User Hits the Card
 */
@Composable
fun MusicPlayableSmallCards(
    imageUri: String?,
    name: String,
    onClick: () -> Unit
) {

    // request for the image and load when it is fetched from the internet
    val painter = rememberAsyncImagePainter(imageUri)

    // Parent Composable of the small Tracks UI
    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp / 2 - 20.dp)

            // Redirecting the User to Spotify App
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Track Image
            Image(
                painter = painter,
                contentDescription = "Track Image",
            )

            // Circular Progress Bar
            if (painter.state.painter == null) LoadingAnimation()

            // track Name
            Text(
                text = name,

                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .padding(start = 8.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),

                maxLines = 2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}