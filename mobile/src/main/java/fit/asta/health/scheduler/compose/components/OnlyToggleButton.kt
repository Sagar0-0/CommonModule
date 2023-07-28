package fit.asta.health.scheduler.compose.components

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.TSelected
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.ASUiState
import fit.asta.health.scheduler.model.net.scheduler.Time

@Composable
fun OnlyToggleButton(
    imageIcon: ImageVector,
    title: String,
    switchTitle: String,
    onNavigateToClickText: (() -> Unit)?,
    onCheckClicked: (Boolean) -> Unit = {},
    mCheckedState: Boolean = false,
    btnEnabled: Boolean=false
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = imageIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectableText(
                    btnEnabled=btnEnabled,
                    arrowTitle = switchTitle,
                    onClick = { onNavigateToClickText?.invoke() })
                Switch(
                    checked = mCheckedState,
                    onCheckedChange = {
                        onCheckClicked(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedTrackColor = MaterialTheme.colorScheme.background,
                        checkedBorderColor = MaterialTheme.colorScheme.primary,
                        uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        }
    }
}


@Composable
fun DigitalDemo(onTimeChange: (Time) -> Unit) {


    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


    val calendar = Calendar.getInstance()
    val initHour = calendar[Calendar.HOUR_OF_DAY]
    val initMinute = calendar[Calendar.MINUTE]


    val minuteDemo = remember { mutableStateOf(initMinute) }
    val hourDemo = remember { mutableStateOf(initHour) }

    val timePickerDialog = TimePickerDialog(LocalContext.current, { _, hour: Int, minute: Int ->
        minuteDemo.value = minute
        hourDemo.value = hour
        onTimeChange(
            Time(
                hours = hour.toString(),
                minutes = minute.toString(),
                midDay = hour > 12
            )
        )
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
                text = if (hourDemo.value > 12) "${hourDemo.value - 12}:${minuteDemo.value}" else "${hourDemo.value}:${minuteDemo.value}",
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alignByBaseline()
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = if (hourDemo.value > 12) "pm" else "am",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

@Composable
fun RepeatAlarm(
    onDaySelect: (Int) -> Unit, alarmSettingUiState: ASUiState,
) {
    var text by remember { mutableStateOf("One Time") }
    text = getRecurringDaysText(alarmSettingUiState)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.EditCalendar,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column {
                        Text(
                            text = "Repeat",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AllDays(onDaySelect = onDaySelect, alarmSettingUiState = alarmSettingUiState)
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun DaysCircleButton(
    day: String,
    isSelected: Boolean = false,
    onDaySelect: () -> Unit = {}
) {

    val colorState: Color = if (!isSelected) TSelected else MaterialTheme.colorScheme.primary

    val colorState2: Color = if (isSelected) Color.White else Color.Black

    Button(
        onClick = { onDaySelect() },
        shape = CircleShape,
        modifier = Modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorState)
    ) {
        Text(text = day, fontSize = 16.sp, color = colorState2, textAlign = TextAlign.Center)
    }
}

@Composable
fun AllDays(
    alarmSettingUiState: ASUiState,
    onDaySelect: (Int) -> Unit
) {


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        DaysCircleButton(day = "S", isSelected = alarmSettingUiState.sunday) { onDaySelect(0) }
        DaysCircleButton(day = "M", isSelected = alarmSettingUiState.monday) { onDaySelect(1) }
        DaysCircleButton(day = "T", isSelected = alarmSettingUiState.tuesday) { onDaySelect(2) }
        DaysCircleButton(day = "W", isSelected = alarmSettingUiState.wednesday) { onDaySelect(3) }
        DaysCircleButton(day = "T", isSelected = alarmSettingUiState.thursday) { onDaySelect(4) }
        DaysCircleButton(day = "F", isSelected = alarmSettingUiState.friday) { onDaySelect(5) }
        DaysCircleButton(day = "S", isSelected = alarmSettingUiState.saturday) { onDaySelect(6) }
    }
}

fun getRecurringDaysText(alarmWeek: ASUiState): String {
    if (!alarmWeek.recurring) {
        return "Once"
    }
    var days = ""
    if (alarmWeek.monday) {
        days += "Mon "
    }
    if (alarmWeek.tuesday) {
        days += "Tue "
    }
    if (alarmWeek.wednesday) {
        days += "Wed "
    }
    if (alarmWeek.thursday) {
        days += "Thu "
    }
    if (alarmWeek.friday) {
        days += "Fri "
    }
    if (alarmWeek.saturday) {
        days += "Sat "
    }
    if (alarmWeek.sunday) {
        days += "Sun "
    }
    return days
}