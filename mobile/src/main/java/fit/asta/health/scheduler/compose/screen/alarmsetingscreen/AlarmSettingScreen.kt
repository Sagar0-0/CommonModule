package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import fit.asta.health.R
import fit.asta.health.common.ui.CustomTopBar
import fit.asta.health.common.ui.components.CustomModelBottomSheet
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.*
import fit.asta.health.scheduler.util.Constants
import kotlinx.coroutines.launch
import xyz.aprildown.ultimateringtonepicker.RingtonePickerDialog
import xyz.aprildown.ultimateringtonepicker.UltimateRingtonePicker

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

    Scaffold( modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface ,
        contentColor = MaterialTheme.colorScheme.onSurface,
        topBar = {
            CustomTopBar(text = alarmSettingUiState.saveProgress,
                backIcon = Icons.Default.Close,
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionItems = {
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
                }, onBackPressed = {
                    navBack()
                    aSEvent(AlarmSettingEvent.ResetUi)
                })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues).padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                DigitalDemo(onTimeChange = {
                    aSEvent(
                        AlarmSettingEvent.SetAlarmTime(
                            it
                        )
                    )
                })
                RepeatAlarm(alarmSettingUiState = alarmSettingUiState,
                    onDaySelect = { aSEvent(AlarmSettingEvent.SetWeek(it)) })
                OnlyToggleButton(icon = R.drawable.ic_ic24_alert,
                    title = "Status",
                    switchTitle = "",
                    onNavigateToClickText = null,
                    mCheckedState = alarmSettingUiState.status,
                    onCheckClicked = { aSEvent(AlarmSettingEvent.SetStatus(it)) })
                AlarmIconButton(image = R.drawable.ic_ic24_alarm_snooze,
                    title = "Tag",
                    arrowTitle = alarmSettingUiState.tag_name,
                    arrowImage = R.drawable.ic_ic24_right_arrow,
                    onNavigateToScreen = {
                        aSEvent(AlarmSettingEvent.GotoTagScreen)
                        navTagSelection()
                    })
                AlarmIconButton(image = R.drawable.ic_ic24_label,
                    title = "Label",
                    arrowTitle = alarmSettingUiState.alarm_name,
                    arrowImage = R.drawable.ic_ic24_right_arrow,
                    onNavigateToScreen = {
                        currentBottomSheet = LABEL
                        openSheet()
                    })
                AlarmIconButton(image = R.drawable.ic_ic24_description,
                    title = "Description",
                    arrowTitle = alarmSettingUiState.alarm_description,
                    arrowImage = R.drawable.ic_ic24_right_arrow,
                    onNavigateToScreen = {
                        currentBottomSheet = DESCRIPTION
                        openSheet()
                    })
                AlarmIconButton(image = R.drawable.ic_ic24_time,
                    title = "Intervals Settings",
                    arrowTitle = alarmSettingUiState.interval,
                    arrowImage = R.drawable.ic_ic24_right_arrow,
                    onNavigateToScreen = {
                        aSEvent(AlarmSettingEvent.GotoTimeSettingScreen)
                        navTimeSetting()
                    })
                AlarmIconButton(image = R.drawable.ic_ic24_notification,
                    title = "Reminder Mode",
                    arrowTitle = alarmSettingUiState.mode,
                    arrowImage = R.drawable.ic_ic24_right_arrow,
                    onNavigateToScreen = {
                        currentBottomSheet = REMINDER
                        openSheet()
                    })
                OnlyToggleButton(icon = R.drawable.ic_ic24_vibrate,
                    title = "Vibration ",
                    mCheckedState = alarmSettingUiState.vibration_status,
                    onCheckClicked = {
                        aSEvent(AlarmSettingEvent.SetVibration(it))
                    },
                    switchTitle = alarmSettingUiState.vibration,
                    onNavigateToClickText = {
                        currentBottomSheet = VIBRATION
                        openSheet()
                    })
                OnlyToggleButton(icon = R.drawable.ic_ic24_voice,
                    title = "Sound",
                    mCheckedState = false,
                    onCheckClicked = {},
                    switchTitle = alarmSettingUiState.tone_name,
                    onNavigateToClickText = {
                        currentBottomSheet = SOUND
                        openSheet()
                    })
                OnlyToggleButton(
                    icon = R.drawable.ic_ic24_warning,
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
    CustomModelBottomSheet(
        targetState = bottomSheetState.isVisible,
        sheetState = bottomSheetState,
        content = {
            currentBottomSheet?.let {
                AlarmCreateBtmSheetLayout(
                    sheetLayout = it, closeSheet = { closeSheet() }, aSEvent = aSEvent
                )
            }
        },
        dragHandle = {},
        onClose = { closeSheet() }
    )
}

enum class AlarmCreateBottomSheetTypes {
    LABEL, DESCRIPTION, REMINDER, VIBRATION, SOUND
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
                NotificationBottomSheetLayout(text = "Select Reminder Mode",
                    onNavigateBack = closeSheet,
                    onSave = {
                        closeSheet()
                        aSEvent(AlarmSettingEvent.SetReminderMode(it))
                    })
            }
        }

        VIBRATION -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                VibrationBottomSheetLayout(text = "Select Vibration Intensity",
                    onNavigateBack = closeSheet,
                    onSave = {
                        closeSheet()
                        aSEvent(AlarmSettingEvent.SetVibrationIntensity(it))
                    })
            }
        }

        SOUND -> {
            RingtonePickerDialog(dialogTitle = "Select Sound", onRingtonePicked = {
                closeSheet()
                aSEvent(AlarmSettingEvent.SetSound(it))
            }, onClose = closeSheet)
        }
    }

}

@Composable
fun RingtonePickerDialog(
    dialogTitle: String, onRingtonePicked: (ToneUiState) -> Unit, onClose: () -> Unit
) {
    val fragmentManager = (LocalContext.current as AppCompatActivity).supportFragmentManager
    val callback = remember {
        object : UltimateRingtonePicker.RingtonePickerListener {
            override fun onRingtonePicked(ringtones: List<UltimateRingtonePicker.RingtoneEntry>) {
                Log.d("TAGTAGTAG", "callback: ")
                if (ringtones.isNotEmpty()) {
                    onRingtonePicked(
                        ToneUiState(
                            name = ringtones[0].name, uri = ringtones[0].uri.toString()
                        )
                    )
                }
                onClose()
            }
        }
    }
    val dialog = RingtonePickerDialog.createEphemeralInstance(
        settings = Constants.settings, dialogTitle, callback
    )
    dialog.show(fragmentManager, null)
    Log.d("TAGTAGTAG", "show: ")


}
