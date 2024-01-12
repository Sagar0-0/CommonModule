package fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.Wysiwyg
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.convert12hrTo24hr
import fit.asta.health.data.scheduler.remote.net.scheduler.Time
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.CustomModelBottomSheet
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.ui.SpotifyActivity
import fit.asta.health.feature.scheduler.ui.components.CustomLabelBottomSheetLayout
import fit.asta.health.feature.scheduler.ui.components.DateSelection
import fit.asta.health.feature.scheduler.ui.components.DigitalDemo
import fit.asta.health.feature.scheduler.ui.components.NotificationBottomSheetLayout
import fit.asta.health.feature.scheduler.ui.components.OnlyToggleButton
import fit.asta.health.feature.scheduler.ui.components.RepeatAlarm
import fit.asta.health.feature.scheduler.ui.components.TextSelection
import fit.asta.health.feature.scheduler.ui.components.TimePickerBottomSheet
import fit.asta.health.feature.scheduler.ui.components.VibrationBottomSheetLayout
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.DATERANGE
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.DESCRIPTION
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.LABEL
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.REMINDER
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.TIME
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.VIBRATION
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
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
    val state = rememberDateRangePickerState(
        initialSelectedStartDateMillis = alarmSettingUiState.selectedStartDateMillis,
        initialSelectedEndDateMillis = alarmSettingUiState.selectedEndDateMillis,
        yearRange = DatePickerDefaults.YearRange,
    )
    var currentBottomSheet: AlarmCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }
    val areInputsValid by remember(alarmSettingUiState) {
        mutableStateOf(
            alarmSettingUiState.alarmName.isNotEmpty() &&
                    alarmSettingUiState.alarmDescription.isNotEmpty() &&
                    alarmSettingUiState.tagName.isNotEmpty()
        )
    }
    var onClick by rememberSaveable {
        mutableStateOf(false)
    }
    val nameBg: Color? by remember(onClick, alarmSettingUiState) {
        mutableStateOf(if (alarmSettingUiState.alarmName.isEmpty() && onClick) Color.Red else null)
    }
    val descriptionBg: Color? by remember(onClick, alarmSettingUiState) {
        mutableStateOf(if (alarmSettingUiState.alarmDescription.isEmpty() && onClick) Color.Red else null)
    }
    val tagBg: Color? by remember(onClick, alarmSettingUiState) {
        mutableStateOf(if (alarmSettingUiState.tagName.isEmpty() && onClick) Color.Red else null)
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
        modifier = Modifier
            .fillMaxSize()
            .imePadding(), snackBarHostState = snackBarHostState,
        topBar = {
            AppTopBar(
                title = stringResource(StringR.string.alarm_setting),
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
                        AppIconButton(
                            imageVector = Icons.Default.Check,
                        ) {
                            if (areInputsValid) {
                                aSEvent(AlarmSettingEvent.Save(context = context))
                                navBack()
                            } else {
                                onClick = true
                            }
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
                        } else if (alarmSettingUiState.timeHours == 0) {
                            12
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
                    title = stringResource(id = StringR.string.status),
                    testTag = stringResource(id = StringR.string.status),
                    switchTitle = "",
                    onNavigateToClickText = null,
                    mCheckedState = alarmSettingUiState.status,
                    onCheckClicked = { aSEvent(AlarmSettingEvent.SetStatus(it)) })
                DateSelection(imageIcon = Icons.Default.CalendarMonth,
                    title = stringResource(id = StringR.string.date_range),
                    arrowTitle = "${getFormattedDate(alarmSettingUiState.selectedStartDateMillis)} : ${
                        if (alarmSettingUiState.selectedEndDateMillis != null)
                            alarmSettingUiState.selectedEndDateMillis?.let {
                                getFormattedDate(it)
                            } else "Optional"
                    } ",
                    btnEnabled = true, color = tagBg,
                    onNavigateAction = {
                        currentBottomSheet = DATERANGE
                        openSheet()
                    })
                TextSelection(imageIcon = Icons.Default.Tag,
                    title = stringResource(id = StringR.string.tag),
                    testTag = stringResource(id = StringR.string.tag),
                    arrowTitle = alarmSettingUiState.tagName,
                    btnEnabled = true, color = tagBg,
                    onNavigateAction = {
                        navTagSelection()
                    })
                TextSelection(
                    imageIcon = Icons.Default.Label,
                    title = stringResource(id = StringR.string.label),
                    testTag = stringResource(id = StringR.string.label),
                    arrowTitle = alarmSettingUiState.alarmName,
                    btnEnabled = true, color = nameBg,
                    onNavigateAction = {
                        currentBottomSheet = LABEL
                        openSheet()
                    })
                TextSelection(imageIcon = Icons.Default.Description,
                    title = stringResource(id = StringR.string.description),
                    testTag = stringResource(id = StringR.string.description),
                    arrowTitle = alarmSettingUiState.alarmDescription,
                    btnEnabled = true, color = descriptionBg,
                    onNavigateAction = {
                        currentBottomSheet = DESCRIPTION
                        openSheet()
                    })
                TextSelection(imageIcon = Icons.Default.AddAlarm,
                    title = stringResource(StringR.string.intervals_settings),
                    testTag = stringResource(StringR.string.intervals_settings),
                    arrowTitle = stringResource(StringR.string.optional),
                    btnEnabled = true,
                    onNavigateAction = {
                        if (areInputsValid) {
                            navTimeSetting()
                        } else {
                            onClick = true
                        }
                    })
                TextSelection(imageIcon = if (alarmSettingUiState.mode == "Notification") Icons.Default.NotificationsActive else Icons.Default.Wysiwyg,
                    title = stringResource(id = StringR.string.reminder_mode),
                    testTag = stringResource(id = StringR.string.reminder_mode),
                    arrowTitle = alarmSettingUiState.mode,
                    btnEnabled = true,
                    onNavigateAction = {
                        currentBottomSheet = REMINDER
                        openSheet()
                    })
                OnlyToggleButton(imageIcon = Icons.Default.Vibration,
                    title = stringResource(StringR.string.vibration),
                    testTag = stringResource(StringR.string.vibration),
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
                    title = stringResource(StringR.string.important),
                    testTag = stringResource(StringR.string.important),
                    mCheckedState = alarmSettingUiState.important,
                    onCheckClicked = {
                        aSEvent(AlarmSettingEvent.SetImportant(it))
                    },
                    switchTitle = "",
                    onNavigateToClickText = null
                )
                CaptionTexts.Level2(
                    text = stringResource(id = StringR.string.this_will_make_sure_you_attempt_with_the_help_of_flashlight_sound_changes_vibration_etc),
                    color = AppTheme.colors.onSurfaceVariant,
                    maxLines = 2
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
                    alarmSettingUiState = alarmSettingUiState,
                    state = state
                )
            }
        },
        dragHandle = {},
        onClose = { closeSheet() })
}

enum class AlarmCreateBottomSheetTypes {
    LABEL, DESCRIPTION, REMINDER, VIBRATION, TIME, DATERANGE
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlarmCreateBtmSheetLayout(
    sheetLayout: AlarmCreateBottomSheetTypes,
    alarmSettingUiState: ASUiState,
    state: DateRangePickerState,
    closeSheet: () -> Unit,
    aSEvent: (AlarmSettingEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var title by remember {
        mutableStateOf("Select Time")
    }
    when (sheetLayout) {
        LABEL -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomLabelBottomSheetLayout(
                    text = stringResource(StringR.string.labels),
                    label = StringR.string.enter_your_label,
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
                    text = stringResource(id = StringR.string.add_description),
                    label = StringR.string.enter_description,
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
                    text = stringResource(id = StringR.string.select_reminder_mode),
                    onNavigateBack = closeSheet,
                    onSave = {
                        closeSheet()
                        aSEvent(AlarmSettingEvent.SetReminderMode(it))
                    })
            }
        }

        VIBRATION -> {
            VibrationBottomSheetLayout(
                text = stringResource(StringR.string.select_vibration_pattern),
                onNavigateBack = closeSheet,
                onSave = {
                    closeSheet()
                    aSEvent(AlarmSettingEvent.SetVibrationIntensity(it))
                })
        }

        TIME -> {
            TimePickerBottomSheet(
                title = title,
                time = AMPMHoursMin(
                    hours = if (alarmSettingUiState.timeHours > 12) {
                        alarmSettingUiState.timeHours - 12
                    } else if (alarmSettingUiState.timeHours == 0) {
                        12
                    } else alarmSettingUiState.timeHours,
                    minutes = alarmSettingUiState.timeMinutes,
                    dayTime = if (alarmSettingUiState.timeHours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
                ),
                onSave = {
                    val time = it.convert12hrTo24hr()
                    val calendar = Calendar.getInstance()
                    val count = alarmSettingUiState.week.getDistanceToNextDay(calendar)
                    calendar[Calendar.DAY_OF_MONTH] = calendar.get(Calendar.DAY_OF_MONTH) + count
                    calendar[Calendar.HOUR_OF_DAY] = time.hour
                    calendar[Calendar.MINUTE] = time.min
                    calendar[Calendar.SECOND] = 0
                    calendar[Calendar.MILLISECOND] = 0
                    val old = alarmSettingUiState.alarmList.find { alarmIns ->
                        alarmIns.alarmTime == calendar
                    }
                    if (old != null) {
                        title = "This alarm time is already set for other "
                        return@TimePickerBottomSheet
                    } else {
                        closeSheet()
                        aSEvent(
                            AlarmSettingEvent.SetAlarmTime(
                                Time(
                                    hours = time.hour,
                                    minutes = it.minutes
                                )
                            )
                        )
                    }
                }, onCancel = closeSheet
            )
        }

        DATERANGE -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {
                DateRangePickerSample(state)
                AppFilledButton(
                    onClick = {
                        state.selectedStartDateMillis?.let { start ->
                            aSEvent(
                                AlarmSettingEvent.SetDateRange(
                                    start,
                                    state.selectedEndDateMillis
                                )
                            )
                        }
                        closeSheet()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp)
                        .navigationBarsPadding(),
                    textToShow = "Done"
                )
            }
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
        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)) {
            Box(contentAlignment = Alignment.Center) {
                AppIcon(
                    imageVector = Icons.Default.Audiotrack,
                    contentDescription = null,
                    tint = AppTheme.colors.primary
                )
            }
            TitleTexts.Level2(
                text = stringResource(StringR.string.sound),
                color = AppTheme.colors.onSecondaryContainer
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppTextButton(
                textToShow = stringResource(StringR.string.spotify),
            ) {
                // Opening the Spotify Activity
                val intent = Intent(activity, SpotifyActivity::class.java)
                activity.startActivity(intent)
            }

            AppTextButton(
                textToShow = stringResource(StringR.string.local_music)
            ) { /*TODO*/ }
        }
    }
}

fun getFormattedDate(timeInMillis: Long, format: String = "dd MMM yy"): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(calender.timeInMillis)
}

fun dateValidator(): (Long) -> Boolean {
    return { timeInMillis ->
        val selectedDate = Calendar.getInstance()
        selectedDate.timeInMillis = timeInMillis
        val todayCalendar = Calendar.getInstance()

        (todayCalendar.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR) &&
                todayCalendar.get(Calendar.DAY_OF_YEAR) == selectedDate.get(Calendar.DAY_OF_YEAR)) ||
                timeInMillis > Calendar.getInstance().timeInMillis
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerSample(state: DateRangePickerState) {
    DateRangePicker(
        state = state,
        modifier = Modifier,
        title = {
            TitleTexts.Level2(
                text = "Select date range ",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        },
        headline = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    (if (state.selectedStartDateMillis != null) state.selectedStartDateMillis?.let {
                        getFormattedDate(it, "dd/MM/yyyy")
                    } else "Start Date")?.let { BodyTexts.Level2(text = it) }
                }
                Box(Modifier.weight(1f)) {
                    (if (state.selectedEndDateMillis != null) state.selectedEndDateMillis?.let {
                        getFormattedDate(it, "dd/MM/yyyy")
                    } else "End Date")?.let { BodyTexts.Level2(text = it) }
                }
                Box(Modifier.weight(0.2f)) {
                    AppIcon(imageVector = Icons.Default.Done, contentDescription = "Okk")
                }

            }
        },
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            disabledDayContentColor = Color.Gray,
            todayDateBorderColor = Color.Blue,
            dayInSelectionRangeContainerColor = Color.LightGray,
            dayInSelectionRangeContentColor = Color.White,
            selectedDayContainerColor = Color.Green
        )
    )
}