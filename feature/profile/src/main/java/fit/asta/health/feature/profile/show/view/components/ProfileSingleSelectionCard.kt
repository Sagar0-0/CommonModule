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
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDrawImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.AppTheme

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
                .padding(AppTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppDrawImg(
                painter = painterResource(id = icon),
                contentDescription = "LifeStyle Icons",
                modifier = Modifier.size(AppTheme.imageSize.largeMedium)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.medium))
            Column {
                AppTexts.BodySmall(text = title)
                Spacer(modifier = Modifier.height(AppTheme.spacing.minSmall))
                AppTexts.BodyLarge(text = value)
            }
        }
    }
}
