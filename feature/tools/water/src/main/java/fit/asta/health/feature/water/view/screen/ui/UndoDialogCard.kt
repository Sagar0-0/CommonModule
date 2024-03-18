package fit.asta.health.feature.water.view.screen.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.dialog.AppAlertDialog
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.view.screen.WTEvent


@Composable
fun UndoCard(
    contentColor: Color,
    event: (WTEvent) -> Unit,
    name: String,
    consumptionExist : Boolean,
    undoQuantity : Int,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    ButtonWithColor(color = contentColor, text = "Undo",
        modifier = Modifier.fillMaxWidth()) {
        event(WTEvent.UndoConsumption(name))
        showDialog = !showDialog
    }
    // }
    AnimatedVisibility(visible = showDialog) {
        AppAlertDialog(onDismissRequest = { /*TODO*/ },
            text = {
                BodyTexts.Level2(text = "Are You Sure, You want to undo this consumption,\n" +
                        "${name} : ${undoQuantity} ml")
            },
            title = {
                HeadingTexts.Level2(text = "Undo Consumption")
            },
            confirmButton = {
                if(undoQuantity==0){
                    MToast(message = "You have not consumed ${name}")
                }
                ButtonWithColor(
                    color = contentColor, text = if(undoQuantity==0) "No Consumption" else "Undo",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    event(WTEvent.DeleteRecentConsumption(name))
                    showDialog = !showDialog
                }
            },
            dismissButton = {
                AppFilledButton(textToShow = "Dismiss",
                    modifier = Modifier.fillMaxWidth()) {
                    showDialog = !showDialog
                }
            })
    }

}
@Composable
fun MToast(message : String) {
    Toast.makeText(LocalContext.current,message, Toast.LENGTH_SHORT).show()
}