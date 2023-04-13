package fit.asta.health.scheduler.compose.screen.timesettingscreen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fit.asta.health.R
import fit.asta.health.scheduler.compose.components.SettingsLayout
import fit.asta.health.scheduler.compose.components.SnoozeBottomSheet
import fit.asta.health.scheduler.compose.components.TimePickerDemo
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingCreateBottomSheetTypes.*
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TimeSettingScreen(navController: NavHostController, schedulerViewModel: SchedulerViewModel) {
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
                    schedulerViewModel = schedulerViewModel
                )
            }
        }) {
        val timeSettingUiState=schedulerViewModel.timeSettingUiState.value
        Scaffold(content = { paddingValues ->
            SettingsLayout(
                timeSettingUiState=timeSettingUiState,
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
                onChoice = {schedulerViewModel.TSEvent(TimeSettingEvent.SetAdvancedStatus(it))}
            )
        }, topBar = {
            BottomNavigation(content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                    IconButton(onClick = { /*TODO*/ }) {
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
    SnoozeSelection, RepetitiveInterval,Advanced,Duration
}

@Composable
fun TimeSettingCreateBtmSheetLayout(
    schedulerViewModel: SchedulerViewModel,
    sheetLayout: TimeSettingCreateBottomSheetTypes,
    closeSheet: () -> Unit,

    ) {

    when (sheetLayout) {
        Advanced->{
            SnoozeBottomSheet(onNavigateBack = {  closeSheet()}, onValueChange = {
                schedulerViewModel.TSEvent(TimeSettingEvent.SetAdvancedDuration(it))
            })
        }
        Duration->{
            SnoozeBottomSheet(onNavigateBack = {  closeSheet()}, onValueChange = {
                schedulerViewModel.TSEvent(TimeSettingEvent.SetDuration(it))
            })
        }
        SnoozeSelection -> {
            SnoozeBottomSheet(onNavigateBack = {  closeSheet()}, onValueChange = {
                schedulerViewModel.TSEvent(TimeSettingEvent.SetSnooze(it))
            })
        }
        RepetitiveInterval -> {
            TimePickerDemo(onNavigateBack = { closeSheet()}, onValueChange = {
                schedulerViewModel.TSEvent(TimeSettingEvent.SetDuration(it))
            })
        }
    }

}