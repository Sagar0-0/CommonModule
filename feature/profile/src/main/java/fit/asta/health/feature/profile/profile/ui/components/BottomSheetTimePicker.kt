package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.molecular.TimePickerContent
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTimePicker(
    isVisible: Boolean,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onSaveClick: (from: Float, to: Float) -> Unit
) {
    var pickerNumber by rememberSaveable(isVisible) {
        mutableIntStateOf(1)
    }
    var startHour by rememberSaveable(isVisible) {
        mutableIntStateOf(LocalTime.now().hour)
    }
    var endHour by rememberSaveable(isVisible) {
        mutableIntStateOf(LocalTime.now().hour)
    }
    var startMinutes by rememberSaveable(isVisible) {
        mutableIntStateOf(LocalTime.now().minute)
    }
    var endMinutes by rememberSaveable(isVisible) {
        mutableIntStateOf(LocalTime.now().minute)
    }


    AppModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetVisible = isVisible,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column {
            AnimatedVisibility(visible = pickerNumber == 1) {
                TimePickerContent(
                    hours = startHour,
                    minutes = startMinutes,
                    title = "Select start time",
                    leftButtonTitle = "Cancel",
                    rightButtonTitle = "Next",
                    onLeftButtonClick = { onDismissRequest() },
                    onRightButtonClick = { hours, minutes ->
                        pickerNumber++
                        startHour = hours
                        startMinutes = minutes
                    }
                )
            }
            AnimatedVisibility(visible = pickerNumber == 2) {
                TimePickerContent(
                    hours = endHour,
                    minutes = endMinutes,
                    title = "Select end time",
                    leftButtonTitle = "Back",
                    rightButtonTitle = "Save",
                    onLeftButtonClick = { pickerNumber-- },
                    onRightButtonClick = { hours, minutes ->
                        pickerNumber += 1
                        endHour = hours
                        endMinutes = minutes
                        onSaveClick(
                            (startHour + startMinutes).toFloat(),
                            (endHour + endMinutes).toFloat()
                        )
                    }
                )
            }

        }
    }
}