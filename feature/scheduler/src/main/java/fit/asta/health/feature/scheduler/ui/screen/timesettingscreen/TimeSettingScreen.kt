package fit.asta.health.feature.scheduler.ui.screen.timesettingscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.convert12hrTo24hr
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.feature.scheduler.ui.components.SettingsLayout
import fit.asta.health.feature.scheduler.ui.components.SnoozeBottomSheet
import fit.asta.health.feature.scheduler.ui.components.TimePickerBottomSheet
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.TimeUi
import fit.asta.health.feature.scheduler.ui.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.*
import kotlinx.coroutines.launch
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSettingScreen(
    timeSettingUiState: IvlUiState,
    tSEvent: (TimeSettingEvent) -> Unit,
    navBack: () -> Unit
) {
    var currentBottomSheet: TimeSettingCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch { bottomSheetState.show() }
    }
    AppScaffold(modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            SettingsLayout(
                timeSettingUiState = timeSettingUiState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                onNavigateSnooze = {
                    currentBottomSheet = SnoozeSelection
                    openSheet()
                },
                onNavigateAdvanced = {
                    currentBottomSheet = Advanced
                    openSheet()
                },
                onAdvancedStatus = { tSEvent(TimeSettingEvent.SetAdvancedStatus(it)) },
                onEndAlarm = {
                    currentBottomSheet = EndAlarm
                    openSheet()
                },
                onDelete = { tSEvent(TimeSettingEvent.DeleteEndAlarm) },
                onEndStatus = { tSEvent(TimeSettingEvent.SetStatusEndAlarm(it)) },
            )
        },
        topBar = {
            AppTopBar(
                title = stringResource(StringR.string.intervals_and_time_settings),
                onBack = { navBack() },
            )
        })
    CustomModelBottomSheet(
        targetState = bottomSheetState.isVisible,
        sheetState = bottomSheetState,
        content = {
            currentBottomSheet?.let {
                TimeSettingCreateBtmSheetLayout(
                    sheetLayout = it,
                    closeSheet = { closeSheet() },
                    tSEvent = tSEvent
                )
            }
        },
        dragHandle = {},
        onClose = { closeSheet() }
    )
}

enum class TimeSettingCreateBottomSheetTypes {
    SnoozeSelection, Advanced, EndAlarm
}

@Composable
fun TimeSettingCreateBtmSheetLayout(
    tSEvent: (TimeSettingEvent) -> Unit,
    sheetLayout: TimeSettingCreateBottomSheetTypes,
    closeSheet: () -> Unit,
) {
    val context = LocalContext.current
    val title by remember {
        mutableStateOf("Select Time")
    }
    when (sheetLayout) {
        Advanced -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, minutesRange = (5..35),
                onValueChange = {
                    closeSheet()
                    tSEvent(TimeSettingEvent.SetAdvancedDuration(it, context))
                })
        }

        SnoozeSelection -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, minutesRange = (5..15),
                onValueChange = {
                    closeSheet()
                    tSEvent(TimeSettingEvent.SetSnooze(it))
                })
        }


        EndAlarm -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                TimePickerBottomSheet(
                    title = title,
                    time = AMPMHoursMin(),
                    onSave = {
                        closeSheet()
                        val time = it.convert12hrTo24hr()
                        tSEvent(
                            TimeSettingEvent.SetEndAlarm(
                                TimeUi(
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

}