package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppTextButton
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
    val (updatedText, onValueChange) = rememberSaveable {
        mutableStateOf(text)
    }
    AppModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetVisible = isVisible,
        sheetState = sheetState,
        dragHandle = null,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            CaptionTexts.Level2(text = label)
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = updatedText,
                onValueChange = onValueChange,
            )
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                AppTextButton(textToShow = "Cancel") {
                    onDismissRequest()
                }
                AppTextButton(textToShow = "Save") {
                    onSaveClick(updatedText)
                }
            }
        }
    }
}