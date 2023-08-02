package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import android.app.Activity
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.SpotifyActivity
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.*
import fit.asta.health.scheduler.model.net.scheduler.Time
import fit.asta.health.tools.breathing.model.domain.mapper.convert12hrTo24hr
import kotlinx.coroutines.launch

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
    val snackBarHostState = remember { SnackbarHostState() }

    AppScaffold(modifier = Modifier.fillMaxSize(), snackBarHostState = snackBarHostState,
        topBar = {
            AppTopBar(title = "Alarm Setting",
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
                            onClick = {
                                aSEvent(AlarmSettingEvent.Save(context = context))
                                navBack()
                            }
                        ) {
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
                        dayTime = if (alarmSettingUiState.time_midDay) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
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
                    arrowTitle = "Optional",
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
                    sheetLayout = it,
                    closeSheet = { closeSheet() },
                    aSEvent = aSEvent,
                    alarmSettingUiState = alarmSettingUiState
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
    alarmSettingUiState: ASUiState,
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
            TimePickerBottomSheet(
                time = AMPMHoursMin(
                    hours = if (alarmSettingUiState.time_hours.toInt() > 12) {
                        alarmSettingUiState.time_hours.toInt() - 12
                    } else alarmSettingUiState.time_hours.toInt(),
                    minutes = alarmSettingUiState.time_minutes.toInt(),
                    dayTime = if (alarmSettingUiState.time_midDay) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
                ),
                onSave = {
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
                }, onCancel = closeSheet
            )
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

