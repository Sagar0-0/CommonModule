package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR


@Composable
fun WelcomeCard() {
    AppElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppLocalImage(
                painter = painterResource(id = DrawR.drawable.feedback1),
                contentDescription = "feedback image",
                modifier = Modifier
                    .size(AppTheme.imageSize.level10)
                    .weight(.4f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(.6f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                BodyTexts.Level1(
                    text = stringResource(id = StringR.string.feedback_welcome),
                    textAlign = TextAlign.Left
                )

                CaptionTexts.Level1(
                    modifier = Modifier.alpha(AppTheme.alphaValues.level3),
                    text = stringResource(id = StringR.string.your_feedback_is_important),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}