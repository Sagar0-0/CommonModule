package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.designsystemx.AstaThemeX

@Composable
fun TextSelection(
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = imageIcon,
                        contentDescription = null,
                        tint = color ?: MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = color ?: MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectableText(arrowTitle,btnEnabled,onNavigateAction)
                Box(contentAlignment = Alignment.Center) {
                    IconButton(enabled = btnEnabled, onClick = onNavigateAction) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectableText(arrowTitle: String,btnEnabled: Boolean=true, onClick: () -> Unit = {}) {
    TextButton(onClick = onClick, enabled = btnEnabled) {
        Text(
            text = arrowTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}