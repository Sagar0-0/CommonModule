package fit.asta.health.scheduler.compose.components

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.shawnlin.numberpicker.NumberPicker
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.RepUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.StatUiState



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
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextSelection(
            image = R.drawable.ic_ic24_alarm_snooze,
            title = "Snooze",
            arrowTitle = "${timeSettingUiState.snoozeTime} Minutes",
            arrowImage = R.drawable.ic_ic24_right_arrow,
            onNavigateAction = onNavigateSnooze
        )
        OnlyToggleButton(
            icon = R.drawable.ic_ic24_notification,
            title = "Advanced\nReminder",
            switchTitle = "${timeSettingUiState.advancedReminder.time} Minutes",
            mCheckedState = timeSettingUiState.advancedReminder.status,
            onCheckClicked = { onChoice(it) },
            onNavigateToClickText = onNavigateAdvanced
        )
        TextSelection(
            image = R.drawable.ic_round_access_alarm_24,
            title = "Duration",
            arrowTitle = "${timeSettingUiState.duration} Minutes",
            arrowImage = R.drawable.ic_ic24_right_arrow,
            onNavigateAction = onNavigateDuration
        )
        OnlyToggleButton(
            icon = R.drawable.ic_ic24_notification,
            title = "Remind at the end\nof Duration",
            switchTitle = "",
            mCheckedState = timeSettingUiState.isRemainderAtTheEnd,
            onCheckClicked = { onRemainderAtEnd(it) },
            onNavigateToClickText = null
        )
        OnlyToggleButton(
            icon = R.drawable.ic_ic24_notification,
            title = "Interval's Status",
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
            title = "Variant Intervals",
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
fun TextSelection(
    image: Int,
    title: String,
    arrowTitle: String,
    arrowImage: Int,
    onNavigateAction: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
                        painter = painterResource(id = image),
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
                SelectableText(arrowTitle)
                Box(contentAlignment = Alignment.Center) {
                    IconButton(onClick = onNavigateAction) {
                        Icon(
                            painter = painterResource(id = arrowImage),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectableText(arrowTitle: String, onClick: () -> Unit = {}) {
    TextButton(onClick = onClick) {
        Text(
            text = arrowTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
    text: String,
    onNavigateBack: () -> Unit,
    onSave: (StatUiState) -> Unit = {}
) {
    val interval = remember {
        mutableStateOf(StatUiState(id = (186566..999999999).random(), name = "Wake Up"))
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
            Text(
                text = text,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                if (interval.value.hours.isNotEmpty() || interval.value.hours.isNotBlank()) {
                    onSave(interval.value)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        DigitalDemo(onTimeChange = {
            interval.value =
                interval.value.copy(
                    hours = it.hours,
                    midDay = it.midDay, minutes = it.minutes
                )
        })
        Spacer(modifier = Modifier.height(20.dp))
        CustomTagTextField(
            label = "Interval label",
            onValueChange = { interval.value = interval.value.copy(name = it) })
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun TimePickerDemo(
    onNavigateBack: () -> Unit,
    onValueChange: (Int) -> Unit = {},
    onSave: (RepUiState) -> Unit
) {
    AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
        val view = View.inflate(it, R.layout.scheduler_layout_dialog_number_picker, null)

        view
    }, update = {
        val durationUnitPicker = it.findViewById<NumberPicker>(R.id.durationUnitPicker)

        val minOrHourPicker = it.findViewById<NumberPicker>(R.id.durationPicker)

        val cancelButton = it.findViewById<Button>(R.id.cancelButton)
        val saveButton = it.findViewById<Button>(R.id.okButton)

        val data = arrayOf("Minute", "Hour")

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
                image = R.drawable.ic_ic24_alarm_snooze,
                title = "Repetitive \n Intervals",
                arrowTitle = repetitiveInterval,
                arrowImage = R.drawable.ic_ic24_right_arrow,
                onNavigateAction = onNavigateRepetitiveInterval
            )
        }
    }
}


@Composable
fun CustomIntervalTextSelection(
    image: Int,
    title: String,
    arrowTitle: String,
    arrowImage: Int,
    onNavigateAction: () -> Unit,
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Box {
                Row {

                    SelectableText(arrowTitle)

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        IconButton(onClick = onNavigateAction) {
                            Icon(
                                painter = painterResource(id = arrowImage),
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
}


@Composable
fun CustomIntervalText(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Text(
            text = "Turn On Interval's Status to use Interval Settings",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
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
            val ampm: String = if (state.midDay) "pm" else "am"
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
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
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
                            painter = painterResource(id = R.drawable.ic_round_remove_circle_24),
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