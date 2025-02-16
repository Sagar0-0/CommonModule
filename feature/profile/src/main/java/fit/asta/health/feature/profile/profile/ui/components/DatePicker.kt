package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@Composable
fun DatePicker(
    useCaseState: UseCaseState,
    onSelection: (LocalDate) -> Unit,
) {
    CalendarDialog(
        state = useCaseState, selection = CalendarSelection.Date {
            onSelection(it)
        },
        config = CalendarConfig(monthSelection = true, yearSelection = true)
    )
}