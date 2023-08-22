package fit.asta.health.scheduler.ui.screen.timesettingscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.scheduler.ui.components.SettingsLayout
import fit.asta.health.scheduler.ui.components.SnoozeBottomSheet
import fit.asta.health.scheduler.ui.components.TimePickerBottomSheet
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.AMPMHoursMin
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.ui.screen.alarmsetingscreen.TimeUi
import fit.asta.health.scheduler.ui.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.*
import fit.asta.health.tools.breathing.model.domain.mapper.convert12hrTo24hr
import kotlinx.coroutines.launch

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
                title = stringResource(R.string.intervals_and_time_settings),
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
    when (sheetLayout) {
        Advanced -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, onValueChange = {
                tSEvent(TimeSettingEvent.SetAdvancedDuration(it, context))
            })
        }

        SnoozeSelection -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, onValueChange = {
                tSEvent(TimeSettingEvent.SetSnooze(it))
            })
        }


        EndAlarm -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                TimePickerBottomSheet(
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