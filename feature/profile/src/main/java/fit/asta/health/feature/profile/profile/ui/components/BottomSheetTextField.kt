package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTextField(
    isVisible: Boolean,
    sheetState: SheetState,
    label: String,
    text: String,
    onDismissRequest: () -> Unit,
    onSaveClick: (String) -> Unit
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = text))
    }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(isVisible) {
        if (isVisible) {
            focusRequester.requestFocus()
            //Move cursor at the end
            textFieldValue = textFieldValue.copy(
                selection = TextRange(textFieldValue.text.length)
            )
        }
    }

    AppModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetVisible = isVisible,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
                .focusRequester(focusRequester),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            CaptionTexts.Level2(text = label)
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
            )
            BottomSheetSaveButtons(onSave = { onSaveClick(textFieldValue.text) }) {
                onDismissRequest()
            }
        }
    }
}