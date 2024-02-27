package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.chargemap.compose.numberpicker.NumberPicker
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import java.time.LocalTime

@Composable
fun TimePickerContent(
    modifier: Modifier = Modifier,
    title: String = "Select Time",
    hours: Int = LocalTime.now().hour,
    minutes: Int = LocalTime.now().hour,
    hoursRange: Iterable<Int> = (0..24),
    minutesRange: Iterable<Int> = (0..59),
    leftButtonTitle: String = "Cancel",
    onLeftButtonClick: () -> Unit,
    rightButtonTitle: String = "Save",
    onRightButtonClick: (hours: Int, mins: Int) -> Unit
) {
    var currentHours by rememberSaveable(hours) {
        mutableIntStateOf(hours)
    }
    var currentMinutes by rememberSaveable(minutes) {
        mutableIntStateOf(minutes)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleTexts.Level1(text = title)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TitleTexts.Level3(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = "Hours",
            )
            TitleTexts.Level3(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = "Minutes",
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumberPicker(
                modifier = Modifier.weight(1f),
                value = currentHours,
                onValueChange = {
                    currentHours = it
                },
                range = hoursRange
            )

            NumberPicker(
                modifier = Modifier.weight(1f),
                value = currentMinutes,
                onValueChange = {
                    currentMinutes = it
                },
                range = minutesRange
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
            AppOutlinedButton(
                onClick = { onLeftButtonClick() },
                modifier = Modifier.weight(1f),
                textToShow = leftButtonTitle
            )
            AppOutlinedButton(
                onClick = { onRightButtonClick(currentHours, currentMinutes) },
                modifier = Modifier.weight(1f),
                textToShow = rightButtonTitle
            )
        }
    }
}