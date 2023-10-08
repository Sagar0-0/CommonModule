package fit.asta.health.navigation.today.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.SkipNext
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.Constants.getHourMinAmPm
import fit.asta.health.common.utils.Constants.goToTool
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.WeatherCardImage
import fit.asta.health.designsystem.molecular.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.ui.components.WeatherCard
import fit.asta.health.main.Graph
import fit.asta.health.main.view.ALL_ALARMS_ROUTE
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TodayContent(
    state: UiState<TodayData>,
    userName: String,
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
            content = stringResource(R.string.are_you_sure_you_want_to_delete_this_alarm),
            actionButton = stringResource(id = R.string.delete),
            onDismiss = { deleteDialog = false },
            onDone = {
                deletedItem?.let { hSEvent(HomeEvent.DeleteAlarm(it, context)) }
                deleteDialog = false
            })
    }
    if (skipDialog) {
        AlertDialogPopUp(
            content = stringResource(R.string.are_you_sure_you_want_to_skip_this_alarm),
            actionButton = stringResource(id = R.string.skip),
            onDismiss = { skipDialog = false },
            onDone = {
                skipItem?.let { hSEvent(HomeEvent.SkipAlarm(it, context)) }
                skipDialog = false
            })
    }
    AppScaffold(
        snackBarHostState = snackBarHostState,
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(color = AppTheme.colors.background),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        ) {
            item {
                NameAndMoodHomeScreenHeader(
                    userName = userName,
                    onAlarm = { onNav(ALL_ALARMS_ROUTE) })
            }
            when (state) {
                is UiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            AppDotTypingAnimation()
                        }
                    }
                }

                is UiState.ErrorMessage -> {
                    item {
                        AppSurface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            AppTonalButton(
                                textToShow = stringResource(R.string.retry),
                                onClick = { hSEvent(HomeEvent.Retry) }
                            )
                        }
                    }
                }

                is UiState.Success -> {
                    item {
                        WeatherCardImage(
                            temperature = state.data.temperature,
                            location = state.data.location,
                            date = state.data.date
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(items = state.data.slots) { slot ->
                                WeatherCard(weatherData = slot, onSchedule = {
                                    hSEvent(
                                        HomeEvent.NavSchedule(
                                            getHourMinAmPm(
                                                slot.time, slot.title
                                            )
                                        )
                                    )
                                    onNav(Graph.Scheduler.route)
                                })
                            }
                        }
                    }

                }

                else -> {}
            }

            if (defaultScheduleVisibility) {
                item {
                    AnimatedVisibility(visible = true) {
                        AppOutlinedButton(textToShow = stringResource(R.string.default_schedule),
                            onClick = { hSEvent(HomeEvent.SetDefaultSchedule(context)) })
                    }
                }
            }
            if (listMorning.isNotEmpty()) {
                item {
                    AnimatedVisibility(visible = listMorning.isNotEmpty()) {
                        TitleTexts.Level2(text = stringResource(R.string.morning_events))
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
                        TitleTexts.Level2(text = stringResource(R.string.afternoon_events))
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
                        TitleTexts.Level2(text = stringResource(R.string.evening_events))
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
                        TitleTexts.Level2(text = stringResource(R.string.tomorrow_events))
                    }
                }
                items(listNextDay) { data ->
                    SwipeDemoToday(data = data, skipEnable = false, onSwipeRight = {
                        evenType = Event.NextDay
                        deleteDialog = true
                        deletedItem = data
                    }, onSwipeLeft = {}, onDone = {
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
fun TodayItem(
    modifier: Modifier = Modifier,
    image: String = "",
    title: String = "Fasting",
    description: String = "Fasting to cleanse your body",
    progress: String = "44%",
    time: String = "9:00am",
    onDone: () -> Unit = {},
    onReschedule: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (!isPressed) AppTheme.colors.primary else Color.Green
    AppCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface)
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = AppTheme.spacing.level2),
                modifier = modifier.fillMaxWidth()
            ) {
                AppNetworkImage(
                    model = ImageRequest.Builder(LocalContext.current).data(getImgUrl(url = image))
                        .crossfade(true).build(),
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(AppTheme.shape.level3)
                        .height(120.dp)
                        .width(80.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        TitleTexts.Level2(
                            modifier = Modifier.weight(.5f),
                            text = title,
                            color = AppTheme.colors.onBackground,
                        )
                        TitleTexts.Level2(
                            modifier = Modifier
                                .weight(.2f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(
                                    AppTheme.colors.primary.copy(alpha = .3f)
                                ),
                            text = progress,
                            color = AppTheme.colors.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                    BodyTexts.Level2(text = description)
                    AppOutlinedButton(
                        onClick = onDone,
                        border = BorderStroke(width = 2.dp, color = color),
                        interactionSource = interactionSource,
                        shape = AppTheme.shape.level3,
                        leadingIcon = Icons.Default.Check,
                        textToShow = "Done",
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                TitleTexts.Level2(
                    modifier = Modifier.weight(.5f),
                    text = time,
                )
                AppTextButton(
                    modifier = Modifier.weight(.5f),
                    textToShow = stringResource(id = R.string.reschedule),
                    onClick = onReschedule
                )
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
    onReschedule: () -> Unit = {},
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
        backgroundUntilSwipeThreshold = AppTheme.colors.background,
    ) {
        val time = AMPMHoursMin(
            hours = if (data.time.hours > 12) {
                data.time.hours - 12
            } else if (data.time.hours == 0) 12
            else data.time.hours,
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
    onReschedule: () -> Unit,
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
    onDone: () -> Unit,
) {
    AppDialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        AppCard(
            modifier = Modifier.clip(AppTheme.shape.level3),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    LargeTexts.Level3(text = stringResource(id = R.string.alert))
                }
                BodyTexts.Level1(text = content, maxLines = 3)
                Row {
                    AppOutlinedButton(
                        onClick = onDismiss,
                        shape = AppTheme.shape.level4,
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 8.dp),
                        textToShow = stringResource(id = R.string.cancel)
                    )
                    AppTonalButton(
                        onClick = onDone,
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 8.dp),
                        textToShow = actionButton
                    )
                }
            }
        }
    }
}


