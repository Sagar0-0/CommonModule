package fit.asta.health.navigation.today.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAlarms(
    list: SnapshotStateList<AlarmEntity>,
    onEvent: (AlarmEvent) -> Unit
) {
    val context = LocalContext.current
    AppScaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "All Events",
                onBack = { onEvent(AlarmEvent.OnBack) })
        }) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
        ) {
            items(list) { alarm ->
                val time = AMPMHoursMin(
                    hours = if (alarm.time.hours > 12) {
                        alarm.time.hours - 12
                    } else alarm.time.hours,
                    minutes = alarm.time.minutes,
                    dayTime = if (alarm.time.hours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
                )
                AlarmItem(
                    image = alarm.info.url,
                    description = alarm.info.description,
                    time = "${if (time.hours < 10) "0" else ""}${time.hours}:${if (time.minutes < 10) "0" else ""}${time.minutes} ${time.dayTime.name}",
                    state = alarm.status,
                    onStateChange = { onEvent(AlarmEvent.SetAlarmState(it, alarm, context)) }
                )
            }
        }
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
    onStateChange: (Boolean) -> Unit = {}
) {
    AppCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = spacing.small)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(getImgUrl(url = image))
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.placeholder_tag),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(120.dp)
                    .width(80.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.small),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            AppButtons.AppToggleButton(checked = state, onCheckedChange = onStateChange)
        }
    }
}