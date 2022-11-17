package fit.asta.health.scheduler.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chargemap.compose.numberpicker.NumberPicker

@Preview
@Composable
fun NumberPickerDemo() {

    var pickerValue by remember { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NumberPicker(
            value = pickerValue,
            range = 0..10,
            onValueChange = {
                pickerValue = it
            }
        )
    }

}