package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.BodyTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun BottomSheetBodyType(
    isVisible: Boolean,
    sheetState: SheetState,
    isValid: (value: Int?) -> Boolean,
    selectedValue: Int?,
    onDismissRequest: () -> Unit,
    onSaveClick: (value: Int?) -> Unit
) {
    val (updatedBodyTypeValue, onValueChange) = rememberSaveable(isVisible) {
        mutableStateOf(selectedValue)
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
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                BodyTypes.entries.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        AppRadioButton(
                            selected = updatedBodyTypeValue == option.value
                        ) {
                            onValueChange(option.value)
                        }
                        CaptionTexts.Level3(
                            text = option.name,
                            color = AppTheme.colors.onPrimaryContainer
                        )
                    }
                }
            }

            BottomSheetSaveButtons(
                onSave = {
                    onSaveClick(
                        updatedBodyTypeValue
                    )
                },
                saveButtonEnabled = isValid(
                    updatedBodyTypeValue
                )
            ) {
                onDismissRequest()
            }
        }
    }
}