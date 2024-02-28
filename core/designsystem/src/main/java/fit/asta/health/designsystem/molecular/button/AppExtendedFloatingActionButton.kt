package fit.asta.health.designsystem.molecular.button


import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import fit.asta.health.designsystem.AppTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
                AppExtendedFloatingActionButton(
                    text = { CaptionTexts.Level1(text = "Add") },
                    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    onClick = {  })
            }
        }
    }
}


@Composable
fun AppExtendedFloatingActionButton(
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = true,
    shape: Shape = AppTheme.shape.level1,
    containerColor: Color = AppTheme.colors.tertiaryContainer,
    contentColor: Color = AppTheme.colors.onTertiaryContainer,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(
        defaultElevation = AppTheme.elevation.level3,
        pressedElevation = AppTheme.elevation.level3,
        focusedElevation = AppTheme.elevation.level3,
        hoveredElevation = AppTheme.elevation.level4
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },

    ) {
    ExtendedFloatingActionButton(
        text = text,
        icon = icon,
        onClick = onClick,
        modifier = modifier,
        expanded = expanded,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource
    )
}