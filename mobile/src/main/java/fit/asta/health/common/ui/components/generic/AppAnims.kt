package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.theme.spacing

/** [AppDivider] is a compose method, which creates a horizontal divider line.
 * @param lineWidth (required): The width of the divider line.
 * [AppDividerLineWidth] define an object containing custom divider widths.
 */

@Composable
fun AppDivider(lineWidth: Dp) {
    Divider(
        color = MaterialTheme.colorScheme.primary,
        thickness = spacing.extraSmall,
        modifier = Modifier
            .clip(
                RoundedCornerShape(spacing.minSmall)
            )
            .width(width = lineWidth)
    )
}

object AppDividerLineWidth {
    val TstDividerWidth = 71.dp
}