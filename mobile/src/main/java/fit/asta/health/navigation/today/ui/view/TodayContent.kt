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
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.utils.AMPMHoursMin
import fit.asta.health.common.utils.Constants.getHourMinAmPm
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.ui.components.SunSlotsProgressCard
import fit.asta.health.feature.scheduler.ui.components.WeatherCardHome
import fit.asta.health.main.Graph
import fit.asta.health.ui.common.AppDialogPopUp
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TodayContent(
    state: UiState<TodayData>,
    userName: String,
    defaultScheduleVisibility: Boolean,
    calendarUiModel: CalendarUiModel,
    listMorning: SnapshotStateList<AlarmEntity>,
    listAfternoon: SnapshotStateList<AlarmEntity>,
    listEvening: SnapshotStateList<AlarmEntity>,
    listNextDay: SnapshotStateList<AlarmEntity>,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
    hSEvent: (HomeEvent) -> Unit,
    onNav: (String) -> Unit,
) {
    var deleteDialog by rememberSaveable { mutableStateOf(false) }
    var skipDialog by rememberSaveable { mutableStateOf(false) }
    var deletedItem by remember { mutableStateOf<AlarmEntity?>(null) }
    var skipItem by remember { mutableStateOf<AlarmEntity?>(null) }
    var evenType by remember { mutableStateOf(Event.Morning) }
    val context = LocalContext.current


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
    AppScreen {
        LazyColumn(
            Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
        ) {

            // Heading Name, show Alarms and What's your mood composable

//            item {
//                NameAndMoodHomeScreenHeader(
//                    userName = userName,
//                    onAlarm = { onNav(ALL_ALARMS_ROUTE) }
//                )
//            }
            item {
                WeekScreen(
                    modifier = Modifier.fillMaxWidth(),
                    data = calendarUiModel,
                    onDateClickListener = onDateClickListener
                )
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
                                textToShow = stringResource(R.string.retry)
                            ) { hSEvent(HomeEvent.Retry) }
                        }
                    }
                }

                is UiState.Success -> {
                    item {
                        WeatherCardHome(
                            modifier = Modifier.fillMaxWidth(),
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
            if (defaultScheduleVisibility) {
                item {
                    AnimatedVisibility(visible = true) {
                        AppOutlinedButton(
                            textToShow = stringResource(R.string.default_schedule)
                        ) { hSEvent(HomeEvent.SetDefaultSchedule(context)) }
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
                    TodayItem(data)
//                    SwipeDemoToday(data = data, onSwipeRight = {
//                        evenType = Event.Morning
//                        deleteDialog = true
//                        deletedItem = data
//                    }, onSwipeLeft = {
//                        evenType = Event.Morning
//                        skipDialog = true
//                        skipItem = data
//                    }, onDone = {
//                        onNav(goToTool(data.info.tag))
//                    }, onReschedule = {
//                        onNav(Graph.Scheduler.route)
//                        hSEvent(HomeEvent.EditAlarm(data))
//                    })
                }
            }
            if (listAfternoon.isNotEmpty()) {
                item {
                    AnimatedVisibility(visible = listAfternoon.isNotEmpty()) {
                        TitleTexts.Level2(text = stringResource(R.string.afternoon_events))
                    }
                }
                items(listAfternoon) { data ->
                    TodayItem1(data)
                }
            }
            if (listEvening.isNotEmpty()) {
                item {
                    AnimatedVisibility(visible = listEvening.isNotEmpty()) {
                        TitleTexts.Level2(text = stringResource(R.string.evening_events))
                    }
                }
                items(listEvening) { data ->
                    TodayItem2(data)
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
                    model = ImageRequest.Builder(LocalContext.current).data(getImgUrl(url = image))
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