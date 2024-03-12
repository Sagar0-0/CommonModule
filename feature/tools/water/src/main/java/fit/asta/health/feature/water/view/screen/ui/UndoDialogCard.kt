package fit.asta.health.feature.water.view.screen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.dialog.AppAlertDialog
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel

@Composable
fun UndoDialogCard(
    name : String,
    viewModel : WaterToolViewModel = hiltViewModel()
) {
    var showDialog by viewModel.showUndoDialog
    AppAlertDialog(onDismissRequest = { /*TODO*/ },
        title = {
            HeadingTexts.Level2(text = "This Action Cannot be Undone", textAlign = TextAlign.Center)
        },
        text = {
               BodyTexts.Level2(text = "Are You Sure, You want to undo the consumption for $name")
        },
        confirmButton = {
            AppFilledButton(textToShow = "Delete") {

            }
        },
        dismissButton = {
            AppFilledButton(textToShow = "Decline") {
                showDialog = false
            }
        })
}