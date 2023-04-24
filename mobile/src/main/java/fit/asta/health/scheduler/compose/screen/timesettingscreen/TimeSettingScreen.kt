package fit.asta.health.scheduler.compose.screen.timesettingscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.IvlUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.StatUiState
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TimeSettingScreen(
    list: SnapshotStateList<StatUiState>,
    timeSettingUiState: IvlUiState,
    tSEvent: (TimeSettingEvent) -> Unit,
    navBack: () -> Unit,
    isIntervalDataValid:(IvlUiState)->Boolean
) {
    val context= LocalContext.current
    var currentBottomSheet: TimeSettingCreateBottomSheetTypes? by remember {
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
                TimeSettingCreateBtmSheetLayout(
                    sheetLayout = it,
                    closeSheet = { closeSheet() },
                    tSEvent = tSEvent
                )
            }
        }) {

        Scaffold(content = { paddingValues ->
            SettingsLayout(
                timeSettingUiState = timeSettingUiState,
                variantIntervals = list,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
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
                onDelete = { tSEvent(TimeSettingEvent.DeleteVariantInterval(it)) }
            )
        }, topBar = {
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
                        text = "Intervals and Time Settings",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = {

                        tSEvent(TimeSettingEvent.Save)
                        if (isIntervalDataValid( timeSettingUiState)) {
                            navBack()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_check_24),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }, elevation = 10.dp, backgroundColor = Color.White)
        })
    }


}

enum class TimeSettingCreateBottomSheetTypes {
    SnoozeSelection, RepetitiveInterval, Advanced, Duration, VariantInterval
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TimeSettingCreateBtmSheetLayout(
    tSEvent: (TimeSettingEvent) -> Unit,
    sheetLayout: TimeSettingCreateBottomSheetTypes,
    closeSheet: () -> Unit,
    ) {
     val context= LocalContext.current
    when (sheetLayout) {
        Advanced -> {
            SnoozeBottomSheet(onNavigateBack = { closeSheet() }, onValueChange = {
                tSEvent(TimeSettingEvent.SetAdvancedDuration(it,context))
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
                    tSEvent(TimeSettingEvent.SetRepetitiveIntervals(it,context))
                    closeSheet()
                })
        }
        VariantInterval -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                AddVariantIntervalBottomSheet(
                    text = "Variant Interval",
                    onNavigateBack = closeSheet,
                    onSave = {
                        closeSheet()
                        tSEvent(TimeSettingEvent.AddVariantInterval(it,context))
                    }
                )
            }
        }
    }

}