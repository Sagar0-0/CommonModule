package fit.asta.health.common.ui.components.generic

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


/**[AppDialog] This function creates a custom dialog that can be used to display various types of
 * content within an app.
 * @param onDismissRequest Executes when the user tries to dismiss the dialog.
 * @param content The content to be displayed inside the dialog.*/

@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        content = content,
        properties = DialogProperties(dismissOnClickOutside = false)
    )
}