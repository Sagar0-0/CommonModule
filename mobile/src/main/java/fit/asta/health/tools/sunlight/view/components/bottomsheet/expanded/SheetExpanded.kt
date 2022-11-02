package fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SheetExpanded(
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
        content()
    }
}