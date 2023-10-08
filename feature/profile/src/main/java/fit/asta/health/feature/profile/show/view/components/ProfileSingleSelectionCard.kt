package fit.asta.health.feature.profile.show.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun ProfileSingleSelectionCard(
    icon: Int,
    title: String,
    value: String,
) {
    AppCard {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level3),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLocalImage(
                painter = painterResource(id = icon),
                contentDescription = "LifeStyle Icons",
                modifier = Modifier.size(AppTheme.imageSize.level5)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
            Column {
                BodyTexts.Level3(text = title)
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                BodyTexts.Level1(text = value)
            }
        }
    }
}
