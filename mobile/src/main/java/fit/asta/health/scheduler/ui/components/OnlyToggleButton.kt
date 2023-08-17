package fit.asta.health.scheduler.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.theme.TSelected
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.AMPMHoursMin
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.ASUiState

@Composable
fun OnlyToggleButton(
    imageIcon: ImageVector,
    title: String,
    switchTitle: String,
    onNavigateToClickText: (() -> Unit)?,
    onCheckClicked: (Boolean) -> Unit = {},
    mCheckedState: Boolean = false,
    btnEnabled: Boolean = false
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
                    btnEnabled = btnEnabled,
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
fun DigitalDemo(time: AMPMHoursMin, open: () -> Unit = {}) {
    val hours by animateIntAsState(
        targetValue = if (time.hours > 12) {
            time.hours - 12
        } else time.hours, label = "",
        animationSpec = tween(700, easing = FastOutLinearInEasing)
    )
    val minutes by animateIntAsState(
        targetValue = time.minutes, label = "",
        animationSpec = tween(700, easing = FastOutLinearInEasing)
    )
    AppCard(modifier = Modifier.clickable { open() }, content = {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${if (time.hours < 10) "0" else ""}${hours}:${if (time.minutes < 10) "0" else ""}${minutes}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = time.dayTime.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    })
}

@Composable
fun RepeatAlarm(
    onDaySelect: (Int) -> Unit, alarmSettingUiState: ASUiState,
) {
    val map: HashMap<Int, String> = HashMap()
    map[1] = stringResource(R.string.once)
    map[2] = stringResource(R.string.mon)
    map[3] = stringResource(R.string.tue)
    map[4] = stringResource(R.string.wed)
    map[5] = stringResource(R.string.thu)
    map[6] = stringResource(R.string.fri)
    map[7] = stringResource(R.string.sat)
    map[8] = stringResource(R.string.sun)
    var text by remember { mutableStateOf(map[1]) }
    text = getRecurringDaysText(alarmSettingUiState, map)
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
                            text = text!!,
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

fun getRecurringDaysText(alarmWeek: ASUiState, map: Map<Int, String>): String {


    if (!alarmWeek.recurring) {
        return map[1]!!
    }
    var days = ""
    if (alarmWeek.monday) {
        days += map[2]
    }
    if (alarmWeek.tuesday) {
        days += map[3]
    }
    if (alarmWeek.wednesday) {
        days += map[4]
    }
    if (alarmWeek.thursday) {
        days += map[5]
    }
    if (alarmWeek.friday) {
        days += map[6]
    }
    if (alarmWeek.saturday) {
        days += map[7]
    }
    if (alarmWeek.sunday) {
        days += map[8]
    }
    return days
}