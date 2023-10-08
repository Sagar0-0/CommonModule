package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.resources.strings.R as StringR

@Composable
fun SpotifyMusicItem(
    imageUri: String?,
    name: String,
    secondaryText: String,
    onCardClick: () -> Unit,
    onApplyClick: () -> Unit
) {

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
            AppNetworkImage(
                modifier = Modifier.size(AppTheme.imageSize.level8),
                model = imageUri,
                contentDescription = stringResource(StringR.string.playlist_image)
            )
        }

        // showing the playlist type and the owner name to the user
        Column(
            modifier = Modifier
                .padding(horizontal = AppTheme.spacing.level3)
                .weight(.6f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            // Song Name
            BodyTexts.Level3(
                text = name,
                maxLines = 2,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )

            // Artist Name
            if (secondaryText.isNotEmpty())
                CaptionTexts.Level3(
                    text = secondaryText,
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    color = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level3)
                )
        }

        AppOutlinedButton(
            modifier = Modifier.weight(.3f),
            textToShow = stringResource(StringR.string.apply),
            onClick = onApplyClick
        )
    }
}