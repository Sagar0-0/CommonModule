package fit.asta.health.designsystem.molecular.button

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMultiChoiceSegmentedButtonRow(
    modifier: Modifier = Modifier,
    items: List<@Composable () -> Unit>,
    checked: Int,
    onCheckedChange: (Int) -> Unit,
) {
    /*MultiChoiceSegmentedButtonRow(modifier = modifier) {
        items.forEachIndexed { index, value ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = items.size),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AppTheme.colors.secondaryContainer,
                    activeContentColor = AppTheme.colors.onSecondaryContainer,
                    inactiveContainerColor = AppTheme.colors.surfaceContainer,
                    inactiveContentColor = AppTheme.colors.onSurface,
                ),
                onCheckedChange = {
                    onCheckedChange(index)
                },
                checked = checked == index
            ) {
                value()
            }
        }
    }*/

}