package fit.asta.health.navigation.today.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.Constants.getHourMinAmPm
import fit.asta.health.common.utils.Constants.goToTool
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.ui.components.SunSlotsProgressCard
import fit.asta.health.feature.scheduler.ui.components.WeatherCardHome
import fit.asta.health.main.Graph
import fit.asta.health.ui.common.AppDialogPopUp
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayContent(
    state: UiState<TodayData>,
    userName: String,
    calendarUiModel: CalendarUiModel,
    defaultScheduleVisibility: Boolean,
    listMorning: SnapshotStateList<AlarmEntity>,
    listAfternoon: SnapshotStateList<AlarmEntity>,
    listEvening: SnapshotStateList<AlarmEntity>,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
    hSEvent: (HomeEvent) -> Unit,
    onNav: (String) -> Unit,
) {
    var deleteDialog by rememberSaveable { mutableStateOf(false) }
    var skipDialog by rememberSaveable { mutableStateOf(false) }
    var deletedItem by remember { mutableStateOf<AlarmEntity?>(null) }
    val skipItem by remember { mutableStateOf<AlarmEntity?>(null) }
    val context = LocalContext.current
    val index = remember { mutableIntStateOf(1) }
    var eventType by remember { mutableStateOf(Event.Morning) }

    if (defaultScheduleVisibility){
        hSEvent(HomeEvent.SetDefaultSchedule(context))
    }

    if (deleteDialog) {
        AppDialogPopUp(
            headingText = stringResource(id = R.string.alert),
            bodyText = stringResource(R.string.are_you_sure_you_want_to_delete_this_alarm),
            primaryButtonText = stringResource(id = R.string.delete),
            secondaryButtonText = stringResource(id = R.string.cancel),
            onDismiss = { deleteDialog = false },
            onDone = {
                deletedItem?.let { hSEvent(HomeEvent.DeleteAlarm(it, context)) }
                deleteDialog = false
            }
        )
    }
    if (skipDialog) {
        AppDialogPopUp(
            headingText = stringResource(id = R.string.alert),
            bodyText = stringResource(R.string.are_you_sure_you_want_to_skip_this_alarm),
            primaryButtonText = stringResource(id = R.string.skip),
            secondaryButtonText = stringResource(id = R.string.cancel),
            onDismiss = { skipDialog = false },
            onDone = {
                skipItem?.let { hSEvent(HomeEvent.SkipAlarm(it, context)) }
                skipDialog = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState(
            initialPage = calendarUiModel.visibleDates.indexOfFirst {
                it.isToday
            }
        ) {
            calendarUiModel.visibleDates.size
        }

        WeekTabBar(
            data = calendarUiModel,
            selectedIndex = pagerState.currentPage,
            onDateClickListener = { date, newIndex ->
                coroutineScope.launch { pagerState.animateScrollToPage(newIndex) }
                index.intValue = newIndex
                onDateClickListener(date)
            }
        )
        AppHorizontalPager(
            pagerState = pagerState,
            enableAutoAnimation = false,
            pageSpacing = AppTheme.spacing.noSpacing,
            contentPadding = PaddingValues(AppTheme.spacing.noSpacing),
            userScrollEnabled = true
        ) {
            if (calendarUiModel.visibleDates[it].isToday) {
                TodayTabContent(
                    calendarUiModel = calendarUiModel,
                    state = state,
                    defaultScheduleVisibility = defaultScheduleVisibility,
                    listMorning = listMorning,
                    listAfternoon = listAfternoon,
                    listEvening = listEvening,
                    hSEvent = hSEvent,
                    onNav = onNav,
                    onDelete = { newEventType, newDeleteDialog, newDeletedItem ->
                        eventType = newEventType
                        deleteDialog = newDeleteDialog
                        deletedItem = newDeletedItem
                    }
                )
            } else {
                OtherDaysTabContent()
            }
        }

    }
}

@Composable
fun OtherDaysTabContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TitleTexts.Level1(
            text = "Coming soon..."
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayTabContent(
    calendarUiModel: CalendarUiModel,
    state: UiState<TodayData>,
    defaultScheduleVisibility: Boolean,
    listMorning: SnapshotStateList<AlarmEntity>,
    listAfternoon: SnapshotStateList<AlarmEntity>,
    listEvening: SnapshotStateList<AlarmEntity>,
    hSEvent: (HomeEvent) -> Unit,
    onNav: (String) -> Unit,
    onDelete: (eventType: Event, deleteDialog: Boolean, deletedItem: AlarmEntity?) -> Unit
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
    ) {
        item { Spacer(modifier = Modifier) }
        if (calendarUiModel.selectedDate.isToday) {
            when (state) {
                is UiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing,
                                    )
                                ),
                            contentAlignment = Alignment.Center
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
                                textToShow = stringResource(R.string.retry)
                            ) { hSEvent(HomeEvent.Retry) }
                        }
                    }
                }

                is UiState.Success -> {
                    item {
                        WeatherCardHome(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing,
                                    )
                                ),
                            temperature = state.data.temperature,
                        )
                    }
                    item { TitleTexts.Level2(text = "SunSlots") }
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                        ) {
                            items(items = state.data.slots) { slot ->
                                SunSlotsProgressCard(
                                    modifier = Modifier.animateItemPlacement(
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing,
                                        )
                                    ),
                                    title = slot.temperature,
                                    titleValue = slot.time,
                                    onSchedule = {
                                        hSEvent(
                                            HomeEvent.NavSchedule(
                                                getHourMinAmPm(
                                                    slot.time, slot.title
                                                )
                                            )
                                        )
                                        onNav(Graph.Scheduler.route)
                                    }
                                )
                            }
                        }
                    }
                }

                else -> {}
            }
        }
       /* if (defaultScheduleVisibility) {
            item {
                AppOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    textToShow = stringResource(R.string.default_schedule)
                ) { hSEvent(HomeEvent.SetDefaultSchedule(context)) }
            }
        }*/
        if (listMorning.isNotEmpty()) {
            item {
                AnimatedVisibility(
                    visible = listMorning.isNotEmpty(),
                    exit = scaleOut(
                        targetScale = 0.5f,
                        animationSpec = tween(durationMillis = 500, delayMillis = 100)
                    ),
                    enter = scaleIn(
                        initialScale = 0.5f,
                        animationSpec = tween(durationMillis = 500, delayMillis = 100)
                    )
                ) {
                    TitleTexts.Level2(text = stringResource(R.string.morning_events))
                }
            }

            items(items = listMorning, key = { it }) { data ->
                TodayItem(
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing,
                        )
                    ),
                    item = data,
                    onDelete = {
                        onDelete(
                            Event.Morning,
                            true,
                            data
                        )
                    },
                    onDone = {
                        val route = goToTool(data.info.tag)
                        if (route.isNotEmpty()) onNav(route)
                    },
                    onReschedule = {
                        onNav(Graph.Scheduler.route)
                        hSEvent(HomeEvent.EditAlarm(data))
                    }
                )
            }
        }
        if (listAfternoon.isNotEmpty()) {
            item {
                AnimatedVisibility(
                    visible = listAfternoon.isNotEmpty(),
                    exit = scaleOut(
                        targetScale = 0.5f,
                        animationSpec = tween(durationMillis = 500, delayMillis = 100)
                    ),
                    enter = scaleIn(
                        initialScale = 0.5f,
                        animationSpec = tween(durationMillis = 500, delayMillis = 100)
                    )
                ) {
                    TitleTexts.Level2(text = stringResource(R.string.afternoon_events))
                }
            }
            items(items = listAfternoon, key = { it }) { data ->
                TodayItem(
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing,
                        )
                    ),
                    item = data,
                    onDelete = {
                        onDelete(
                            Event.Afternoon,
                            true,
                            data
                        )
                    },
                    onDone = {
                        val route = goToTool(data.info.tag)
                        if (route.isNotEmpty()) onNav(route)
                    },
                    onReschedule = {
                        onNav(Graph.Scheduler.route)
                        hSEvent(HomeEvent.EditAlarm(data))
                    }
                )
            }
        }
        if (listEvening.isNotEmpty()) {
            item {
                AnimatedVisibility(
                    visible = listEvening.isNotEmpty(), exit = scaleOut(
                        targetScale = 0.5f,
                        animationSpec = tween(durationMillis = 500, delayMillis = 100)
                    ),
                    enter = scaleIn(
                        initialScale = 0.5f,
                        animationSpec = tween(durationMillis = 500, delayMillis = 100)
                    )
                ) {
                    TitleTexts.Level2(text = stringResource(R.string.evening_events))
                }
            }
            items(items = listEvening, key = { it }) { data ->
                TodayItem(
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing,
                        )
                    ),
                    item = data,
                    onDelete = {
                        onDelete(
                            Event.Evening,
                            true,
                            data
                        )
                    },
                    onDone = {
                        val route = goToTool(data.info.tag)
                        if (route.isNotEmpty()) onNav(route)
                    },
                    onReschedule = {
                        onNav(Graph.Scheduler.route)
                        hSEvent(HomeEvent.EditAlarm(data))
                    }
                )
            }
        }
        item { Spacer(modifier = Modifier) }
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
        modifier = modifier
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = AppTheme.spacing.level1),
                modifier = modifier.fillMaxWidth()
            ) {
                AppNetworkImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(getImageUrl(url = image))
                        .crossfade(true).build(),
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(AppTheme.shape.level2)
                        .height(120.dp)
                        .width(80.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
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
                        textToShow = "Done",
                        leadingIcon = Icons.Default.Check,
                        shape = AppTheme.shape.level2,
                        border = BorderStroke(width = 2.dp, color = color),
                        interactionSource = interactionSource,
                        onClick = onDone,
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
                    textToShow = stringResource(id = R.string.reschedule),
                    modifier = Modifier.weight(.5f),
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