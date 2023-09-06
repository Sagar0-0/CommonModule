package fit.asta.health.navigation.today.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.SkipNext
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.Constants.getHourMinAmPm
import fit.asta.health.common.utils.Constants.goToTool
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.functional.WeatherCardImage
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDialog
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.feature.scheduler.ui.components.WeatherCard
import fit.asta.health.main.Graph
import fit.asta.health.main.view.ALL_ALARMS_ROUTE
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.time.LocalTime

@Composable
fun TodayContent(
    uiState: TodayData,
    defaultScheduleVisibility: Boolean,
    listMorning: SnapshotStateList<AlarmEntity>,
    listAfternoon: SnapshotStateList<AlarmEntity>,
    listEvening: SnapshotStateList<AlarmEntity>,
    listNextDay: SnapshotStateList<AlarmEntity>,
    hSEvent: (HomeEvent) -> Unit,
    onNav: (String) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var deleteDialog by rememberSaveable { mutableStateOf(false) }
    var skipDialog by rememberSaveable { mutableStateOf(false) }
    var deletedItem by remember { mutableStateOf<AlarmEntity?>(null) }
    var skipItem by remember { mutableStateOf<AlarmEntity?>(null) }
    var evenType by remember { mutableStateOf(Event.Morning) }
    val context = LocalContext.current

    if (deleteDialog) {
        AlertDialogPopUp(
            content = "Are you sure you want to delete this alarm?",
            actionButton = stringResource(id = R.string.delete),
            onDismiss = { deleteDialog = false },
            onDone = {
                deletedItem?.let { hSEvent(HomeEvent.DeleteAlarm(it, context)) }
                deleteDialog = false
            })
    }
    if (skipDialog) {
        AlertDialogPopUp(
            content = "Are you sure you want to skip this alarm?",
            actionButton = stringResource(id = R.string.skip),
            onDismiss = { skipDialog = false },
            onDone = {
                skipItem?.let { hSEvent(HomeEvent.SkipAlarm(it, context)) }
                skipDialog = false
            })
    }
    AppScaffold(
        snackBarHostState = snackBarHostState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    hSEvent(HomeEvent.SetAlarm)
                    hSEvent(
                        HomeEvent.NavSchedule(
                            HourMinAmPm(
                                LocalTime.now().hour,
                                LocalTime.now().minute,
                                !LocalTime.now().isBefore(LocalTime.NOON),
                                0
                            )
                        )
                    )
                    onNav(Graph.Scheduler.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(50.dp),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
        ) {
            item { NameAndMoodHomeScreenHeader(onAlarm = { onNav(ALL_ALARMS_ROUTE) }) }
            item {
                WeatherCardImage(
                    temperature = uiState.temperature,
                    location = uiState.location,
                    date = uiState.date
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = uiState.slots) { slot ->
                        WeatherCard(
                            weatherData = slot,
                            onSchedule = {
                                hSEvent(
                                    HomeEvent.NavSchedule(
                                        getHourMinAmPm(
                                            slot.time,
                                            slot.title
                                        )
                                    )
                                )
                                onNav(Graph.Scheduler.route)
                            }
                        )
                    }
                }
            }
            if (defaultScheduleVisibility) {
                item {
                    AnimatedVisibility(visible = true) {
                        AppButtons.AppOutlinedButton(onClick = {
                            hSEvent(
                                HomeEvent.SetDefaultSchedule(
                                    context
                                )
                            )
                        }) {
                            AppTexts.TitleLarge(text = stringResource(R.string.default_schedule))
                        }
                    }
                }
            }
            if (listMorning.isNotEmpty()) {
                item {
                    AnimatedVisibility(visible = listMorning.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.morning_events),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                items(listMorning) { data ->
                    SwipeDemoToday(data = data, onSwipeRight = {
                        evenType = Event.Morning
                        deleteDialog = true
                        deletedItem = data
                    }, onSwipeLeft = {
                        evenType = Event.Morning
                        skipDialog = true
                        skipItem = data
                    }, onDone = {
                        onNav(goToTool(data.info.tag))
                    }, onReschedule = {
                        onNav(Graph.Scheduler.route)
                        hSEvent(HomeEvent.EditAlarm(data))
                    })
                }
            }
            if (listAfternoon.isNotEmpty()) {
                item {
                    AnimatedVisibility(visible = listAfternoon.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.afternoon_events),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                items(listAfternoon) { data ->
                    SwipeDemoToday(data = data, onSwipeRight = {
                        evenType = Event.Afternoon
                        deleteDialog = true
                        deletedItem = data
                    }, onSwipeLeft = {
                        evenType = Event.Afternoon
                        skipDialog = true
                        skipItem = data
                    }, onDone = {
                        onNav(goToTool(data.info.tag))
                    }, onReschedule = {
                        onNav(Graph.Scheduler.route)
                        hSEvent(HomeEvent.EditAlarm(data))
                    })
                }
            }
            if (listEvening.isNotEmpty()) {
                item {
                    AnimatedVisibility(visible = listEvening.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.evening_events),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                items(listEvening) { data ->
                    SwipeDemoToday(data = data, onSwipeRight = {
                        evenType = Event.Evening
                        deleteDialog = true
                        deletedItem = data
                    }, onSwipeLeft = {
                        evenType = Event.Evening
                        skipDialog = true
                        skipItem = data
                    }, onDone = {
                        onNav(goToTool(data.info.tag))
                    }, onReschedule = {
                        onNav(Graph.Scheduler.route)
                        hSEvent(HomeEvent.EditAlarm(data))
                    })
                }
            }
            if (listNextDay.isNotEmpty()) {
                item {
                    AnimatedVisibility(visible = listNextDay.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.tomorrow_events),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                items(listNextDay) { data ->
                    SwipeDemoToday(data = data, skipEnable = false,
                        onSwipeRight = {
                            evenType = Event.NextDay
                            deleteDialog = true
                            deletedItem = data
                        }, onSwipeLeft = {},
                        onDone = {
                            onNav(goToTool(data.info.tag))
                        }, onReschedule = {
                            onNav(Graph.Scheduler.route)
                            hSEvent(HomeEvent.EditAlarm(data))
                        })
                }
            }
        }
    }
}


@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class)
fun TodayItem(
    modifier: Modifier = Modifier,
    image: String = "",
    title: String = "Fasting",
    description: String = "Fasting to cleanse your body",
    progress: String = "44%",
    time: String = "9:00am",
    onDone: () -> Unit = {},
    onReschedule: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (!isPressed) MaterialTheme.colorScheme.primary else Color.Green
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        interactionSource = interactionSource,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 6.dp,
            focusedElevation = 4.dp,
            draggedElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.minSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
                modifier = modifier.fillMaxWidth()
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.weight(.5f),
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier
                                .weight(.2f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = .3f)
                                ),
                            text = progress,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                    OutlinedButton(
                        onClick = onDone,
                        border = BorderStroke(
                            width = 2.dp, color = color
                        ),
                        interactionSource = interactionSource,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Done",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(.5f),
                    text = time,
                    style = MaterialTheme.typography.titleMedium
                )
                TextButton(modifier = Modifier.weight(.5f), onClick = onReschedule) {
                    Text(
                        text = "Reschedule",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
fun SwipeDemoToday(
    onSwipeRight: () -> Unit,
    onSwipeLeft: () -> Unit,
    skipEnable: Boolean = true,
    progress: String = "44%",
    data: AlarmEntity,
    onDone: () -> Unit = {},
    onReschedule: () -> Unit = {}
) {

    val archive = SwipeAction(
        icon = rememberVectorPainter(Icons.TwoTone.Delete),
        background = Color.Red,
        onSwipe = onSwipeRight
    )
    val skip = SwipeAction(
        icon = rememberVectorPainter(Icons.TwoTone.SkipNext),
        background = Color.Yellow,
        onSwipe = onSwipeLeft,
    )
    SwipeableActionsBox(
        startActions = listOf(archive),
        endActions = if (skipEnable) listOf(skip) else emptyList(),
        swipeThreshold = 20.dp,
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.background,
    ) {
        val time = AMPMHoursMin(
            hours = if (data.time.hours > 12) {
                data.time.hours - 12
            } else data.time.hours,
            minutes = data.time.minutes,
            dayTime = if (data.time.hours >= 12) AMPMHoursMin.DayTime.PM else AMPMHoursMin.DayTime.AM
        )
        TodayCard(
            image = data.info.url,
            time = "${if (time.hours < 10) "0" else ""}${time.hours}:${if (time.minutes < 10) "0" else ""}${time.minutes} ${time.dayTime.name}",
            title = data.info.tag,
            description = data.info.description,
            progress = progress,
            onDone = onDone,
            onReschedule = onReschedule
        )
    }

}

@Composable
fun TodayCard(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    description: String,
    progress: String,
    time: String,
    onDone: () -> Unit,
    onReschedule: () -> Unit
) {
    TodayItem(
        image = image,
        title = title,
        time = time,
        description = description,
        progress = progress,
        onReschedule = onReschedule,
        onDone = onDone,
        modifier = modifier
    )
}

@Composable
fun AlertDialogPopUp(
    content: String,
    actionButton: String,
    onDismiss: () -> Unit,
    onDone: () -> Unit
) {
    AppDialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        AppCard(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    AppTexts.DisplaySmall(text = stringResource(id = R.string.alert))
                }
                AppTexts.BodyLarge(text = content)
                Row {
                    AppButtons.AppOutlinedButton(
                        onClick = onDismiss,
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        AppTexts.BodyLarge(text = stringResource(id = R.string.cancel))
                    }
                    AppButtons.AppStandardButton(
                        onClick = onDone,
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        AppTexts.BodyLarge(text = actionButton)
                    }
                }
            }
        }
    }
}


