package fit.asta.health.new_scheduler.view.components

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Composable
fun OnlyToggleButton(
    icon: Int,
    title: String,
    switchTitle: String,
) {

    val mCheckedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Row {
                    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            Modifier.size(20.dp),
                            tint = Color(0xff0088FF)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, fontSize = 16.sp, color = Color(0xff132839))
                }
            }
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = switchTitle, fontSize = 16.sp, color = Color(0xff8694A9))

                    Spacer(modifier = Modifier.width(8.dp))

                    Switch(
                        checked = mCheckedState.value,
                        onCheckedChange = { mCheckedState.value = it },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = Color(0xff8694A9),
                            checkedThumbColor = Color(0xff0088FF)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AlarmIconButton(
    image: Int,
    title: String,
    arrowTitle: String,
    arrowImage: Int,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Row {
                    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = image),
                            contentDescription = null,
                            Modifier.size(20.dp),
                            tint = Color(0xff0088FF)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, fontSize = 16.sp, color = Color(0xff132839))
                }
            }

            Box {
                Row {
                    Text(text = arrowTitle, fontSize = 16.sp, color = Color(0xff8694A9))

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = arrowImage),
                                contentDescription = null,
                                Modifier.size(20.dp),
                                tint = Color(0xff0088FF)
                            )
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DigitalDemo() {

    val calendar = Calendar.getInstance()
    val initHour = calendar[Calendar.HOUR_OF_DAY]
    val initMinute = calendar[Calendar.MINUTE]


    val minuteDemo = remember { mutableStateOf("$initMinute") }
    val hourDemo = remember { mutableStateOf("$initHour") }

    val timePickerDialog = TimePickerDialog(LocalContext.current, { _, hour: Int, minute: Int ->
        minuteDemo.value = "$minute"
        hourDemo.value = "$hour"
    }, initHour, initMinute, false)

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xff0088FF)),
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        TextButton(onClick = { timePickerDialog.show() }) {
            Text(
                text = "${hourDemo.value}:${minuteDemo.value}",
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alignByBaseline()
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "am",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

@Composable
fun RepeatAlarm() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Row {
                    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ic24_calendar_edit),
                            contentDescription = null,
                            Modifier.size(20.dp),
                            tint = Color(0xff0088FF)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "Repeat", fontSize = 16.sp, color = Color(0xff132839))
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(text = "Everyday", fontSize = 16.sp, color = Color(0xff8694A9))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AllDays()
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun DaysCircleButton(
    day: String,
) {

    var selected by remember { mutableStateOf(true) }

    val colorState: Color = if (selected) Color(0xffD6D6D6) else Color(0xff0088FF)

    val colorState2: Color = if (!selected) Color.White else Color.Black

    Button(
        onClick = { selected = !selected },
        shape = CircleShape,
        modifier = Modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorState)
    ) {
        Text(text = day, fontSize = 16.sp, color = colorState2, textAlign = TextAlign.Center)
    }
}

@Composable
fun AllDays() {

    val daysList = listOf("S", "M", "T", "W", "T", "F", "S")

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        daysList.forEach {
            DaysCircleButton(day = it)
        }
    }
}