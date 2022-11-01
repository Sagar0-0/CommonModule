package fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SheetCollapsed(
    content: @Composable RowScope.() -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
        .background(MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically) {
        content()
    }
}