package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import fit.asta.health.designsystem.components.ButtonWithColor
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.TimeUi
import kotlin.math.abs
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR


@Composable
fun SettingsLayout(
    modifier: Modifier = Modifier,
    timeSettingUiState: IvlUiState,
    onNavigateSnooze: () -> Unit,
    onNavigateAdvanced: () -> Unit,
    onAdvancedStatus: (Boolean) -> Unit,
    onEndStatus: (Boolean) -> Unit,
    onEndAlarm: () -> Unit,
    onDelete: () -> Unit,
) {

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(AstaThemeX.appSpacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextSelection(
            imageIcon = Icons.Default.Snooze,
            title = stringResource(id = StringR.string.snooze),
            arrowTitle = "${timeSettingUiState.snoozeTime} Minutes",
            btnEnabled = true,
            onNavigateAction = onNavigateSnooze
        )
        OnlyToggleButton(
            imageIcon = Icons.Default.Notifications,
            title = stringResource(id = StringR.string.advanced_reminder),
            btnEnabled = true,
            switchTitle = stringResource(
                StringR.string.minutes,
                timeSettingUiState.advancedReminder.time
            ),
            mCheckedState = timeSettingUiState.advancedReminder.status,
            onCheckClicked = { onAdvancedStatus(it) },
            onNavigateToClickText = onNavigateAdvanced
        )
        OnlyToggleButton(
            imageIcon = Icons.Default.Schedule,
            title = stringResource(id = StringR.string.end_alarm),
            switchTitle = "",
            mCheckedState = timeSettingUiState.statusEnd,
            onCheckClicked = { onEndStatus(it) },
            onNavigateToClickText = null
        )
        AnimatedVisibility(visible = timeSettingUiState.statusEnd) {
            CustomFloatingButton(
                onDelete = onDelete,
                onEndAlarm = onEndAlarm,
                endAlarm = timeSettingUiState.endAlarmTime
            )
        }
    }
}


@Composable
fun SnoozeBottomSheet(
    minutesRange: Iterable<Int> = (1..59),
    onNavigateBack: () -> Unit,
    onValueChange: (Int) -> Unit = {}
) {
    MinutesPicker(onCancel = onNavigateBack, onSave = onValueChange, minutesRange = minutesRange)

}

@Composable
fun MinutesPicker(
    onCancel: () -> Unit, onSave: (Int) -> Unit,
    modifier: Modifier = Modifier,
    leadingZero: Boolean = true,
    minutesRange: Iterable<Int> = (5..30),
    dividersColor: Color = Color.Green,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    var min by remember { mutableIntStateOf(minutesRange.first()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(AstaThemeX.appSpacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Time", style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumberPicker(modifier = Modifier.weight(1f), label = {
                "${if (leadingZero && (abs(it) < 10)) "0" else ""}$it"
            }, value = min, onValueChange = {
                min = it
            }, dividersColor = dividersColor, textStyle = textStyle, range = minutesRange
            )

            Text(
                textAlign = TextAlign.Center,
                text = "Minutes",
                style = MaterialTheme.typography.titleSmall
            )

        }
        Row(horizontalArrangement = Arrangement.spacedBy(AstaThemeX.appSpacing.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = Color.Red,
                text = stringResource(id = StringR.string.cancel)
            ) { onCancel() }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = Color.Blue,
                text = stringResource(StringR.string.save)
            ) { onSave(min) }
        }
    }
}

@Composable
fun CustomFloatingButton(
    endAlarm: TimeUi,
    onDelete: () -> Unit,
    onEndAlarm: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AstaThemeX.appSpacing.small)
    ) {
        if (endAlarm.hours > 0 || endAlarm.minutes > 0) {
            val postAlarm = stringResource(id = StringR.string.end_alarm)
            val ampm: String =
                if (endAlarm.hours > 12) stringResource(StringR.string.pm) else stringResource(
                    StringR.string.am
                )
            val time: String =
                if (endAlarm.hours > 12) "${endAlarm.hours - 12}" else endAlarm.hours.toString()
            CustomVariantInterval(
                time = "$time:${endAlarm.minutes} $ampm ",
                tagName = postAlarm,
                onClick = { onDelete() }
            )
        }


        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = onEndAlarm,
                shape = CircleShape,
                modifier = Modifier.size(50.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun CustomVariantInterval(
    time: String,
    tagName: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Row {
                Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = DrawR.drawable.ic_ic24_rotate),
                        contentDescription = null,
                        Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = time,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SelectableText(arrowTitle = tagName)

                Spacer(modifier = Modifier.width(8.dp))

                Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Rounded.RemoveCircle,
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}