package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun ReferAndEarn() {

    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Refer Image
            AppLocalImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(AppTheme.shape.level2)
                    .size(AppTheme.imageSize.level10)
                    .padding(horizontal = AppTheme.spacing.level4),
                painter = painterResource(id = R.drawable.referral_image),
                contentDescription = "Referral",
                contentScale = ContentScale.Fit
            )

            // Referral Text
            TitleTexts.Level4(
                text = "Refer Your Friend to gain rewards!!",
                modifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level4)
                    .alpha(.7f),
                textAlign = TextAlign.Center,
                maxLines = 3
            )

            // Rate on Play Store Button
            AppFilledButton(textToShow = "Refer Us") {
                // TODO :-
            }
        }
    }
}