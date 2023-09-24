package fit.asta.health.designsystemx.molecular.dialog

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.designsystemx.molecular.texts.LabelTexts


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AstaThemeX {
        Surface {
            AstaDialog(
                onDismissRequest = {},
                content = {
                    LabelTexts.Large(text = "Error Found here for Testing")
                }
            )
        }
    }
}


/**
 * [AstaDialog] This function creates a custom dialog that can be used to display various types of
 * content within an app.
 * @param onDismissRequest Executes when the user tries to dismiss the dialog.
 * @param content The content to be displayed inside the dialog.*/
@Composable
fun AstaDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(dismissOnClickOutside = false),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        content = content,
        properties = properties
    )
}