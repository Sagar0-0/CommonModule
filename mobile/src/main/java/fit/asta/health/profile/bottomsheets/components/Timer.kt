package fit.asta.health.profile.bottomsheets.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
fun CaptureTimer() {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            ShowTimePicker(context = LocalContext.current, initHour = 0, initMinute = 0)
        }
    }
}