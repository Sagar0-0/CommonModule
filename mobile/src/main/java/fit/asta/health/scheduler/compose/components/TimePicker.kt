package fit.asta.health.scheduler.compose.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowTimePicker(context: Context, initHour: Int, initMinute: Int) {

    val time = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(context, { _, hour: Int, minute: Int ->
        time.value = "$hour:$minute"
    }, initHour, initMinute, false)
    timePickerDialog.show()

}


@Preview
@Composable
fun CaptureTimerDemoPreview() {
    ShowTimePicker(context = LocalContext.current, initHour = 0, initMinute = 0)
}