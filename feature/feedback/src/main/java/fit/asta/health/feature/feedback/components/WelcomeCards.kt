package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR


@Composable
fun WelcomeCard() {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            AppLocalImage(
                painter = painterResource(id = DrawR.drawable.feedback1),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.45f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))

            Column {
                TitleTexts.Level2(
                    text = stringResource(id = StringR.string.feedback_welcome),
                    textAlign = TextAlign.Left
                )

                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))

                TitleTexts.Level2(
                    text = stringResource(id = StringR.string.your_feedback_is_important),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}