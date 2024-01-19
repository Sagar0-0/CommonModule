package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme
import kotlin.math.ceil

@Composable
fun AppVerticalGrid(
    modifier: Modifier = Modifier,
    count: Int = 3,
    horizontalPadding: Dp = AppTheme.spacing.level1,
    verticalPadding: Dp = AppTheme.spacing.level1,
    items: List<@Composable () -> Unit>
) {
    val rowsCount = remember {
        ceil((items.size.div(count.toDouble()))).toInt()
    }
    Column(modifier = modifier.fillMaxWidth()) {
        for (i in 0 until rowsCount) {
            RowWithItems(
                horizontalPadding = horizontalPadding,
                items = items.subList(i * count, count * (i + 1))//Take count elements every time
            )
            Spacer(modifier = Modifier.padding(vertical = verticalPadding))
        }
    }
}

@Composable
private fun RowWithItems(horizontalPadding: Dp, items: List<@Composable () -> Unit>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        items.forEachIndexed { index, function ->
            Box(Modifier.weight(1f)) {
                function.invoke()
            }
            if (index < items.size - 1) Spacer(modifier = Modifier.padding(horizontal = horizontalPadding))
        }
    }
}