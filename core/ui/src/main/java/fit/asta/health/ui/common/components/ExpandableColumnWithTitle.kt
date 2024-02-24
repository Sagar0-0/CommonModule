package fit.asta.health.ui.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun AppExpandableColumnWithTitle(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    title: String,
    initialExpandState: Boolean = true,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(initialExpandState) }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = horizontalAlignment
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTexts.Level4(
                modifier = Modifier.padding(vertical = AppTheme.spacing.level2),
                text = title,
            )

            Spacer(modifier = Modifier.weight(1f))

            AppIcon(
                imageVector = if (expanded) Icons.Filled.ExpandLess
                else Icons.Filled.ExpandMore,
                contentDescription = null,
            )
        }
        AnimatedVisibility(visible = expanded) {
            content()
        }
    }
}