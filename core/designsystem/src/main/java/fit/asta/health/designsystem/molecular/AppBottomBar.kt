package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable

@Composable
fun AppBottomBar(
    floatingActionButton: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    BottomAppBar(
        floatingActionButton = floatingActionButton,
        actions = actions
    )
}