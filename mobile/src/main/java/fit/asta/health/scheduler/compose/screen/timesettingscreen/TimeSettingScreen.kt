package fit.asta.health.scheduler.compose.screen.timesettingscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import fit.asta.health.common.ui.components.AppTopBar
import fit.asta.health.common.ui.components.CustomModelBottomSheet
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.StatUiState
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSettingScreen(
    list: SnapshotStateList<StatUiState>,
    timeSettingUiState: IvlUiState,
    tSEvent: (TimeSettingEvent) -> Unit,
    navBack: () -> Unit,
    isIntervalDataValid: (IvlUiState) -> Boolean
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
    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = { paddingValues ->
            SettingsLayout(
                timeSettingUiState = timeSettingUiState,
                variantIntervals = list,
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
                onNavigateDuration = {
                    currentBottomSheet = Duration
                    openSheet()
                },
                onNavigateRepetitiveInterval = {
                    currentBottomSheet = RepetitiveInterval
                    openSheet()
                },
                onChoice = { tSEvent(TimeSettingEvent.SetAdvancedStatus(it)) },
                onRemainderAtEnd = {
                    tSEvent(TimeSettingEvent.RemindAtEndOfDuration(it))
                },
                onVariantStateChange = {
                    tSEvent(TimeSettingEvent.SetVariantStatus(it))
                },
                onAddVariantInterval = {
                    currentBottomSheet = VariantInterval
                    openSheet()
                },
                onDelete = { tSEvent(TimeSettingEvent.DeleteVariantInterval(it)) },
                onStateChange = { tSEvent(TimeSettingEvent.SetStatus(it))}
            )
        },
        topBar = {
            AppTopBar(text = "Intervals and Time Settings",
                backIcon = Icons.Default.Close,
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionItems = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        IconButton(
                            onClick = {
                                tSEvent(TimeSettingEvent.Save)
                                if (isIntervalDataValid(timeSettingUiState)) {
                                    navBack()
                                }
                            }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }, onBackPressed = { navBack() })
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
    SnoozeSelection, RepetitiveInterval, Advanced, Duration, VariantInterval
}

@OptIn(ExperimentalComposeUiApi::class)
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

        Duration -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, onValueChange = {
                tSEvent(TimeSettingEvent.SetDuration(it))
            })
        }

        SnoozeSelection -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, onValueChange = {
                tSEvent(TimeSettingEvent.SetSnooze(it))
            })
        }

        RepetitiveInterval -> {
            TimePickerDemo(onNavigateBack = { closeSheet() }, onValueChange = {},
                onSave = {
                    tSEvent(TimeSettingEvent.SetRepetitiveIntervals(it, context))
                    closeSheet()
                })
        }

        VariantInterval -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                val keyboardController = LocalSoftwareKeyboardController.current
                AddVariantIntervalBottomSheet(
                    text = "Variant Interval",
                    onNavigateBack = {
                        closeSheet()
                        keyboardController?.hide()
                    },
                    onSave = {
                        closeSheet()
                        keyboardController?.hide()
                        tSEvent(TimeSettingEvent.AddVariantInterval(it, context))
                    }
                )
            }
        }
    }

}