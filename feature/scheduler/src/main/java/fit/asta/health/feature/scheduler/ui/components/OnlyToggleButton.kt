package fit.asta.health.feature.scheduler.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.data.scheduler.db.entity.Weekdays
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.theme.TSelected
import fit.asta.health.designsystem.theme.spacing
import java.util.Calendar

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
        targetValue = time.hours, label = "",
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
    onDaySelect: (Int) -> Unit, weekdays: Weekdays,
) {
    val context = LocalContext.current
    val text by remember(weekdays) {
        mutableStateOf(
            weekdays.toString(context = context, order = Weekdays.Order.SUN_TO_SAT)
        )
    }
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
        AllDays(onDaySelect = onDaySelect, weekdays = weekdays)
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
    weekdays: Weekdays,
    onDaySelect: (Int) -> Unit
) {


    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        DaysCircleButton(day = "S", isSelected = weekdays.isBitOn(Calendar.SUNDAY)) {
            onDaySelect(Calendar.SUNDAY)
        }
        DaysCircleButton(day = "M", isSelected = weekdays.isBitOn(Calendar.MONDAY)) {
            onDaySelect(Calendar.MONDAY)
        }
        DaysCircleButton(day = "T", isSelected = weekdays.isBitOn(Calendar.TUESDAY)) {
            onDaySelect(Calendar.TUESDAY)
        }
        DaysCircleButton(
            day = "W", isSelected = weekdays.isBitOn(Calendar.WEDNESDAY)
        ) { onDaySelect(Calendar.WEDNESDAY) }
        DaysCircleButton(day = "T", isSelected = weekdays.isBitOn(Calendar.THURSDAY)) {
            onDaySelect(Calendar.THURSDAY)
        }
        DaysCircleButton(day = "F", isSelected = weekdays.isBitOn(Calendar.FRIDAY)) {
            onDaySelect(Calendar.FRIDAY)
        }
        DaysCircleButton(day = "S", isSelected = weekdays.isBitOn(Calendar.SATURDAY)) {
            onDaySelect(Calendar.SATURDAY)
        }
    }
}
