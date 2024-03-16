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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun BottomSheetRadioList(
    isVisible: Boolean,
    sheetState: SheetState,
    isValid: (value: Int?) -> Boolean,
    selectedIndex: Int?,
    list: List<String>,
    onDismissRequest: () -> Unit,
    onSaveClick: (value: Int?) -> Unit
) {
    val (index, onIndexChange) = rememberSaveable(isVisible) {
        mutableStateOf(selectedIndex)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                list.forEachIndexed { i, name ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        AppRadioButton(
                            selected = index == i
                        ) {
                            onIndexChange(i)
                        }
                        CaptionTexts.Level3(
                            text = name,
                            color = AppTheme.colors.onPrimaryContainer
                        )
                    }
                }
            }

            BottomSheetSaveButtons(
                onSave = {
                    onSaveClick(
                        index
                    )
                },
                saveButtonEnabled = isValid(
                    index
                )
            ) {
                onDismissRequest()
            }
        }
    }
}