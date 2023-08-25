package fit.asta.health.designsystem.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.samples.apps.nowinandroid.core.designsystem.icon.AstaIcons
import fit.asta.health.designsystem.atomic.AstaTypography
import fit.asta.health.designsystem.atomic.LocalColors

/**
 * Asta filter chip with included leading checked icon as well as text content slot.
 *
 * @param selected Whether the chip is currently checked.
 * @param onSelectedChange Called when the user clicks the chip and toggles checked.
 * @param modifier Modifier to be applied to the chip.
 * @param enabled Controls the enabled state of the chip. When `false`, this chip will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The text label content.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AstaFilterChip(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        label = {
            ProvideTextStyle(value = AstaTypography.labelSmall) {
                label()
            }
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = AstaIcons.Check,
                    contentDescription = null,
                )
            }
        } else {
            null
        },
        shape = CircleShape,
        border = FilterChipDefaults.filterChipBorder(
            borderColor = LocalColors.current.onBackground,
            selectedBorderColor = LocalColors.current.onBackground,
            disabledBorderColor = LocalColors.current.onBackground.copy(
                alpha = AstaChipDefaults.DisabledChipContentAlpha,
            ),
            disabledSelectedBorderColor = LocalColors.current.onBackground.copy(
                alpha = AstaChipDefaults.DisabledChipContentAlpha,
            ),
            selectedBorderWidth = AstaChipDefaults.ChipBorderWidth,
        ),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = LocalColors.current.onBackground,
            iconColor = LocalColors.current.onBackground,
            disabledContainerColor = if (selected) {
                LocalColors.current.onBackground.copy(
                    alpha = AstaChipDefaults.DisabledChipContainerAlpha,
                )
            } else {
                Color.Transparent
            },
            disabledLabelColor = LocalColors.current.onBackground.copy(
                alpha = AstaChipDefaults.DisabledChipContentAlpha,
            ),
            disabledLeadingIconColor = LocalColors.current.onBackground.copy(
                alpha = AstaChipDefaults.DisabledChipContentAlpha,
            ),
            selectedContainerColor = LocalColors.current.primaryContainer,
            selectedLabelColor = LocalColors.current.onBackground,
            selectedLeadingIconColor = LocalColors.current.onBackground,
        ),
    )
}

/**
 * Asta chip default values.
 */
object AstaChipDefaults {
    // TODO: File bug
    // FilterChip default values aren't exposed via FilterChipDefaults
    const val DisabledChipContainerAlpha = 0.12f
    const val DisabledChipContentAlpha = 0.38f
    val ChipBorderWidth = 1.dp
}
