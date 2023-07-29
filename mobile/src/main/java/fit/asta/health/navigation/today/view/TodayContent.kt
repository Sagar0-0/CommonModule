package fit.asta.health.navigation.today.view

import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.main.Graph
import fit.asta.health.navigation.home.view.component.NameAndMoodHomeScreenHeader
import fit.asta.health.scheduler.compose.screen.homescreen.Event
import fit.asta.health.scheduler.compose.screen.homescreen.HomeEvent
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TodayContent(
    listMorning: SnapshotStateList<AlarmEntity>,
    listAfternoon: SnapshotStateList<AlarmEntity>,
    listEvening: SnapshotStateList<AlarmEntity>,
    hSEvent: (HomeEvent) -> Unit,
    onNav: (Graph) -> Unit
) {


    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    AppScaffold(
        snackBarHostState = snackBarHostState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    hSEvent(HomeEvent.SetAlarm)
                    onNav(Graph.Scheduler)
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
                AnimatedVisibility(visible = listMorning.isNotEmpty()) {
                    Text(text = "Morning Events", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(listMorning) { data ->
                SwipeDemoToday(data = data, onSwipeRight = {
                    swipeRight(Event.Morning, data, context, coroutineScope, snackBarHostState,hSEvent)
                },onSwipeLeft = {
                    swipeLeft(Event.Morning, data, context, coroutineScope, snackBarHostState,hSEvent)
                }, onDone = {
                   onNav(goToTool(data.info.tag))
                }, onReschedule = {
                    onNav(Graph.Scheduler)
                    hSEvent(HomeEvent.EditAlarm(data))
                }
                )
            }
            item {
                AnimatedVisibility(visible = listAfternoon.isNotEmpty()) {
                    Text(text = "Afternoon Events", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(listAfternoon) { data ->
                SwipeDemoToday(data = data, onSwipeRight = {
                    swipeRight(Event.Afternoon, data, context, coroutineScope, snackBarHostState,hSEvent)
                },onSwipeLeft = {
                    swipeLeft(Event.Afternoon, data, context, coroutineScope, snackBarHostState,hSEvent)
                }, onDone = {
                    onNav(goToTool(data.info.tag))
                }, onReschedule = {
                    onNav(Graph.Scheduler)
                    hSEvent(HomeEvent.EditAlarm(data))
                }
                )
            }
            item {
                AnimatedVisibility(visible = listEvening.isNotEmpty()) {
                    Text(text = "Evening Events", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(listEvening) { data ->
                SwipeDemoToday(data = data, onSwipeRight = {
                    swipeRight(Event.Evening, data, context, coroutineScope, snackBarHostState,hSEvent)
                },onSwipeLeft = {
                    swipeLeft(Event.Evening, data, context, coroutineScope, snackBarHostState,hSEvent)
                },onDone = {
                    onNav(goToTool(data.info.tag))
                }, onReschedule = {
                    onNav(Graph.Scheduler)
                    hSEvent(HomeEvent.EditAlarm(data))
                }
                )
            }
        }
    }


}


private fun swipeRight(
    event: Event,
    data: AlarmEntity,
    context: Context,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    hSEvent: (HomeEvent) -> Unit,
) {
    hSEvent(HomeEvent.DeleteAlarm(data, context, event))
    coroutineScope.launch {
        val snackBarResult = snackBarHostState.showSnackbar(
            message = "Deleted ${data.info.name}",
            actionLabel = "Undo",
            duration = SnackbarDuration.Long
        )
        when (snackBarResult) {
            SnackbarResult.ActionPerformed -> {
                hSEvent(HomeEvent.UndoAlarm(data, context, event))
            }
            else -> {}
        }
    }
}


private fun swipeLeft(
    event: Event,
    data: AlarmEntity,
    context: Context,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    hSEvent: (HomeEvent) -> Unit
) {
    hSEvent(HomeEvent.SkipAlarm(data, context, event))
    coroutineScope.launch {
        val snackBarResult = snackBarHostState.showSnackbar(
            message = "Skip ${data.info.name}",
            actionLabel = "Undo",
            duration = SnackbarDuration.Long
        )
        when (snackBarResult) {
            SnackbarResult.ActionPerformed -> {
                hSEvent(HomeEvent.UndoSkipAlarm(data, context,event))
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
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(getImageUrl(url = image))
                        .crossfade(true)
                        .build(),
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
                            width = 2.dp,
                            color = color
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
        icon =rememberVectorPainter(Icons.TwoTone.SkipNext),
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

fun goToTool(tag: String): Graph {
    return when (tag) {
        "Breathing" -> {
            Graph.BreathingTool
        }
//        "Diet" -> {}
//        "Face Wash" -> {}
//        "Intermittent" -> {}
//        "Medicine" -> {}
        "Meditation" -> {
            Graph.MeditationTool
        }
//        "Power Nap" -> {}
        "Sleep" -> {
            Graph.SleepTool
        }
//        "Sleep Therapy" -> {}
        "Stretches" -> {
            Graph.Yoga
        }

        "SunLight" -> {
            Graph.SunlightTool
        }

        "Walking" -> {
            Graph.WalkingTool
        }

        "Water" -> {
            Graph.WaterTool
        }

        "Workout" -> {
            Graph.Workout
        }

        "Yoga" -> {
            Graph.Yoga
        }

        else -> {
            Graph.Home
        }
    }
}
