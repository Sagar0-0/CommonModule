package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun TextSelection(
    title: String,
    arrowTitle: String = "",
    imageIcon: ImageVector,
    color: Color? = null,
    onNavigateAction: () -> Unit,
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onNavigateAction
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(end = AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    imageVector = imageIcon,
                    contentDescription = null,
                    tint = color ?: AppTheme.colors.primary
                )
                TitleTexts.Level2(
                    maxLines = 1,
                    text = title,
                    color = color ?: AppTheme.colors.onSecondaryContainer
                )
            }

            CaptionTexts.Level1(
                modifier = Modifier.weight(1f),
                text = arrowTitle,
                maxLines = 1
            )
            AppIcon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        }
    }

}

@Composable
fun DateSelection(
    title: String,
    arrowTitle: String = "",
    imageIcon: ImageVector,
    color: Color? = null,
    onNavigateAction: () -> Unit,
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onNavigateAction
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                AppIcon(
                    imageVector = imageIcon,
                    contentDescription = null,
                    tint = color ?: AppTheme.colors.primary
                )
                TitleTexts.Level2(
                    maxLines = 1,
                    text = title,
                    color = color ?: AppTheme.colors.onSecondaryContainer
                )
            }

            CaptionTexts.Level1(text = arrowTitle, maxLines = 1)
        }
    }

}

@Composable
fun SelectableText(arrowTitle: String, btnEnabled: Boolean = true, onClick: () -> Unit = {}) {
    AppTextButton(textToShow = arrowTitle, enabled = btnEnabled, onClick = onClick)
}