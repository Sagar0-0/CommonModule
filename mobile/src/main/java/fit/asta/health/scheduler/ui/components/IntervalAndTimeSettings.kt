package fit.asta.health.scheduler.ui.components

import android.util.Log
import android.view.View
import android.widget.Button
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.shawnlin.numberpicker.NumberPicker
import fit.asta.health.R
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.TimeUi


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
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextSelection(
            imageIcon = Icons.Default.Snooze,
            title = stringResource(id = R.string.snooze),
            arrowTitle = "${timeSettingUiState.snoozeTime} Minutes",
            btnEnabled = true,
            onNavigateAction = onNavigateSnooze
        )
        OnlyToggleButton(
            imageIcon = Icons.Default.Notifications,
            title = stringResource(id = R.string.advanced_reminder),
            btnEnabled = true,
            switchTitle = stringResource(
                R.string.minutes,
                timeSettingUiState.advancedReminder.time
            ),
            mCheckedState = timeSettingUiState.advancedReminder.status,
            onCheckClicked = { onAdvancedStatus(it) },
            onNavigateToClickText = onNavigateAdvanced
        )
        OnlyToggleButton(
            imageIcon = Icons.Default.Schedule,
            title = stringResource(id = R.string.end_alarm),
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
fun SnoozeBottomSheet(onNavigateBack: () -> Unit, onValueChange: (Int) -> Unit = {}) {

    AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
        val view = View.inflate(it, R.layout.scheduler_layout_dialog_number_picker, null)

        view
    }, update = {
        val durationUnitPicker = it.findViewById<NumberPicker>(R.id.durationUnitPicker)
        val durationPicker = it.findViewById<NumberPicker>(R.id.durationPicker)
        val cancelButton = it.findViewById<Button>(R.id.cancelButton)
        val okButton = it.findViewById<Button>(R.id.okButton)

        val data = arrayOf("Minute")
        durationPicker.setOnValueChangedListener { picker, _, _ ->
            Log.d("manish", "SnoozeBottomSheet: ${picker.value}")
        }
        durationUnitPicker.minValue = 1
        durationUnitPicker.maxValue = data.size
        durationUnitPicker.displayedValues = data
        cancelButton.setOnClickListener {
            onNavigateBack.invoke()
        }
        okButton.setOnClickListener {
            onValueChange.invoke(durationPicker.value)
            onNavigateBack.invoke()
        }
    })

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
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        if (endAlarm.hours > 0 || endAlarm.minutes > 0) {
            val postAlarm = stringResource(id = R.string.end_alarm)
            val ampm: String =
                if (endAlarm.hours > 12) stringResource(R.string.pm) else stringResource(R.string.am)
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
                        painter = painterResource(id = R.drawable.ic_ic24_rotate),
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