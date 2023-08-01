package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.Wysiwyg
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.SpotifyActivity
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.*
import fit.asta.health.scheduler.model.net.scheduler.Time
import fit.asta.health.scheduler.util.Constants.Companion.getTimeDifference
import fit.asta.health.tools.breathing.model.domain.mapper.convert12hrTo24hr
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AlarmSettingScreen(
    alarmSettingUiState: ASUiState = ASUiState(),
    aSEvent: (AlarmSettingEvent) -> Unit = {},
    navTagSelection: () -> Unit = {},
    navTimeSetting: () -> Unit = {},
    navBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState()
    var currentBottomSheet: AlarmCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch { bottomSheetState.show() }
    }

    AppScaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppTopBar(title = alarmSettingUiState.saveProgress,
            backIcon = Icons.Default.Close,
            onBack = {
                navBack()
                aSEvent(AlarmSettingEvent.ResetUi)
            },
            actions = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(enabled = alarmSettingUiState.saveButtonEnable,
                        onClick = { aSEvent(AlarmSettingEvent.Save(context = context)) }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            })
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            DigitalDemo(
                time = AMPMHoursMin(
                    hours = alarmSettingUiState.time_hours.toInt(),
                    minutes = alarmSettingUiState.time_minutes.toInt(),
                    dayTime = if (alarmSettingUiState.time_midDay) AMPMHoursMin.DayTime.AM else AMPMHoursMin.DayTime.PM
                )
            ) {
                currentBottomSheet = TIME
                openSheet()
            }
            RepeatAlarm(alarmSettingUiState = alarmSettingUiState,
                onDaySelect = { aSEvent(AlarmSettingEvent.SetWeek(it)) })
            OnlyToggleButton(imageIcon = if (alarmSettingUiState.status) Icons.Default.AlarmOn else Icons.Default.AlarmOff,
                title = "Status",
                switchTitle = "",
                onNavigateToClickText = null,
                mCheckedState = alarmSettingUiState.status,
                onCheckClicked = { aSEvent(AlarmSettingEvent.SetStatus(it)) })
            TextSelection(imageIcon = Icons.Default.Tag,
                title = "Tag",
                arrowTitle = alarmSettingUiState.tag_name,
                btnEnabled = true,
                onNavigateAction = {
                    aSEvent(AlarmSettingEvent.GotoTagScreen)
                    navTagSelection()
                })
            TextSelection(imageIcon = Icons.Default.Label,
                title = "Label",
                arrowTitle = alarmSettingUiState.alarm_name,
                btnEnabled = true,
                onNavigateAction = {
                    currentBottomSheet = LABEL
                    openSheet()
                })
            TextSelection(imageIcon = Icons.Default.Description,
                title = "Description",
                arrowTitle = alarmSettingUiState.alarm_description,
                btnEnabled = true,
                onNavigateAction = {
                    currentBottomSheet = DESCRIPTION
                    openSheet()
                })
            TextSelection(imageIcon = Icons.Default.AddAlarm,
                title = "Intervals Settings",
                arrowTitle = alarmSettingUiState.interval,
                btnEnabled = true,
                onNavigateAction = {
                    aSEvent(AlarmSettingEvent.GotoTimeSettingScreen)
                    navTimeSetting()
                })
            TextSelection(imageIcon = if (alarmSettingUiState.mode == "Notification") Icons.Default.NotificationsActive else Icons.Default.Wysiwyg,
                title = "Reminder Mode",
                arrowTitle = alarmSettingUiState.mode,
                btnEnabled = true,
                onNavigateAction = {
                    currentBottomSheet = REMINDER
                    openSheet()
                })
            OnlyToggleButton(imageIcon = Icons.Default.Vibration,
                title = "Vibration ",
                mCheckedState = alarmSettingUiState.vibration_status,
                onCheckClicked = {
                    aSEvent(AlarmSettingEvent.SetVibration(it))
                },
                switchTitle = alarmSettingUiState.vibration,
                btnEnabled = true,
                onNavigateToClickText = {
                    currentBottomSheet = VIBRATION
                    openSheet()
                })
//                OnlyToggleButton(
//                    imageIcon = Icons.Default.Audiotrack,
//                    title = "Sound",
//                    mCheckedState = false,
//                    onCheckClicked = {},
//                    btnEnabled = true,
//                    switchTitle = alarmSettingUiState.tone_name,
//                    onNavigateToClickText = {
//                        currentBottomSheet = SOUND
//                        openSheet()
//                    })

            SoundOptionsUI()

            OnlyToggleButton(
                imageIcon = Icons.Default.NotificationImportant,
                title = "Important",
                mCheckedState = alarmSettingUiState.important,
                onCheckClicked = {
                    aSEvent(AlarmSettingEvent.SetImportant(it))
                },
                switchTitle = "",
                onNavigateToClickText = null
            )
            Text(
                text = "This will make sure you attempt with the help of flashlight, sound changes, vibration etc.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    })
    CustomModelBottomSheet(targetState = bottomSheetState.isVisible,
        sheetState = bottomSheetState,
        content = {
            currentBottomSheet?.let {
                AlarmCreateBtmSheetLayout(
                    sheetLayout = it, closeSheet = { closeSheet() }, aSEvent = aSEvent
                )
            }
        },
        dragHandle = {},
        onClose = { closeSheet() })
}

enum class AlarmCreateBottomSheetTypes {
    LABEL, DESCRIPTION, REMINDER, VIBRATION, TIME
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlarmCreateBtmSheetLayout(
    sheetLayout: AlarmCreateBottomSheetTypes,
    closeSheet: () -> Unit,
    aSEvent: (AlarmSettingEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    when (sheetLayout) {
        LABEL -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomLabelBottomSheetLayout(text = "Labels",
                    label = "Enter your Label",
                    onNavigateBack = {
                        keyboardController?.hide()
                        closeSheet()
                    },
                    onSave = {
                        closeSheet()
                        keyboardController?.hide()
                        aSEvent(AlarmSettingEvent.SetLabel(it))
                    })
            }
        }

        DESCRIPTION -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomLabelBottomSheetLayout(text = "Add Description",
                    label = "Enter Description",
                    onNavigateBack = {
                        closeSheet()
                        keyboardController?.hide()
                    },
                    onSave = {
                        closeSheet()
                        keyboardController?.hide()
                        aSEvent(AlarmSettingEvent.SetDescription(it))
                    })
            }
        }

        REMINDER -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                NotificationBottomSheetLayout(
                    text = "Select Reminder Mode",
                    onNavigateBack = closeSheet,
                    onSave = {
                        closeSheet()
                        aSEvent(AlarmSettingEvent.SetReminderMode(it))
                    })
            }
        }

        VIBRATION -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                VibrationBottomSheetLayout(
                    text = "Select Vibration Intensity",
                    onNavigateBack = closeSheet,
                    onSave = {
                        closeSheet()
                        aSEvent(AlarmSettingEvent.SetVibrationIntensity(it))
                    })
            }
        }

        TIME -> {
            TimePickerBottomSheet(onSave = {
                closeSheet()
                val time = it.convert12hrTo24hr()
                aSEvent(
                    AlarmSettingEvent.SetAlarmTime(
                        Time(
                            hours = time.hour.toString(),
                            midDay = it.dayTime != AMPMHoursMin.DayTime.AM,
                            minutes = it.minutes.toString()
                        )
                    )
                )
            }, onCancel = closeSheet)
        }
    }

}


@Composable
fun TimePickerBottomSheet(onCancel: () -> Unit, onSave: (AMPMHoursMin) -> Unit) {
    var pickerValue by remember { mutableStateOf(AMPMHoursMin(9, 12, AMPMHoursMin.DayTime.PM)) }

    TimePickerClock(
        dividersColor = MaterialTheme.colorScheme.primary,
        value = pickerValue,
        onValueChange = { pickerValue = it },
        onCancel = onCancel,
        onSave = onSave
    )
}

@Composable
fun TimePickerClock(
    onCancel: () -> Unit, onSave: (AMPMHoursMin) -> Unit,
    modifier: Modifier = Modifier,
    value: AMPMHoursMin,
    leadingZero: Boolean = true,
    hoursRange: Iterable<Int> = (1..12),
    minutesRange: Iterable<Int> = (0..59),
    hoursDivider: (@Composable () -> Unit)? = null,
    minutesDivider: (@Composable () -> Unit)? = null,
    onValueChange: (AMPMHoursMin) -> Unit,
    dividersColor: Color = Color.Green,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    val ampmHoursMin by remember(value) {
        mutableStateOf(value)
    }

    var time by remember {
        mutableStateOf("")
    }

    var visibility by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = ampmHoursMin) {
        val timeDifference = getTimeDifference(ampmHoursMin.convert12hrTo24hr())
        time = if (timeDifference < 0) {
            "Selected time is passed"
        } else {
            // Convert the time difference to hours and minutes
            val hour = timeDifference / 60
            val min = timeDifference % 60
            // Format the result as "HH:MM"
            val formattedDifference = String.format("%02d:%02d", hour, min)
            "Ring In $formattedDifference"
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Time", style = MaterialTheme.typography.titleLarge
        )
        AnimatedVisibility(visible = visibility) {
            Text(
                text = time, style = MaterialTheme.typography.titleLarge
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier.weight(.4f),
                textAlign = TextAlign.Center,
                text = "Hours",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier.weight(.4f),
                textAlign = TextAlign.Center,
                text = "Minutes",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.weight(.2f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NumberPicker(modifier = Modifier.weight(1f), value = ampmHoursMin.hours, label = {
                "${if (leadingZero && (abs(it) < 10)) "0" else ""}$it"
            }, onValueChange = {
                visibility = true
                onValueChange(ampmHoursMin.copy(hours = it))
            }, dividersColor = dividersColor, textStyle = textStyle, range = hoursRange
            )

            hoursDivider?.invoke()

            NumberPicker(modifier = Modifier.weight(1f), label = {
                "${if (leadingZero && (abs(it) < 10)) "0" else ""}$it"
            }, value = ampmHoursMin.minutes, onValueChange = {
                visibility = true
                onValueChange(ampmHoursMin.copy(minutes = it))
            }, dividersColor = dividersColor, textStyle = textStyle, range = minutesRange
            )

            minutesDivider?.invoke()

            NumberPicker(modifier = Modifier, value = when (ampmHoursMin.dayTime) {
                AMPMHoursMin.DayTime.AM -> 0
                else -> 1
            }, label = {
                when (it) {
                    0 -> "AM"
                    else -> "PM"
                }
            }, onValueChange = {
                visibility = true
                onValueChange(
                    ampmHoursMin.copy(
                        dayTime = when (it) {
                            0 -> AMPMHoursMin.DayTime.AM
                            else -> AMPMHoursMin.DayTime.PM
                        }
                    )
                )
            }, dividersColor = dividersColor, textStyle = textStyle, range = (0..1)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Red, text = "CANCEL"
            ) { onCancel() }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Blue, text = "SAVE"
            ) { onSave(ampmHoursMin) }
        }
    }
}


@Composable
private fun SoundOptionsUI() {
    val activity = LocalContext.current as Activity

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Audiotrack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = "Sound",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {

                // Opening the Spotify Activity
                val intent = Intent(activity, SpotifyActivity::class.java)
                activity.startActivity(intent)
            }) {
                Text(
                    text = "Spotify",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Local Music",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

