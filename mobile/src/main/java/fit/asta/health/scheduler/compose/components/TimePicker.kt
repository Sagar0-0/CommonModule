package fit.asta.health.scheduler.compose.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Button(onClick = { timePickerDialog.show() }) {
                Text("Click me")
            }
            Spacer(Modifier.height(50.dp))
            Text(text = "Selected Time -> ${time.value}")
        }
    }
}


@Preview
@Composable
fun CaptureTimerDemoPreview() {
    ShowTimePicker(context = LocalContext.current, initHour = 0, initMinute = 0)
}

