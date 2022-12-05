@file:Suppress("UNUSED_EXPRESSION")

package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun UserBedAndWakeUpTimeBottomSheetLayout() {

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
            Text(text = "Please Select the Sleep and Wake Up Time", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        TimerButton()

    }
}

@Composable
fun TimerButton() {

    var selected by remember { mutableStateOf(false) }

    val colorState: Color = if (selected) Color(0xffD6D6D6) else Color(0xff0088FF)

    val colorState2: Color = if (!selected) Color(0xffD6D6D6) else Color(0xff0088FF)

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {

            Button(onClick = {
                selected = !selected
            },
                content = {
                    Text(text = "Wakeup Time",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 19.6.sp)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorState))

            Button(onClick = {
                selected = !selected
            },
                content = {
                    Text(text = "Sleep Time",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 19.6.sp)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorState2))

        }
    }
}