package fit.asta.health.profile.createprofile.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileTimePicker(
    clockState: UseCaseState,
    onPositiveClick: ((hours: Int, minutes: Int) -> Unit)? = null,
) {

    onPositiveClick?.let { ClockSelection.HoursMinutes(onPositiveClick = it) }?.let {
        ClockDialog(
            state = clockState, selection = it
        )
    }

}