package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts

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

    // Parent Composable of the small Tracks UI
    AppCard(
        modifier = Modifier
            .padding(AppTheme.spacing.level1)
            .width(LocalConfiguration.current.screenWidthDp.dp / 2 - 20.dp),
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Track Image
            AppNetworkImage(
                model = imageUri,
                contentDescription = "Track Image",
                contentScale = ContentScale.FillHeight
            )

            // track Name
            BodyTexts.Level3(
                text = name,
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .padding(
                        start = AppTheme.spacing.level2,
                        top = AppTheme.spacing.level1,
                        end = AppTheme.spacing.level1,
                        bottom = AppTheme.spacing.level1
                    ),
                maxLines = 2
            )
        }
    }
}