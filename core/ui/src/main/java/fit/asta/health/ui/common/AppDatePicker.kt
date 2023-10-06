package fit.asta.health.ui.common

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(
    "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            AppDatePicker(
                localDate = LocalDate.now(),
                onPreviousButtonClick = {},
                onNextButtonClick = {},
                onDateChanged = { _ ->
                }
            )
        }
    }
}

/**
 * This function shows a date picker row
 *
 * @param localDate This variable contains the LocalDate which needs to be shown in the UI
 * @param onPreviousButtonClick This function is executed when the user hits the left Arrow button
 * @param onNextButtonClick This function is executed when the user hits the right Arrow button
 * @param onDateChanged This function is executed when the user changes the date selected
 */
@Composable
fun AppDatePicker(
    localDate: LocalDate,
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onDateChanged: (LocalDate) -> Unit,
) {

    // Formatting the text to be showed
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val textToShow = localDate.format(dateTimeFormatter)

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            AppIconButton(
                modifier = Modifier.size(AppTheme.iconSize.level5),
                imageVector = Icons.Default.ArrowLeft,
                onClick = onPreviousButtonClick
            )

            TitleTexts.Level2(text = textToShow)


            AppIconButton(
                modifier = Modifier.size(AppTheme.iconSize.level5),
                imageVector = Icons.Default.ArrowRight,
                onClick = onNextButtonClick
            )
        }


        AppIconButton(imageVector = Icons.Default.EditCalendar) {
            DatePickerDialog(
                context,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    onDateChanged(LocalDate.of(mYear, mMonth + 1, mDayOfMonth))
                }, localDate.year, localDate.monthValue - 1, localDate.dayOfMonth
            ).show()
        }
    }
}