package fit.asta.health.designsystem.atomic

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme


@Composable
fun AppDateRangePicker(
    state: DateRangePickerState,
    modifier: Modifier = Modifier,
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    title: @Composable() (() -> Unit)? = {
        DateRangePickerDefaults.DateRangePickerTitle(
            displayMode = state.displayMode,
            modifier = Modifier.padding(AppTheme.spacing.level2)
        )
    },
    headline: @Composable() (() -> Unit)? = {
        DateRangePickerDefaults.DateRangePickerHeadline(
            selectedStartDateMillis = state.selectedStartDateMillis,
            selectedEndDateMillis = state.selectedEndDateMillis,
            displayMode = state.displayMode,
            dateFormatter,
            modifier = Modifier.padding(AppTheme.spacing.level2)
        )
    },
    showModeToggle: Boolean = true,
    colors: DatePickerColors = DatePickerDefaults.colors()
) {

    DateRangePicker(
        state = state,
        modifier = modifier,
        dateFormatter = dateFormatter,
        title = title,
        headline = headline,
        showModeToggle = showModeToggle,
        colors = colors
    )
}

class AppDateRangePickerState(
    override var displayMode: DisplayMode,
    override var displayedMonthMillis: Long,
    override val selectableDates: SelectableDates,
    override val selectedEndDateMillis: Long?,
    override val selectedStartDateMillis: Long?,
    override val yearRange: IntRange
) : DateRangePickerState {
    override fun setSelection(startDateMillis: Long?, endDateMillis: Long?) {

    }

}

@Composable
fun appRememberDateRangePickerState(
    initialSelectedStartDateMillis: Long? = null,
    initialSelectedEndDateMillis: Long? = null,
    initialDisplayedMonthMillis: Long? = initialSelectedStartDateMillis,
    yearRange: IntRange = DatePickerDefaults.YearRange,
    initialDisplayMode: DisplayMode = DisplayMode.Picker,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates
) : DateRangePickerState{
    return rememberDateRangePickerState(
        yearRange = yearRange,
        initialDisplayedMonthMillis = initialDisplayedMonthMillis,
        initialDisplayMode = initialDisplayMode,
        selectableDates = selectableDates,
        initialSelectedEndDateMillis = initialSelectedEndDateMillis,
        initialSelectedStartDateMillis = initialSelectedStartDateMillis
    )
}