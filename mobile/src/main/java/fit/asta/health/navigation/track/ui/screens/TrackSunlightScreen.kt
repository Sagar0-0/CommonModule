package fit.asta.health.navigation.track.ui.screens

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import fit.asta.chart.circular.CircularChart
import fit.asta.chart.circular.center.CircularImageCenter
import fit.asta.chart.circular.charts.CircularDonutChartRow
import fit.asta.chart.circular.data.CircularTargetDataBuilder
import fit.asta.chart.circular.foreground.CircularDonutTargetForeground
import fit.asta.chart.linear.LinearChart
import fit.asta.chart.linear.data.LinearEmojiData
import fit.asta.chart.linear.data.LinearStringData
import fit.asta.chart.util.ChartPoint
import fit.asta.health.R
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.designsystem.organism.common.AppDatePicker
import fit.asta.health.navigation.track.ui.components.TrackTopTabBar
import fit.asta.health.designsystem.organism.common.AppTitleElevatedCard
import fit.asta.health.navigation.track.ui.components.TrackingDetailsCard
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import java.text.DecimalFormat
import java.time.LocalDate

@Composable
fun TrackSunlightScreenControl(
    sunlightTrackData: UiState<SunlightResponse>,
    calendarData: LocalDate,
    setUiEvent: (TrackUiEvent) -> Unit
) {
    // This is the Item which is selected in the Top Tab Bar Layout
    val selectedItem = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
    }

    // Drag Flags to keep a check of how the user has dragged
    val isLeftDrag = remember { mutableStateOf(false) }
    val isRightDrag = remember { mutableStateOf(false) }

    val tabList = listOf("DAY", "WEEK", "MONTH", "YEAR")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(
                    ColorUtils.blendARGB(
                        AppTheme.colors.surface.toArgb(),
                        AppTheme.colors.onSurface.toArgb(),
                        0.08f
                    )
                )
            )
            .pointerInput(Unit) {

                // Detects all the drag gestures and processes the data accordingly
                detectDragGestures(

                    // This is called when the drag is started
                    onDrag = { change, dragAmount ->
                        change.consume()

                        when {

                            // Right Drag
                            dragAmount.x > 0 -> {
                                isRightDrag.value = true
                                isLeftDrag.value = false
                            }

                            // Left Drag
                            dragAmount.x < 0 -> {
                                isLeftDrag.value = true
                                isRightDrag.value = false
                            }
                        }
                    },

                    // This is called when the drag is completed
                    onDragEnd = {

                        // If the drag is a right drag
                        if (isRightDrag.value && selectedItem.intValue > 0 && sunlightTrackData !is UiState.Loading) {

                            // Checking which tab option is selected by the User and showing the UI Accordingly
                            selectedItem.intValue = selectedItem.intValue - 1
                            setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))

                            // Resetting the drag flags
                            isRightDrag.value = false
                        }

                        // If the drag is a left drag
                        if (isLeftDrag.value && selectedItem.intValue < tabList.size - 1 && sunlightTrackData !is UiState.Loading) {

                            // Checking which tab option is selected by the User and showing the UI Accordingly
                            selectedItem.intValue = selectedItem.intValue + 1
                            setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))

                            // Resetting the drag flags
                            isLeftDrag.value = false
                        }
                    },

                    // This function is called when the drag is cancelled
                    onDragCancel = {

                        // Resetting all the drag flags
                        isLeftDrag.value = false
                        isRightDrag.value = false
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // This Function makes the Tab Layout UI
        TrackTopTabBar(
            tabList = tabList,
            selectedItem = selectedItem.intValue
        ) {

            if (selectedItem.intValue != it && sunlightTrackData !is UiState.Loading) {

                // Checking which tab option is selected by the User and showing the UI Accordingly
                selectedItem.intValue = it
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }
        }

        // Date Picker
        AppDatePicker(
            localDate = calendarData,
            onPreviousButtonClick = {
                setUiEvent(TrackUiEvent.ClickedPreviousDateButton)
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            },
            onNextButtonClick = {
                setUiEvent(TrackUiEvent.ClickedNextDateButton)
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            },
            onDateChanged = {
                setUiEvent(TrackUiEvent.SetNewDate(it))
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }
        )

        when (sunlightTrackData) {

            is UiState.Idle -> {
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }

            is UiState.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            is UiState.Success -> {
                Spacer(modifier = Modifier.height(8.dp))
                TrackSuccessScreen(sunlightTrackData.data.sunlightData)
            }

            is UiState.ErrorMessage -> {
                AppErrorScreen(
                    isInternetError = false,
                    desc = sunlightTrackData.resId.toStringFromResId()
                ) {
                    setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
                }
            }

            else -> {}
        }
    }
}

@Composable
fun TrackSuccessScreen(sunlightData: SunlightResponse.SunlightData) {

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(
                    ColorUtils.blendARGB(
                        AppTheme.colors.surface.toArgb(),
                        AppTheme.colors.onSurface.toArgb(),
                        0.08f
                    )
                )
            ),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.medium)
    ) {

        // Daily Progress
        sunlightData.progress?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.DAILY_PROGRESS) {
                    CircularDonutChartRow.TargetDonutChart(
                        circularData = CircularTargetDataBuilder(
                            target = it.target,
                            achieved = it.achieved,
                            siUnit = TrackStringConstants.TIME_SI_UNIT,
                            cgsUnit = TrackStringConstants.TIME_CGS_UNIT,
                            conversionRate = { it / 60f }
                        )
                    )
                }
            }
        }


        // Weekly Progress
        sunlightData.weekly?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.WEEKLY_PROGRESS) {
                    Row {
                        it.forEachIndexed { index, weekly ->
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                CircularChart.DonutChartImage(
                                    modifier = Modifier
                                        .size(55.dp),
                                    circularData = CircularTargetDataBuilder(
                                        target = weekly.tgt,
                                        achieved = weekly.ach,
                                        siUnit = "",
                                        cgsUnit = "",
                                        conversionRate = { it }
                                    ),
                                    circularCenter = CircularImageCenter(
                                        image = Icons.Default.Check,
                                        contentDescription = null
                                    ),
                                    circularForeground = CircularDonutTargetForeground(strokeWidth = 10f)
                                )

                                Text(
                                    text = TrackStringConstants.WEEKDAYS_STRINGS[index],

                                    // Text Features
                                    textAlign = TextAlign.Start,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W700,
                                    color = AppTheme.colors.onSurface,
                                )
                            }
                        }
                    }
                }
            }
        }


        // Vitamin D Details Card
        sunlightData.weather?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.VITAMIN_D_DETAILS) {
                    TrackingDetailsCard(
                        imageList = listOf(
                            R.drawable.image_sun,
                            R.drawable.track_image_duration,
                            R.drawable.track_image_exposure
                        ),
                        headerTextList = listOf(
                            TrackStringConstants.AVG_VITAMIN_D,
                            TrackStringConstants.AVG_DURATION,
                            TrackStringConstants.AVG_EXPOSURE
                        ),
                        valueList = listOf(
                            "${DecimalFormat("#.##").format(it.vitD.avg)} ${it.vitD.unit}",
                            "${DecimalFormat("#.##").format(it.duration.dur)} ${it.duration.unit}",
                            "${DecimalFormat("#.##").format(it.exposure.avg)} ${it.exposure.unit}"
                        )
                    )
                }
            }
        }


        // Vitamin D Graph
        sunlightData.vitaminGraph?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.VITAMIN_D) {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Sunlight Duration Graph
        sunlightData.durationGraph?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.SUNLIGHT_DURATION) {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Skin Exposure Graph
        sunlightData.exposureGraph?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.SKIN_EXPOSURE) {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // TODO :- Your Mood Card Composable needs to be implemented


        // Mood Graph Line Chart
        sunlightData.moodGraph?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.MOOD_GRAPH) {
                    LinearChart.EmojiLineChart(
                        linearData = LinearEmojiData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis),
                            yMarkerList = ChartPoint.pointDataBuilder(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.emoji_furious
                                ) as BitmapDrawable,
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.emoji_angry
                                ) as BitmapDrawable,
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.emoji_sad
                                ) as BitmapDrawable,
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.emoji_depressed
                                ) as BitmapDrawable,
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.emoji_confused
                                ) as BitmapDrawable,
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.emoji_calm
                                ) as BitmapDrawable,
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.emoji_happy
                                ) as BitmapDrawable
                            ).toMutableList()
                        )
                    )
                }
            }
        }
    }
}
