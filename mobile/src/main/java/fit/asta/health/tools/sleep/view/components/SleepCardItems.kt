package fit.asta.health.tools.sleep.view.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun SleepCardItems(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = R.drawable.sleep_factors,
    textToShow: String,
    onClick: (() -> Unit)? = null
) {

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (icon != null)
                AppIcon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )

            TitleTexts.Level2(text = textToShow)
        }
    }
}