package fit.asta.health.scheduler.compose.components

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.shawnlin.numberpicker.NumberPicker
import fit.asta.health.R


data class CustomInterval(
    val time: String = "",
    val tagName: String = "",
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntervalTimeLayout(
    onNavigateBack: () -> Unit,
    onNavigateSnooze: () -> Unit,
    onNavigateRepetitiveInterval: (() -> Unit)?,
) {
    Scaffold(content = {

        SettingsLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            onNavigateAction = onNavigateSnooze,
            onNavigateRepetitiveInterval = onNavigateRepetitiveInterval
        )
    }, topBar = {
        BottomNavigation(content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_close_24),
                        contentDescription = null,
                        Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Intervals and Time Settings",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_check_24),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }, elevation = 10.dp, backgroundColor = Color.White)
    })
}


@Composable
fun SettingsLayout(
    modifier: Modifier = Modifier, onNavigateAction: () -> Unit,
    onNavigateRepetitiveInterval: (() -> Unit)?,
) {

    Column(modifier = modifier) {
        TextSelection(
            image = R.drawable.ic_ic24_alarm_snooze,
            title = "Snooze",
            arrowTitle = "5 Minutes",
            arrowImage = R.drawable.ic_ic24_right_arrow,
            onNavigateAction = onNavigateAction
        )
        Spacer(modifier = Modifier.height(16.dp))
        OnlyToggleButton(
            icon = R.drawable.ic_ic24_notification,
            title = "Advanced\nReminder",
            switchTitle = "15 Minutes",
            onNavigateToClickText = onNavigateAction
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextSelection(
            image = R.drawable.ic_round_access_alarm_24,
            title = "Duration",
            arrowTitle = "30 Minutes",
            arrowImage = R.drawable.ic_ic24_right_arrow,
            onNavigateAction = onNavigateAction
        )
        Spacer(modifier = Modifier.height(16.dp))
        OnlyToggleButton(
            icon = R.drawable.ic_ic24_notification,
            title = "Remind at the end\nof Duration",
            switchTitle = "",
            onNavigateToClickText = null
        )
        Spacer(modifier = Modifier.height(16.dp))

        ShowMoreContent(
            icon = R.drawable.ic_ic24_alert,
            title = "Interval's Status",
            switchTitle = "",
            onNavigateToClickText = null,
            onNavigateRepetitiveInterval = onNavigateRepetitiveInterval
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun ShowMoreContent(
    icon: Int,
    title: String,
    switchTitle: String,
    onNavigateToClickText: (() -> Unit)?,
    onNavigateRepetitiveInterval: (() -> Unit)?,
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
                        onCheckedChange = {
                            mCheckedState.value = it
                        },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            checkedThumbColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
        if (mCheckedState.value) {
            Spacer(modifier = Modifier.height(16.dp))
            onNavigateRepetitiveInterval?.let {
                CustomIntervalToggleButton(
                    icon = R.drawable.ic_ic24_time,
                    title = "Variant Intervals",
                    switchTitle = "",
                    onNavigateToClickText = null,
                    onNavigateRepetitiveInterval = it
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            CustomIntervalText(modifier = Modifier.fillMaxWidth())
        }
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
                    Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
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
private fun SelectableText(arrowTitle: String) {
    ClickableText(
        onClick = {},
        text = AnnotatedString(
            text = arrowTitle,
            spanStyle = SpanStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
    )
}


@Composable
fun SnoozeBottomSheet(onNavigateBack: () -> Unit) {

    AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
        val view = View.inflate(it, R.layout.scheduler_layout_dialog_number_picker, null)

        view
    }, update = {
        val durationUnitPicker = it.findViewById<NumberPicker>(R.id.durationUnitPicker)
        val cancelButton = it.findViewById<Button>(R.id.cancelButton)

        val data = arrayOf("Minute")

        durationUnitPicker.minValue = 1
        durationUnitPicker.maxValue = data.size
        durationUnitPicker.displayedValues = data
        cancelButton.setOnClickListener {
            onNavigateBack.invoke()
        }
    })

}


@Composable
fun TimePickerDemo(onNavigateBack: () -> Unit) {
    AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
        val view = View.inflate(it, R.layout.scheduler_layout_dialog_number_picker, null)

        view
    }, update = {
        val durationUnitPicker = it.findViewById<NumberPicker>(R.id.durationUnitPicker)

        val minOrHourPicker = it.findViewById<NumberPicker>(R.id.durationPicker)

        val cancelButton = it.findViewById<Button>(R.id.cancelButton)

        val data = arrayOf("Minute", "Hour")

        var amOrPm: String

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

    })
}


@Composable
fun CustomIntervalToggleButton(
    icon: Int,
    title: String,
    switchTitle: String,
    onNavigateToClickText: (() -> Unit)?,
    onNavigateRepetitiveInterval: () -> Unit,
) {

    val mCheckedState = remember { mutableStateOf(false) }

    val enabled by remember { mutableStateOf(true) }

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
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
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

        if (mCheckedState.value) {
            CustomFloatingButton()
        } else {
            Spacer(modifier = Modifier.height(32.dp))
            CustomIntervalTextSelection(
                image = R.drawable.ic_ic24_alarm_snooze,
                title = "Repetitive\nIntervals",
                arrowTitle = "1 Hour",
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
                    Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
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
fun CustomFloatingButton() {

    val customIntervalList = listOf(
        CustomInterval(time = "07:05 am", tagName = "Wake Up"),
        CustomInterval(time = "07:05 am", tagName = "Wake Up"),
        CustomInterval(time = "07:05 am", tagName = "Wake Up"),
        CustomInterval(time = "07:05 am", tagName = "Wake Up"),
        CustomInterval(time = "07:05 am", tagName = "Wake Up"),
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        customIntervalList.forEach {
            CustomVariantInterval(time = it.time, tagName = it.tagName)
            Spacer(modifier = Modifier.height(32.dp))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = CircleShape,
                modifier = Modifier.size(60.dp),
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
) {
    Row {
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
                                painter = painterResource(id = R.drawable.ic_ic24_rotate),
                                contentDescription = null,
                                Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = time, fontSize = 16.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    }
                }

                Box {
                    Row {

                        SelectableText(arrowTitle = tagName)

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                            IconButton(onClick = {}) {
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
    }
}