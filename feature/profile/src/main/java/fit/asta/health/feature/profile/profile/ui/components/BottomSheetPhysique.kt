package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import fit.asta.health.data.profile.remote.model.PhysiqueUnit
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.RowToggleButtonGroup
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun BottomSheetPhysique(
    isVisible: Boolean,
    sheetState: SheetState,
    isValid: (value: Double?, unit: Int?) -> Boolean,
    label: String,
    text: String,
    units: List<PhysiqueUnit>,
    selectedUnitIndex: Int?,
    onDismissRequest: () -> Unit,
    onSaveClick: (value: Double?, unit: Int?) -> Unit
) {
    var textFieldValue by remember(isVisible, text) {
        mutableStateOf(TextFieldValue(text = text))
    }
    var updatedUnitIndex by rememberSaveable(isVisible, selectedUnitIndex) {
        mutableStateOf(selectedUnitIndex)
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
            RowToggleButtonGroup(
                selectedIndex = updatedUnitIndex ?: -1,
                buttonTexts = units.map { it.title },
                onButtonClick = { index ->
                    updatedUnitIndex = index
                },
                modifier = Modifier.size(width = 80.dp, height = 24.dp),
                selectedColor = AppTheme.colors.primary
            )
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
            )
            BottomSheetSaveButtons(
                onSave = {
                    onSaveClick(
                        textFieldValue.text.toDoubleOrNull(),
                        updatedUnitIndex?.let { units[it].value }
                    )
                },
                saveButtonEnabled = isValid(
                    textFieldValue.text.toDoubleOrNull(),
                    updatedUnitIndex?.let { units[it].value }
                )
            ) {
                onDismissRequest()
            }
        }
    }
}