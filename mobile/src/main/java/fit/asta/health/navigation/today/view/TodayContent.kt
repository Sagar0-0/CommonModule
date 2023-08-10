package fit.asta.health.navigation.today.view

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.functional.WeatherCardImage
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.main.Graph
import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.navigation.today.view.utils.HourMinAmPm
import fit.asta.health.navigation.today.view.utils.Utils
import fit.asta.health.scheduler.compose.naman.WeatherCard
import fit.asta.health.scheduler.compose.naman.WeatherData
import fit.asta.health.scheduler.compose.screen.homescreen.Event
import fit.asta.health.scheduler.compose.screen.homescreen.HomeEvent
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TodayContent(
    uiState: TodayData,
    listMorning: SnapshotStateList<AlarmEntity>,
    listAfternoon: SnapshotStateList<AlarmEntity>,
    listEvening: SnapshotStateList<AlarmEntity>,
    listNextDay: SnapshotStateList<AlarmEntity>,
    hSEvent: (HomeEvent) -> Unit,
    onNav: (String) -> Unit,
    onSchedule: (HourMinAmPm?) -> Unit
) {


    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var showSnackBar by rememberSaveable { mutableStateOf(false) }
    var deletedItem by remember { mutableStateOf<AlarmEntity?>(null) }
    var skipItem by remember { mutableStateOf<AlarmEntity?>(null) }

    if (showSnackBar) {
        LaunchedEffect(Unit) {
            delay(9000L) // 9-second countdown timer
            if (showSnackBar) {
                // SnackBar is still visible after 5 seconds
                deletedItem?.let { hSEvent(HomeEvent.DeleteAlarm(it)) }
                skipItem?.let { hSEvent(HomeEvent.SkipAlarm(it)) }
            }
            skipItem = null
            deletedItem = null
            showSnackBar = false
        }
    }
    AppScaffold(
        snackBarHostState = snackBarHostState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    hSEvent(HomeEvent.SetAlarm)
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
            item { NameAndMoodHomeScreenHeader() }
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
                        val dayAndTime = Utils.getDayAndTime(slot.time)
                        WeatherCard(
                            weatherData = WeatherData(
                                time = dayAndTime.time,
                                temperature = "${slot.temp}°C",
                                uvDetails = "${slot.uv} Uv",
                                timeSlot = dayAndTime.timeOfDay,
                                title = dayAndTime.day
                            ),
                            onSchedule = {
                                onSchedule(Utils.getHourMinAmPm(dayAndTime.time, dayAndTime.day))
                                onNav(Graph.Scheduler.route)
                            }
                        )
                    }
                }
            }
            item {
                AnimatedVisibility(visible = listMorning.isNotEmpty()) {
                    Text(text = "Morning Events", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(listMorning) { data ->
                SwipeDemoToday(data = data, onSwipeRight = {
                    showSnackBar = true
                    deletedItem = data
                    swipeRight(Event.Morning,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onSwipeLeft = {
                    showSnackBar = true
                    skipItem = data
                    swipeLeft(Event.Morning,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onDone = {
                    onNav(goToTool(data.info.tag))
                }, onReschedule = {
                    onNav(Graph.Scheduler.route)
                    hSEvent(HomeEvent.EditAlarm(data))
                })
            }
            item {
                AnimatedVisibility(visible = listAfternoon.isNotEmpty()) {
                    Text(text = "Afternoon Events", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(listAfternoon) { data ->
                SwipeDemoToday(data = data, onSwipeRight = {
                    showSnackBar = true
                    deletedItem = data
                    swipeRight(Event.Afternoon,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onSwipeLeft = {
                    showSnackBar = true
                    skipItem = data
                    swipeLeft(Event.Afternoon,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onDone = {
                    onNav(goToTool(data.info.tag))
                }, onReschedule = {
                    onNav(Graph.Scheduler.route)
                    hSEvent(HomeEvent.EditAlarm(data))
                })
            }
            item {
                AnimatedVisibility(visible = listEvening.isNotEmpty()) {
                    Text(text = "Evening Events", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(listEvening) { data ->
                SwipeDemoToday(data = data, onSwipeRight = {
                    showSnackBar = true
                    deletedItem = data
                    swipeRight(Event.Evening,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onSwipeLeft = {
                    showSnackBar = true
                    skipItem = data
                    swipeLeft(Event.Evening,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onDone = {
                    onNav(goToTool(data.info.tag))
                }, onReschedule = {
                    onNav(Graph.Scheduler.route)
                    hSEvent(HomeEvent.EditAlarm(data))
                })
            }
            item {
                AnimatedVisibility(visible = listNextDay.isNotEmpty()) {
                    Text(text = "Tomorrow Events", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(listNextDay) { data ->
                SwipeDemoToday(data = data, onSwipeRight = {
                    showSnackBar = true
                    deletedItem = data
                    swipeRight(Event.NextDay,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onSwipeLeft = {
                    showSnackBar = true
                    skipItem = data
                    swipeLeft(Event.NextDay,
                        data,
                        coroutineScope,
                        snackBarHostState,
                        hSEvent,
                        onUndo = { showSnackBar = false })
                }, onDone = {
                    onNav(goToTool(data.info.tag))
                }, onReschedule = {
                    onNav(Graph.Scheduler.route)
                    hSEvent(HomeEvent.EditAlarm(data))
                })
            }
        }
    }


}


private fun swipeRight(
    event: Event,
    data: AlarmEntity,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    hSEvent: (HomeEvent) -> Unit,
    onUndo: () -> Unit,
) {
    hSEvent(HomeEvent.RemoveAlarm(data, event))
    coroutineScope.launch {
        val snackBarResult = snackBarHostState.showSnackbar(
            message = "Deleted ${data.info.name}",
            actionLabel = "Undo",
            duration = SnackbarDuration.Long
        )
        when (snackBarResult) {
            SnackbarResult.ActionPerformed -> {
                onUndo()
                hSEvent(HomeEvent.UndoAlarm(data, event))
            }

            else -> {}
        }
    }
}


private fun swipeLeft(
    event: Event,
    data: AlarmEntity,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    hSEvent: (HomeEvent) -> Unit,
    onUndo: () -> Unit
) {
    hSEvent(HomeEvent.RemoveAlarm(data, event))
    coroutineScope.launch {
        val snackBarResult = snackBarHostState.showSnackbar(
            message = "Skip ${data.info.name}",
            actionLabel = "Undo",
            duration = SnackbarDuration.Long
        )
        when (snackBarResult) {
            SnackbarResult.ActionPerformed -> {
                onUndo()
                hSEvent(HomeEvent.UndoAlarm(data, event))
            }

            else -> {}
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
            verticalArrangement = Arrangement.spacedBy(spacing.small),
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
                        horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
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
                horizontalArrangement = Arrangement.spacedBy(space = spacing.small),
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
    onSwipeRight: () -> Unit = {},
    onSwipeLeft: () -> Unit = {},
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
        endActions = listOf(skip),
        swipeThreshold = 20.dp,
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.background
    ) {
        TodayCard(
            image = data.info.url,
            time = "${data.time.hours}:${data.time.minutes}",
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

fun goToTool(tag: String): String {
    return when (tag) {
        "Breathing" -> {
            Graph.BreathingTool.route
        }
//        "Diet" -> {}
//        "Face Wash" -> {}
//        "Intermittent" -> {}
//        "Medicine" -> {}
        "Meditation" -> {
            Graph.MeditationTool.route
        }
//        "Power Nap" -> {}Graph.ExerciseTool.route + "?activity=dance"
        "Sleep" -> {
            Graph.SleepTool.route
        }
//        "Sleep Therapy" -> {}
        "Stretches" -> {
            Graph.Yoga.route
        }

        "SunLight" -> {
            Graph.SunlightTool.route
        }

        "Walking" -> {
            Graph.WalkingTool.route
        }

        "Water" -> {
            Graph.WaterTool.route
        }

        "Workout" -> {
            Graph.Workout.route
        }

        "Yoga" -> {
            Graph.Yoga.route
        }

        else -> {
            Graph.Home.route
        }
    }
}
