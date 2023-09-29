package fit.asta.health.designsystem.molecular.chip

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            Column {
                AppFilterChip(
                    onClick = {},
                    textToShow = "Enabled",
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.TrackChanges,
                    selected = true
                )

                AppFilterChip(
                    enabled = false,
                    onClick = {},
                    textToShow = "Disabled",
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.TrackChanges,
                    selected = false
                )
            }
        }
    }
}

/**
 * [AppFilterChip] The purpose of this wrapper function might be to provide a more
 * user-friendly or simplified interface to the FilterChip composable
 *
 * @param modifier the [Modifier] to be applied to this chip
 * @param selected whether this chip is selected or not
 * @param onClick called when this chip is clicked
 * @param textToShow text String for this chip
 * @param enabled controls the enabled state of this chip.
 * @param leadingIcon optional icon at the start of the chip, preceding the [textToShow] text.
 * @param leadingIconDes This is the description for the leading Icon
 * @param trailingIcon optional icon at the end of the chip
 * @param trailingIconDes This is the description for the trailing icon
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFilterChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    textToShow: String,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null
) {

    val leadingIconComposable: @Composable (() -> Unit) = {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes
            )
        }
    }

    val trailingIconComposable: @Composable (() -> Unit) = {
        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes
            )
        }
    }

    val textLabelComposable: @Composable (() -> Unit) = {
        if (enabled)
            CaptionTexts.Level2(text = textToShow)
        else
            CaptionTexts.Level2(
                text = textToShow,
                color = AppTheme.colors.onSurface.copy(alpha = .35f)
            )
    }

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = textLabelComposable,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (leadingIcon != null) leadingIconComposable else null,
        trailingIcon = if (trailingIcon != null) trailingIconComposable else null,
        shape = AppTheme.appShape.large,
        colors = FilterChipDefaults.filterChipColors(),
        elevation = FilterChipDefaults.filterChipElevation(),
        border = FilterChipDefaults.filterChipBorder()
    )
}