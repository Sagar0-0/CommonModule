package fit.asta.health.scheduler.ui.components

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R

@Composable
fun SpotifyMusicItem(
    imageUri: String?,
    name: String,
    secondaryText: String,
    onCardClick: () -> Unit,
    onApplyClick: () -> Unit
) {

    // request for the image and load when it is fetched from the internet
    val painter = rememberAsyncImagePainter(imageUri)

    // Parent Composable of the Playlist UI
    Row(
        modifier = Modifier
            .fillMaxWidth()

            // Redirecting the User to Spotify App playlist
            .clickable {
                onCardClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Showing the Loader or the Image accordingly
        Box(
            modifier = Modifier.weight(.2f),
            contentAlignment = Alignment.Center
        ) {

            // Circular Progress Bar
            if (painter.state.painter == null)
                CircularProgressIndicator()

            // Playlist Image
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.playlist_image),
                modifier = Modifier
                    .size(64.dp)
            )
        }

        // showing the playlist type and the owner name to the user
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(.6f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            // Song Name
            Text(
                text = name,

                maxLines = 2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis
            )

            // Artist Name
            if (secondaryText.isNotEmpty())
                Text(
                    text = secondaryText,

                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
        }

        OutlinedButton(
            onClick = onApplyClick,
            modifier = Modifier
                .weight(.3f)
        ) {
            Text(
                text = stringResource(R.string.apply),
                fontSize = 14.sp,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}