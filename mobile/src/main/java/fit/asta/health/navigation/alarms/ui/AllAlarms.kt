package fit.asta.health.navigation.alarms.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAlarms(
    list: SnapshotStateList<AlarmEntity>,
    onEvent: (AlarmEvent) -> Unit,
) {
    val context = LocalContext.current
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.all_events),
                onBack = { onEvent(AlarmEvent.OnBack) }
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = {
                    onEvent(AlarmEvent.SetAlarm)
                    onEvent(
                        AlarmEvent.NavSchedule(
                            HourMinAmPm(
                                LocalTime.now().hour,
                                LocalTime.now().minute,
                                !LocalTime.now().isBefore(LocalTime.NOON),
                                0
                            )
                        )
                    )
                },
            ) {
                AppIcon(
                    imageVector = Icons.Filled.Add
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        ) {
            item {
                Spacer(modifier = Modifier)
            }
            items(items = list) { alarm ->
                val time = remember {
                    AMPMHoursMin(
                        hours = if (alarm.time.hours > 12) {
                            alarm.time.hours - 12
                        } else if (alarm.time.hours == 0) 12
                        else alarm.time.hours,
                        minutes = alarm.time.minutes,
                        dayTime = if (alarm.time.hours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
                    )
                }
                AlarmItem(
                    modifier = Modifier.padding(horizontal = AppTheme.spacing.level2),
                    title = alarm.info.name,
                    image = alarm.info.url,
                    description = alarm.info.description,
                    time = "${if (time.hours < 10) "0" else ""}${time.hours}:${if (time.minutes < 10) "0" else ""}${time.minutes} ${time.dayTime.name}",
                    state = alarm.status,
                    onStateChange = { onEvent(AlarmEvent.SetAlarmState(it, alarm, context)) },
                    onSchedule = {
                        onEvent(AlarmEvent.EditAlarm(alarm.alarmId))
                    }
                )
            }
            item {
                Spacer(modifier = Modifier)
            }
        }
    }
}

@Composable
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
fun AlarmItemPreview() {
    AppTheme {
        AlarmItem(
            modifier = Modifier,
            image = "",
            title = "Fasting",
            description = "Fasting to cleanse your body",
            time = "9:00am",
            state = true,
            onStateChange = {},
            onSchedule = {}
        )
    }
}

@Composable
@Preview
fun AlarmItem(
    modifier: Modifier = Modifier,
    image: String = "",
    title: String = "Fasting",
    description: String = "Fasting to cleanse your body",
    time: String = "9:00am",
    state: Boolean = true,
    onStateChange: (Boolean) -> Unit = {},
    onSchedule: () -> Unit = {}
) {
    AppCard(
        modifier = modifier,
        onClick = onSchedule
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = AppTheme.spacing.level1)
        ) {
            AppNetworkImage(
                model = ImageRequest.Builder(LocalContext.current).data(getImgUrl(url = image))
                    .crossfade(true).build(),
                contentDescription = R.string.description.toStringFromResId(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(AppTheme.shape.level2)
                    .width(90.dp)
                    .aspectRatio(AppTheme.aspectRatio.square)
            )
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                horizontalAlignment = Alignment.Start
            ) {
                TitleTexts.Level2(text = title, maxLines = 1)
                BodyTexts.Level3(text = description, maxLines = 1)
                TitleTexts.Level2(text = time, maxLines = 1)
            }
            AppSwitch(
                checked = state,
                onCheckedChange = onStateChange
            )
        }
    }
}