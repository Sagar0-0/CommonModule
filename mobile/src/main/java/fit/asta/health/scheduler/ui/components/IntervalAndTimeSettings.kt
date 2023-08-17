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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.AMPMHoursMin
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.RepUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.StatUiState
import fit.asta.health.tools.breathing.model.domain.mapper.convert12hrTo24hr


@Composable
fun SettingsLayout(
    modifier: Modifier = Modifier,
    onNavigateSnooze: () -> Unit,
    onNavigateAdvanced: () -> Unit,
    onNavigateDuration: () -> Unit,
    onChoice: (Boolean) -> Unit,
    onRemainderAtEnd: (Boolean) -> Unit,
    onNavigateRepetitiveInterval: () -> Unit,
    timeSettingUiState: IvlUiState,
    variantIntervals: SnapshotStateList<StatUiState>,
    onVariantStateChange: (Boolean) -> Unit,
    onStateChange: (Boolean) -> Unit,
    onDelete: (StatUiState) -> Unit,
    onAddVariantInterval: () -> Unit
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
            onCheckClicked = { onChoice(it) },
            onNavigateToClickText = onNavigateAdvanced
        )
        OnlyToggleButton(
            imageIcon = Icons.Default.Timelapse,
            title = stringResource(R.string.post_reminder),
            btnEnabled = true,
            switchTitle = stringResource(R.string.minutes, timeSettingUiState.duration),
            mCheckedState = timeSettingUiState.isRemainderAtTheEnd,
            onCheckClicked = { onRemainderAtEnd(it) },
            onNavigateToClickText = onNavigateDuration
        )
        OnlyToggleButton(
            imageIcon = Icons.Default.Schedule,
            title = stringResource(id = R.string.interval_s_status),
            switchTitle = "",
            mCheckedState = timeSettingUiState.status,
            onCheckClicked = { onStateChange(it) },
            onNavigateToClickText = null
        )
        AnimatedVisibility(visible = timeSettingUiState.status) {
            ShowMoreContent(
                onNavigateRepetitiveInterval = onNavigateRepetitiveInterval,
                variantIntervals = variantIntervals,
                onDelete = onDelete,
                repetitiveInterval = "${timeSettingUiState.repeatableInterval.time}  ${timeSettingUiState.repeatableInterval.unit}",
                onAddVariantInterval = onAddVariantInterval,
                onVariantStateChange = onVariantStateChange,
                variantState = timeSettingUiState.isVariantInterval
            )
        }
    }
}


@Composable
fun ShowMoreContent(
    onNavigateRepetitiveInterval: () -> Unit,
    repetitiveInterval: String,
    variantState: Boolean,
    variantIntervals: SnapshotStateList<StatUiState>,
    onVariantStateChange: (Boolean) -> Unit,
    onDelete: (StatUiState) -> Unit,
    onAddVariantInterval: () -> Unit
) {


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomIntervalToggleButton(
            icon = R.drawable.ic_ic24_time,
            title = stringResource(id = R.string.variant_intervals),
            switchTitle = "",
            onNavigateToClickText = null,
            onNavigateRepetitiveInterval = onNavigateRepetitiveInterval,
            onDelete = onDelete,
            onAddVariantInterval = onAddVariantInterval,
            onCheckChange = onVariantStateChange,
            mCheckedState = variantState,
            variantIntervals = variantIntervals,
            repetitiveInterval = repetitiveInterval
        )
        Spacer(modifier = Modifier.height(16.dp))
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
fun AddVariantIntervalBottomSheet(
    onNavigateBack: () -> Unit,
    onSave: (StatUiState) -> Unit = {}
) {
    val interval = remember {
        mutableStateOf(StatUiState(id = (186566..999999999).random(), name = "Wake Up"))
    }
    TimePickerBottomSheet(
        time = AMPMHoursMin(), onSave = {
            val time = it.convert12hrTo24hr()
            interval.value =
                interval.value.copy(
                    hours = time.hour.toString(),
                    midDay = it.dayTime != AMPMHoursMin.DayTime.AM,
                    minutes = time.min.toString()
                )
            if (interval.value.hours.isNotEmpty() || interval.value.hours.isNotBlank()) {
                onSave(interval.value)
            }
        }, onCancel = onNavigateBack
    )
}


@Composable
fun TimePickerDemo(
    onNavigateBack: () -> Unit,
    onValueChange: (Int) -> Unit = {},
    onSave: (RepUiState) -> Unit
) {
    val min = stringResource(R.string.minute)
    val hour = stringResource(R.string.hour)
    AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
        val view = View.inflate(it, R.layout.scheduler_layout_dialog_number_picker, null)

        view
    }, update = {
        val durationUnitPicker = it.findViewById<NumberPicker>(R.id.durationUnitPicker)

        val minOrHourPicker = it.findViewById<NumberPicker>(R.id.durationPicker)

        val cancelButton = it.findViewById<Button>(R.id.cancelButton)
        val saveButton = it.findViewById<Button>(R.id.okButton)

        val data = arrayOf(min, hour)

        var amOrPm: String
        minOrHourPicker.setOnValueChangedListener { picker, _, _ ->
            onValueChange.invoke(picker.value)
        }
        durationUnitPicker.setOnValueChangedListener { picker, _, _ ->
            val i = picker.value
            amOrPm = data[i]
            Log.d("Min or Hr", "Index number --> $i, value for index $i is $amOrPm")

            if (amOrPm == data[0]) {
                minOrHourPicker.minValue = 1
                minOrHourPicker.maxValue = 60
            } else {
                minOrHourPicker.minValue = 1
                minOrHourPicker.maxValue = 6
            }

            Log.d("Min or Hr", "If else value of $amOrPm")
        }

        durationUnitPicker.minValue = 0
        durationUnitPicker.maxValue = data.size - 1
        durationUnitPicker.displayedValues = data

        cancelButton.setOnClickListener {
            onNavigateBack.invoke()
        }
        saveButton.setOnClickListener {
            onSave(
                RepUiState(
                    time = minOrHourPicker.value,
                    unit = if (durationUnitPicker.value == 0) data[0] else data[1]
                )
            )
        }

    })
}


@Composable
fun CustomIntervalToggleButton(
    icon: Int,
    title: String,
    switchTitle: String,
    onNavigateToClickText: (() -> Unit)?,
    onNavigateRepetitiveInterval: () -> Unit,
    variantIntervals: SnapshotStateList<StatUiState>,
    repetitiveInterval: String,
    onDelete: (StatUiState) -> Unit,
    onAddVariantInterval: () -> Unit,
    mCheckedState: Boolean,
    onCheckChange: (Boolean) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
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
                    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = icon),
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
                        arrowTitle = switchTitle,
                        onClick = { onNavigateToClickText?.invoke() })
                    Switch(
                        checked = mCheckedState,
                        onCheckedChange = onCheckChange,
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

        if (mCheckedState) {
            CustomFloatingButton(
                onAddVariantInterval = onAddVariantInterval,
                onDelete = onDelete,
                variantIntervals = variantIntervals
            )
        } else {
            TextSelection(
                imageIcon = Icons.Default.Snooze,
                title = stringResource(R.string.repetitive_intervals),
                arrowTitle = repetitiveInterval,
                btnEnabled = true,
                onNavigateAction = onNavigateRepetitiveInterval
            )
        }
    }
}



@Composable
fun CustomFloatingButton(
    onDelete: (StatUiState) -> Unit,
    onAddVariantInterval: () -> Unit,
    variantIntervals: SnapshotStateList<StatUiState>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        variantIntervals.forEach { state ->
            val ampm: String =
                if (state.midDay) stringResource(R.string.pm) else stringResource(R.string.am)
            val time: String = if (state.midDay) "${state.hours.toInt() - 12}" else state.hours
            CustomVariantInterval(
                time = "$time:${state.minutes} $ampm ",
                tagName = state.name,
                onClick = { onDelete(state) }
            )
        }

        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = onAddVariantInterval,
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