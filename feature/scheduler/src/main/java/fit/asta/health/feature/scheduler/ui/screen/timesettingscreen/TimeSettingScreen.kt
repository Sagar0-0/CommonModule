package fit.asta.health.feature.scheduler.ui.screen.timesettingscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.convert12hrTo24hr
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.feature.scheduler.ui.components.SettingsLayout
import fit.asta.health.feature.scheduler.ui.components.SnoozeBottomSheet
import fit.asta.health.feature.scheduler.ui.components.TimePickerBottomSheet
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.TimeUi
import fit.asta.health.feature.scheduler.ui.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.Advanced
import fit.asta.health.feature.scheduler.ui.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.EndAlarm
import fit.asta.health.feature.scheduler.ui.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.SnoozeSelection
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
    var bottomSheetVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                bottomSheetVisible = false
            }
        }
    }

    val openSheet = {
        scope.launch { bottomSheetState.expand() }
        bottomSheetVisible = true
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

    AppModalBottomSheet(
        sheetVisible = bottomSheetVisible,
        sheetState = bottomSheetState,
        dragHandle = null,
        onDismissRequest = { closeSheet() },
    ) {
        currentBottomSheet?.let {
            TimeSettingCreateBtmSheetLayout(
                sheetLayout = it,
                closeSheet = { closeSheet() },
                tSEvent = tSEvent
            )
        }
    }
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