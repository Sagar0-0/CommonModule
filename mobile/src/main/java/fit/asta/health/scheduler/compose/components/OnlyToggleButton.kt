package fit.asta.health.scheduler.compose.components

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.common.ui.theme.TSelected

@Composable
fun OnlyToggleButton(
    icon: Int,
    title: String,
    switchTitle: String,
    onNavigateToClickText: (() -> Unit)?,
) {

    val mCheckedState = remember { mutableStateOf(false) }

    val enabled by remember { mutableStateOf(true) }

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
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
            }
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ClickableText(text = AnnotatedString(
                        text = switchTitle,
                        spanStyle = SpanStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    ),
                        onClick = {
                            if (enabled) {
                                onNavigateToClickText?.invoke()
                            }
                        })

                    Spacer(modifier = Modifier.width(8.dp))

                    Switch(
                        checked = mCheckedState.value,
                        onCheckedChange = { mCheckedState.value = it },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            checkedThumbColor = MaterialTheme.colorScheme.primary
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
    onNavigateToScreen: () -> Unit,
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
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
            }

            Box {
                Row {
                    Text(text = arrowTitle, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        IconButton(onClick = onNavigateToScreen) {
                            Icon(
                                painter = painterResource(id = arrowImage),
                                contentDescription = null,
                                Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
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


    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


    val calendar = Calendar.getInstance()
    val initHour = calendar[Calendar.HOUR_OF_DAY]
    val initMinute = calendar[Calendar.MINUTE]


    val minuteDemo = remember { mutableStateOf("$initMinute") }
    val hourDemo = remember { mutableStateOf("$initHour") }

    val timePickerDialog = TimePickerDialog(LocalContext.current, { _, hour: Int, minute: Int ->
        minuteDemo.value = "$minute"
        hourDemo.value = "$hour"
    }, initHour, initMinute, false)

    if (isPressed) timePickerDialog.show()

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        TextButton(onClick = { }, interactionSource = interactionSource) {
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
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "Repeat", fontSize = 16.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(text = "Everyday", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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

    val colorState: Color = if (selected) TSelected else MaterialTheme.colorScheme.primary

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