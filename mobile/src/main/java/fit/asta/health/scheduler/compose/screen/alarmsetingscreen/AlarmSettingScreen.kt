package fit.asta.health.scheduler.compose.screen.alarmsetingscreen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.R
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmCreateBottomSheetTypes.*
import fit.asta.health.scheduler.navigation.AlarmSchedulerScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun AlarmSettingScreen(
    navController: NavHostController = rememberNavController(),
) {

    var currentBottomSheet: AlarmCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(modalBottomSheetValue)

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
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
                    sheetLayout = it,
                    closeSheet = { closeSheet() }
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_close_24),
                            contentDescription = null,
                            Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "Alarm Setting",
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
        }, content = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                DigitalDemo()
                RepeatAlarm()
                OnlyToggleButton(
                    icon = R.drawable.ic_ic24_alert,
                    title = "Status",
                    switchTitle = "",
                    onNavigateToClickText = null
                )
                AlarmIconButton(
                    image = R.drawable.ic_ic24_alarm_snooze,
                    title = "Tag",
                    arrowTitle = "Water",
                    arrowImage = R.drawable.ic_ic24_right_arrow, onNavigateToScreen = {
                        navController.navigate(route = AlarmSchedulerScreen.TagSelection.route)
                    }
                )
                AlarmIconButton(
                    image = R.drawable.ic_ic24_label,
                    title = "Label",
                    arrowTitle = "Power Nap",
                    arrowImage = R.drawable.ic_ic24_right_arrow, onNavigateToScreen = {
                        currentBottomSheet = LABEL
                        openSheet()
                    }
                )
                AlarmIconButton(
                    image = R.drawable.ic_ic24_description,
                    title = "Description",
                    arrowTitle = "Relax to energise",
                    arrowImage = R.drawable.ic_ic24_right_arrow, onNavigateToScreen = {
                        currentBottomSheet = DESCRIPTION
                        openSheet()
                    }
                )
                AlarmIconButton(
                    image = R.drawable.ic_ic24_time,
                    title = "Intervals Settings",
                    arrowTitle = "Power Nap",
                    arrowImage = R.drawable.ic_ic24_right_arrow,
                    onNavigateToScreen = {
                        navController.navigate(route = AlarmSchedulerScreen.IntervalSettingsSelection.route)
                    }
                )
                AlarmIconButton(
                    image = R.drawable.ic_ic24_notification,
                    title = "Reminder Mode",
                    arrowTitle = "Notification",
                    arrowImage = R.drawable.ic_ic24_right_arrow,
                    onNavigateToScreen = {
                        currentBottomSheet = REMINDER
                        openSheet()
                    }
                )
                OnlyToggleButton(
                    icon = R.drawable.ic_ic24_vibrate,
                    title = "Vibration ",
                    switchTitle = "Pattern 1", onNavigateToClickText = {
                        currentBottomSheet = VIBRATION
                        openSheet()
                    }
                )
                OnlyToggleButton(
                    icon = R.drawable.ic_ic24_voice,
                    title = "Sound",
                    switchTitle = "Spring", onNavigateToClickText = {
                        currentBottomSheet = SOUND
                        openSheet()
                    }
                )
                OnlyToggleButton(
                    icon = R.drawable.ic_ic24_warning,
                    title = "Important",
                    switchTitle = "", onNavigateToClickText = null
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
    LABEL,
    DESCRIPTION,
    REMINDER,
    VIBRATION,
    SOUND
}

@Composable
fun AlarmCreateBtmSheetLayout(
    sheetLayout: AlarmCreateBottomSheetTypes,
    closeSheet: () -> Unit,

    ) {

    when (sheetLayout) {
        LABEL -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomLabelBottomSheetLayout(
                    text = "Labels",
                    label = "Enter your Label",
                    onNavigateBack = closeSheet
                )
            }
        }
        DESCRIPTION -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomLabelBottomSheetLayout(
                    text = "Add Description",
                    label = "Enter Description",
                    onNavigateBack = closeSheet
                )
            }
        }
        REMINDER -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                NotificationBottomSheetLayout(
                    text = "Select Reminder Mode",
                    onNavigateBack = closeSheet
                )
            }
        }
        VIBRATION -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                VibrationBottomSheetLayout(
                    text = "Select Vibration Intensity",
                    onNavigateBack = closeSheet
                )
            }
        }
        SOUND -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                VibrationBottomSheetLayout(
                    text = "Select Vibration Intensity",
                    onNavigateBack = closeSheet
                )
            }
        }
    }

}
