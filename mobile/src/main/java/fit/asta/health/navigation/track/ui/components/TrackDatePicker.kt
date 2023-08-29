package fit.asta.health.navigation.track.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.theme.spacing
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
        TrackDatePicker(
            date = 23,
            month = 8,
            year = 2023,
            onPreviousButtonClick = {},
            onNextButtonClick = {},
            onDateChanged = { _, _, _ ->
            }
        )
    }
}

/**
 * This function shows a date picker row of the track screens
 *
 * @param date This variable contains the current date selected
 * @param month This variable contains the current month selected
 * @param year This variable contains the current year selected
 * @param onPreviousButtonClick This function is executed when the user hits the left Arrow button
 * @param onNextButtonClick This function is executed when the user hits the right Arrow button
 * @param onDateChanged This function is executed when the user changes the date selected
 */
@Composable
fun TrackDatePicker(
    date: Int,
    month: Int,
    year: Int,
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onDateChanged: (Int, Int, Int) -> Unit,
) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clickable {
                        onPreviousButtonClick()
                    }
            )

            Text(
                text = "$date/$month/$year",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )

            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clickable {
                        onNextButtonClick()
                    }
            )
        }

        Icon(
            imageVector = Icons.Default.EditCalendar,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    DatePickerDialog(
                        context,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            onDateChanged(mDayOfMonth, mMonth + 1, mYear)
                        }, year, month - 1, date
                    ).show()
                }
        )
    }
}