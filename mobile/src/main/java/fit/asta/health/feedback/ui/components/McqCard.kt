package fit.asta.health.feedback.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

@Composable
fun mcqCard(list: List<String>): MutableState<String> {

    val ans = rememberSaveable { mutableStateOf("") }

    Column {
        list.forEach { text ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = text == ans.value,
                    onClick = {
                        ans.value = text
                    },
                    colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                )
                Text(text = text)
            }
        }
    }

    return ans
}