package fit.asta.health.scheduler.ui.screen.alarmsetingscreen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.scheduler.data.api.net.scheduler.Time
import fit.asta.health.scheduler.ui.SpotifyActivity
import fit.asta.health.scheduler.ui.components.CustomLabelBottomSheetLayout
import fit.asta.health.scheduler.ui.components.DigitalDemo
import fit.asta.health.scheduler.ui.components.NotificationBottomSheetLayout
import fit.asta.health.scheduler.ui.components.OnlyToggleButton
import fit.asta.health.scheduler.ui.components.RepeatAlarm
import fit.asta.health.scheduler.ui.components.TextSelection
import fit.asta.health.scheduler.ui.components.TimePickerBottomSheet
import fit.asta.health.scheduler.ui.components.VibrationBottomSheetLayout
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.*
import fit.asta.health.tools.breathing.model.domain.mapper.convert12hrTo24hr
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingScreen(
    alarmSettingUiState: ASUiState = ASUiState(),
    uiError: String,
    areInputsValid: Boolean,
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

    AppScaffold(
        modifier = Modifier.fillMaxSize(), snackBarHostState = snackBarHostState,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.alarm_setting),
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
                        IconButton(enabled = areInputsValid,
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
                        hours = if (alarmSettingUiState.timeHours > 12) {
                            alarmSettingUiState.timeHours - 12
                        } else alarmSettingUiState.timeHours,
                        minutes = alarmSettingUiState.timeMinutes,
                        dayTime = if (alarmSettingUiState.timeHours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
                    )
                ) {
                    currentBottomSheet = TIME
                    openSheet()
                }
                RepeatAlarm(weekdays = alarmSettingUiState.week,
                    onDaySelect = { aSEvent(AlarmSettingEvent.SetWeek(it)) })
                OnlyToggleButton(imageIcon = if (alarmSettingUiState.status) Icons.Default.AlarmOn else Icons.Default.AlarmOff,
                    title = stringResource(id = R.string.status),
                    switchTitle = "",
                    onNavigateToClickText = null,
                    mCheckedState = alarmSettingUiState.status,
                    onCheckClicked = { aSEvent(AlarmSettingEvent.SetStatus(it)) })
                TextSelection(imageIcon = Icons.Default.Tag,
                    title = stringResource(id = R.string.tag),
                    arrowTitle = alarmSettingUiState.tagName,
                    btnEnabled = true,
                    onNavigateAction = {
                        aSEvent(AlarmSettingEvent.GotoTagScreen)
                        navTagSelection()
                    })
                TextSelection(imageIcon = Icons.Default.Label,
                    title = stringResource(id = R.string.label),
                    arrowTitle = alarmSettingUiState.alarmName,
                    btnEnabled = true,
                    onNavigateAction = {
                        currentBottomSheet = LABEL
                        openSheet()
                    })
                TextSelection(imageIcon = Icons.Default.Description,
                    title = stringResource(id = R.string.description),
                    arrowTitle = alarmSettingUiState.alarmDescription,
                    btnEnabled = true,
                    onNavigateAction = {
                        currentBottomSheet = DESCRIPTION
                        openSheet()
                    })
                TextSelection(imageIcon = Icons.Default.AddAlarm,
                    title = stringResource(R.string.intervals_settings),
                    arrowTitle = stringResource(R.string.optional),
                    btnEnabled = areInputsValid,
                    onNavigateAction = {
                        aSEvent(AlarmSettingEvent.GotoTimeSettingScreen)
                        navTimeSetting()
                    })
                TextSelection(imageIcon = if (alarmSettingUiState.mode == "Notification") Icons.Default.NotificationsActive else Icons.Default.Wysiwyg,
                    title = stringResource(id = R.string.reminder_mode),
                    arrowTitle = alarmSettingUiState.mode,
                    btnEnabled = true,
                    onNavigateAction = {
                        currentBottomSheet = REMINDER
                        openSheet()
                    })
                OnlyToggleButton(imageIcon = Icons.Default.Vibration,
                    title = stringResource(R.string.vibration),
                    mCheckedState = alarmSettingUiState.vibrationStatus,
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
                    title = stringResource(R.string.important),
                    mCheckedState = alarmSettingUiState.important,
                    onCheckClicked = {
                        aSEvent(AlarmSettingEvent.SetImportant(it))
                    },
                    switchTitle = "",
                    onNavigateToClickText = null
                )
                Text(
                    text = stringResource(id = R.string.this_will_make_sure_you_attempt_with_the_help_of_flashlight_sound_changes_vibration_etc),
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
                CustomLabelBottomSheetLayout(
                    text = stringResource(R.string.labels),
                    label = stringResource(R.string.enter_your_label),
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
                CustomLabelBottomSheetLayout(
                    text = stringResource(id = R.string.add_description),
                    label = stringResource(id = R.string.enter_description),
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
                    text = stringResource(id = R.string.select_reminder_mode),
                    onNavigateBack = closeSheet,
                    onSave = {
                        closeSheet()
                        aSEvent(AlarmSettingEvent.SetReminderMode(it))
                    })
            }
        }

        VIBRATION -> {
            VibrationBottomSheetLayout(
                text = stringResource(R.string.select_vibration_pattern),
                onNavigateBack = closeSheet,
                onSave = {
                    closeSheet()
                    aSEvent(AlarmSettingEvent.SetVibrationIntensity(it))
                })
        }

        TIME -> {
            TimePickerBottomSheet(
                time = AMPMHoursMin(
                    hours = if (alarmSettingUiState.timeHours > 12) {
                        alarmSettingUiState.timeHours - 12
                    } else alarmSettingUiState.timeHours,
                    minutes = alarmSettingUiState.timeMinutes,
                    dayTime = if (alarmSettingUiState.timeHours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
                ),
                onSave = {
                    closeSheet()
                    val time = it.convert12hrTo24hr()
                    aSEvent(
                        AlarmSettingEvent.SetAlarmTime(
                            Time(
                                hours = time.hour,
                                minutes = it.minutes
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
                text = stringResource(R.string.sound),
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
                    text = stringResource(R.string.spotify),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(R.string.local_music),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

