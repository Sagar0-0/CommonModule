package fit.asta.health.designsystem.molecular.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.designsystem.AppTheme


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
                AppAlertDialog(
                    onDismissRequest = {},
                    confirmButton = {}
                )
            }
        }
    }
}


/**
 * Dialogs provide important prompts in a user flow. They can require an action, communicate
 * information, or help users accomplish a task.
 *
 * ![Basic dialog image](https://developer.android.com/images/reference/androidx/compose/material3/basic-dialog.png)
 *
 * @param modifier the [Modifier] to be applied to this dialog
 * @param onDismissRequest called when the user tries to dismiss the Dialog by clicking outside
 * or pressing the back button. This is not called when the dismiss button is clicked.
 * @param confirmButton button which is meant to confirm a proposed action, thus resolving what
 * triggered the dialog. The dialog does not set up any events for this button so they need to be
 * set up by the caller.
 * @param dismissButton button which is meant to dismiss the dialog. The dialog does not set up any
 * events for this button so they need to be set up by the caller.
 * @param icon optional icon that will appear above the [title] or above the [text], in case a
 * title was not provided.
 * @param title title which should specify the purpose of the dialog. The title is not mandatory,
 * because there may be sufficient information inside the [text].
 * @param text text which presents the details regarding the dialog's purpose.
 * @param shape defines the shape of this dialog's container
 * @param containerColor the color used for the background of this dialog. Use [Color.Transparent]
 * to have no color.
 * @param iconContentColor the content color used for the icon.
 * @param titleContentColor the content color used for the title.
 * @param textContentColor the content color used for the text.
 * @param tonalElevation when [containerColor] is [ColorScheme.surface], a translucent primary color
 * overlay is applied on top of the container. A higher tonal elevation value will result in a
 * darker color in light theme and lighter color in dark theme. See also: [Surface].
 * @param properties typically platform specific properties to further configure the dialog.
 */
@Composable
fun AppAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    text: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    shape: Shape = AppTheme.shape.level3,
    containerColor: Color = AppTheme.colors.surface,
    iconContentColor: Color = AppTheme.colors.secondary,
    titleContentColor: Color = AppTheme.colors.onSurface,
    textContentColor: Color = AppTheme.colors.onSurfaceVariant,
    tonalElevation: Dp = AppTheme.elevation.level3,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        text = text,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties
    )
}