package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun TextSelection(
    title: String,
    testTag: String,
    arrowTitle: String = "",
    btnEnabled: Boolean = false,
    imageIcon: ImageVector,
    color: Color? = null,
    onNavigateAction: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    AppIcon(
                        imageVector = imageIcon,
                        contentDescription = null,
                        tint = color ?: AppTheme.colors.primary
                    )
                }
                TitleTexts.Level2(
                    text = title,
                    color = color ?: AppTheme.colors.onSecondaryContainer
                )
            }
        }

        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectableText(arrowTitle, btnEnabled, onNavigateAction)
                Box(contentAlignment = Alignment.Center) {
                    IconButton(
                        modifier = Modifier.testTag(testTag),
                        enabled = btnEnabled,
                        onClick = onNavigateAction
                    ) {
                        AppIcon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = AppTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DateSelection(
    title: String,
    arrowTitle: String = "",
    btnEnabled: Boolean = false,
    imageIcon: ImageVector,
    color: Color? = null,
    onNavigateAction: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            AppIcon(
                imageVector = imageIcon,
                contentDescription = null,
                tint = color ?: AppTheme.colors.primary
            )
        }
        TitleTexts.Level2(
            text = title,
            color = color ?: AppTheme.colors.onSecondaryContainer
        )

        SelectableText(arrowTitle, btnEnabled, onNavigateAction)
    }
}

@Composable
fun SelectableText(arrowTitle: String, btnEnabled: Boolean = true, onClick: () -> Unit = {}) {
    AppTextButton(textToShow = arrowTitle, enabled = btnEnabled, onClick = onClick)
}