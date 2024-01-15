package fit.asta.health.navigation.track.ui.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import fit.asta.chart.circular.CircularChart
import fit.asta.chart.circular.center.CircularImageCenter
import fit.asta.chart.circular.center.CircularRingTextCenter
import fit.asta.chart.circular.charts.CircularDonutChartRow
import fit.asta.chart.circular.charts.CircularRingChart
import fit.asta.chart.circular.data.CircularTargetDataBuilder
import fit.asta.chart.circular.foreground.CircularDonutTargetForeground
import fit.asta.chart.linear.LinearChart
import fit.asta.chart.linear.colorconvention.LinearGridColorConvention
import fit.asta.chart.linear.data.LinearStringData
import fit.asta.chart.util.ChartPoint
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import fit.asta.health.ui.common.AppDatePicker
import fit.asta.health.ui.common.AppTitleElevatedCard
import fit.asta.health.ui.common.AppTopTabBar
import java.time.LocalDate

@Composable
fun TrackExerciseScreenControl(
    exerciseTrackData: UiState<ExerciseResponse>,
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
                        if (isRightDrag.value && selectedItem.intValue > 0 && exerciseTrackData !is UiState.Loading) {

                            // Checking which tab option is selected by the User and showing the UI Accordingly
                            selectedItem.intValue = selectedItem.intValue - 1
                            setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))

                            // Resetting the drag flags
                            isRightDrag.value = false
                        }

                        // If the drag is a left drag
                        if (isLeftDrag.value && selectedItem.intValue < tabList.size - 1 && exerciseTrackData !is UiState.Loading) {

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
        AppTopTabBar(
            tabList = tabList,
            selectedItem = selectedItem.intValue
        ) {

            if (selectedItem.intValue != it && exerciseTrackData !is UiState.Loading) {

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

        when (exerciseTrackData) {

            is UiState.Idle -> {
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }

            is UiState.Loading -> {
                AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
            }

            is UiState.Success -> {
                Spacer(modifier = Modifier.height(8.dp))
                TrackSuccessScreen(exerciseTrackData = exerciseTrackData.data)
            }

            is UiState.ErrorMessage -> {
                AppErrorScreen(
                    text = exerciseTrackData.resId.toStringFromResId()
                ) {
                    setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun TrackSuccessScreen(exerciseTrackData: ExerciseResponse) {
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
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Daily Progress
        exerciseTrackData.dailyProgress?.let {
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
        exerciseTrackData.weekly?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.WEEKLY_PROGRESS) {
                    Row {
                        it.forEachIndexed { index, weekly ->
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
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

                                TitleTexts.Level2(
                                    text = TrackStringConstants.WEEKDAYS_STRINGS[index],

                                    // Text Feature
                                    textAlign = TextAlign.Start,
                                    color = AppTheme.colors.onSurface,
                                )
                            }
                        }
                    }
                }
            }
        }


        // Progress Bar Chart
        exerciseTrackData.progressGraph?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.PROGRESS) {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Heart Health Double Circular Chart
        exerciseTrackData.heartDetail?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.HEART_HEALTH) {
                    CircularRingChart.MultipleRingChart(
                        circularData = listOf(
                            CircularTargetDataBuilder(
                                target = 120f,
                                achieved = it.heartRate.rate.toFloat(),
                                siUnit = it.heartRate.unit,
                                cgsUnit = it.heartRate.unit,
                                conversionRate = { it }
                            ),
                            CircularTargetDataBuilder(
                                target = 150f,
                                achieved = it.bloodPressure.hg.toFloat(),
                                siUnit = it.bloodPressure.unit,
                                cgsUnit = it.bloodPressure.unit,
                                conversionRate = { it }
                            )
                        ),
                        circularCenter = listOf(
                            CircularRingTextCenter(
                                title = TrackStringConstants.HEART_RATE,
                                centerValue = "${it.heartRate.rate} ${it.heartRate.unit}",
                                status = it.heartRate.sts
                            ),
                            CircularRingTextCenter(
                                title = TrackStringConstants.BP,
                                centerValue =
                                "${it.bloodPressure.mm}/${it.bloodPressure.hg} ${it.bloodPressure.unit}",
                                status = it.bloodPressure.sts
                            )
                        )
                    )
                }
            }
        }


        // Heart Rate Line Chart
        exerciseTrackData.heartRateGraph?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.HEART_RATE) {
                    LinearChart.LineChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Blood Pressure Line Chart
        exerciseTrackData.bloodPressureGraph?.let { graphData ->
            item {
                AppTitleElevatedCard(title = TrackStringConstants.BLOOD_PRESSURE) {
                    LinearChart.LineChart(
                        linearData = LinearStringData(
                            yAxisReadings = graphData.multiGraphDataList.map {
                                ChartPoint.pointDataBuilder(it.yVal)
                            },
                            xAxisReadings = ChartPoint.pointDataBuilder(graphData.xAxis)
                        ),
                        colorConvention = LinearGridColorConvention(
                            textList = graphData.multiGraphDataList.map { it.name }
                        )
                    )
                }
            }
        }
    }
}
