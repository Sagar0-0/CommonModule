package fit.asta.health.feature.profile.show.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun GenderOptionsLayout(
    cardImg: Int,
    cardType: String,
    cardValue: String,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(AppTheme.spacing.level3)
                .fillMaxWidth() // Occupy the maximum available width
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
            ) {
                AppLocalImage(
                    painter = painterResource(id = cardImg),
                    contentDescription = "Gender Images",
                    modifier = Modifier.size(AppTheme.imageSize.level5)
                )
                Column {
                    BodyTexts.Level3(text = cardType)
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    BodyTexts.Level1(text = cardValue)
                }
            }
        }
    }
}
