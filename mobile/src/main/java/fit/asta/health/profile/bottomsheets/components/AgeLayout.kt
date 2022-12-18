package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AgeLayout() {
    Column(Modifier
        .fillMaxWidth()
        .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DividerLine()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Please enter your age", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Age",
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0x99000000))
            Text(text = "24",
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0x99000000))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(Modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            val textState2 = remember { mutableStateOf(TextFieldValue()) }
            val textState3 = remember { mutableStateOf(TextFieldValue()) }
            TextField(value = textState.value,
                onValueChange = { textState.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.3f),
                placeholder = { Text(text = "Day") })
            Spacer(modifier = Modifier.width(4.dp))
            TextField(value = textState2.value,
                onValueChange = { textState2.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.5f),
                placeholder = { Text(text = "Month") })
            Spacer(modifier = Modifier.width(4.dp))
            TextField(value = textState3.value,
                onValueChange = { textState3.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(01f),
                placeholder = { Text(text = "Year") })
        }
        DoneButton()
    }
}