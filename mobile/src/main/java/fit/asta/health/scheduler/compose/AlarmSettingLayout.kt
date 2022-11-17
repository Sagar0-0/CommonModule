package fit.asta.health.scheduler.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.new_scheduler.view.components.AlarmIconButton
import fit.asta.health.new_scheduler.view.components.DigitalDemo
import fit.asta.health.new_scheduler.view.components.OnlyToggleButton
import fit.asta.health.new_scheduler.view.components.RepeatAlarm


@RequiresApi(Build.VERSION_CODES.N)
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingLayout() {
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
                    color = Color(0xff132839),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_check_24),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        tint = Color(0xff0088FF)
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
            OnlyToggleButton(icon = R.drawable.ic_ic24_alert, title = "Status", switchTitle = "")
            AlarmIconButton(
                image = R.drawable.ic_ic24_alarm_snooze,
                title = "Tag",
                arrowTitle = "Water",
                arrowImage = R.drawable.ic_ic24_right_arrow
            )
            AlarmIconButton(
                image = R.drawable.ic_ic24_label,
                title = "Label",
                arrowTitle = "Power Nap",
                arrowImage = R.drawable.ic_ic24_right_arrow
            )
            AlarmIconButton(
                image = R.drawable.ic_ic24_description,
                title = "Description",
                arrowTitle = "Relax to energise",
                arrowImage = R.drawable.ic_ic24_right_arrow
            )
            AlarmIconButton(
                image = R.drawable.ic_ic24_time,
                title = "Intervals Settings",
                arrowTitle = "Power Nap",
                arrowImage = R.drawable.ic_ic24_right_arrow
            )
            AlarmIconButton(
                image = R.drawable.ic_ic24_notification,
                title = "Reminder Mode",
                arrowTitle = "Notification",
                arrowImage = R.drawable.ic_ic24_right_arrow
            )
            OnlyToggleButton(
                icon = R.drawable.ic_ic24_vibrate,
                title = "Vibration ",
                switchTitle = "Pattern 1"
            )
            OnlyToggleButton(
                icon = R.drawable.ic_ic24_voice,
                title = "Sound",
                switchTitle = "Spring"
            )
            OnlyToggleButton(
                icon = R.drawable.ic_ic24_warning,
                title = "Important",
                switchTitle = ""
            )
            Text(
                text = "This will make sure you attempt with the help of flashlight, sound changes, vibration etc.",
                color = Color(0xff8694A9),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    })
}