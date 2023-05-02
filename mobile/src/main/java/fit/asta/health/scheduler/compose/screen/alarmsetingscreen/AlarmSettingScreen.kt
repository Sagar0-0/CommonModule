package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.*
import fit.asta.health.scheduler.util.Constants
import kotlinx.coroutines.launch
import xyz.aprildown.ultimateringtonepicker.RingtonePickerDialog
import xyz.aprildown.ultimateringtonepicker.UltimateRingtonePicker

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.N)
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
    var currentBottomSheet: AlarmCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(modalBottomSheetValue)

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch {
            modalBottomSheetState.hide()
            if (modalBottomSheetValue == ModalBottomSheetValue.Expanded) {
                modalBottomSheetValue = ModalBottomSheetValue.Hidden
            }
        }
    }

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            if (modalBottomSheetValue == ModalBottomSheetValue.Hidden) {
                modalBottomSheetValue = ModalBottomSheetValue.Expanded
            }
        }
    }

    ModalBottomSheetLayout(modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                AlarmCreateBtmSheetLayout(
                    sheetLayout = it, closeSheet = { closeSheet() }, aSEvent = aSEvent
                )
            }
        }) {

        Scaffold(topBar = {
            BottomNavigation(content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = navBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_close_24),
                            contentDescription = null,
                            Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = alarmSettingUiState.saveProgress,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        enabled = alarmSettingUiState.saveButtonEnable,
                        onClick = { aSEvent(AlarmSettingEvent.Save(context = context)) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_check_24),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }, elevation = 10.dp, backgroundColor = Color.White)
        }, content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
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
                    onDaySelect = { aSEvent(AlarmSettingEvent.SetWeek(it)) }
                )
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
    }

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
                        closeSheet()
                        keyboardController?.hide()
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
    dialogTitle: String,
    onRingtonePicked: (ToneUiState) -> Unit,
    onClose: () -> Unit
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
