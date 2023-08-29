package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chargemap.compose.numberpicker.NumberPicker


@Composable
fun NumberPickerDemo() {

    var pickerValue by remember { mutableIntStateOf(0) }

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