package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun ClickableTextBox(
    label: String,
    value: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = Icons.Default.Edit,
    onClick: (() -> Unit)? = null,
) {
    AppCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            AppIcon(imageVector = leadingIcon)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                CaptionTexts.Level2(
                    text = label,
                    maxLines = 1
                )
                TitleTexts.Level2(
                    text = value,
                    maxLines = 1
                )
            }
            trailingIcon?.let {
                AppIcon(imageVector = trailingIcon)
            }
        }
    }
}