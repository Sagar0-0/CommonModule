package fit.asta.health.designsystem.molecular

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable

@Composable
fun AppAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    text: @Composable() (() -> Unit)? = null,
    dismissButton: @Composable() (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = text,
        confirmButton = confirmButton,
        dismissButton = dismissButton
    )
}