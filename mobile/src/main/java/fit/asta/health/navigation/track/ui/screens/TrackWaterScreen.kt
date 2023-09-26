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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import fit.asta.chart.circular.CircularChart
import fit.asta.chart.circular.center.CircularImageCenter
import fit.asta.chart.circular.charts.CircularDonutChartRow
import fit.asta.chart.circular.data.CircularDonutListData
import fit.asta.chart.circular.data.CircularTargetDataBuilder
import fit.asta.chart.circular.foreground.CircularDonutTargetForeground
import fit.asta.chart.linear.LinearChart
import fit.asta.chart.linear.colorconvention.LinearGridColorConvention
import fit.asta.chart.linear.data.LinearStringData
import fit.asta.chart.util.ChartPoint
import fit.asta.health.R
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import fit.asta.health.designsystemx.organism.common.AstaDatePicker
import fit.asta.health.navigation.track.ui.components.TrackTopTabBar
import fit.asta.health.designsystemx.organism.common.AstaTitleElevatedCard
import fit.asta.health.navigation.track.ui.components.TrackingDetailsCard
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import java.text.DecimalFormat
import java.time.LocalDate

@Composable
fun TrackWaterScreenControl(
    waterTrackData: UiState<WaterResponse>,
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
                        AstaThemeX.colorsX.surface.toArgb(),
                        AstaThemeX.colorsX.onSurface.toArgb(),
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
                        if (isRightDrag.value && selectedItem.intValue > 0 && waterTrackData !is UiState.Loading) {

                            // Checking which tab option is selected by the User and showing the UI Accordingly
                            selectedItem.intValue = selectedItem.intValue - 1
                            setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))

                            // Resetting the drag flags
                            isRightDrag.value = false
                        }

                        // If the drag is a left drag
                        if (isLeftDrag.value && selectedItem.intValue < tabList.size - 1 && waterTrackData !is UiState.Loading) {

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

            if (selectedItem.intValue != it && waterTrackData !is UiState.Loading) {

                // Checking which tab option is selected by the User and showing the UI Accordingly
                selectedItem.intValue = it
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }
        }

        // Date Picker
        AstaDatePicker(
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

        when (waterTrackData) {

            is UiState.Idle -> {
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }

            is UiState.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            is UiState.Success -> {
                Spacer(modifier = Modifier.height(8.dp))
                TrackSuccessScreen(waterTrackData.data.waterData)
            }

            is UiState.ErrorMessage -> {
                AppErrorScreen(
                    isInternetError = false,
                    desc = waterTrackData.resId.toStringFromResId()
                ) {
                    setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun TrackSuccessScreen(waterTrackData: WaterResponse.WaterData) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(
                    ColorUtils.blendARGB(
                        AstaThemeX.colorsX.surface.toArgb(),
                        AstaThemeX.colorsX.onSurface.toArgb(),
                        0.08f
                    )
                )
            ),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
    ) {

        // Daily Progress Circular Target Chart 
        waterTrackData.dailyProgress?.let {
            item {
                AstaTitleElevatedCard(title = TrackStringConstants.DAILY_PROGRESS) {
                    CircularDonutChartRow.TargetDonutChart(
                        circularData = CircularTargetDataBuilder(
                            target = it.target,
                            achieved = it.achieved,
                            siUnit = TrackStringConstants.LITRES_SI_UNIT,
                            cgsUnit = TrackStringConstants.LITRES_SI_UNIT,
                            conversionRate = { it }
                        )
                    )
                }
            }
        }


        // Weekly Progress Target Charts
        waterTrackData.weekly?.let { weeklyData ->
            item {
                AstaTitleElevatedCard(title = TrackStringConstants.WEEKLY_PROGRESS) {
                    Row {
                        weeklyData.forEachIndexed { index, weekly ->
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                CircularChart.DonutChartImage(
                                    modifier = Modifier.size(55.dp),
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
                                    color = AstaThemeX.colorsX.onSurface,
                                )
                            }
                        }
                    }
                }
            }
        }


        // Amount Consumed Composable Card
        waterTrackData.amountConsumed?.let {
            item {
                AstaTitleElevatedCard(title = TrackStringConstants.AMOUNT_CONSUMED) {
                    TrackingDetailsCard(
                        imageList = listOf(
                            R.drawable.track_water_glass,
                            R.drawable.track_water_total
                        ),
                        headerTextList = listOf(
                            TrackStringConstants.DAILY_AVG,
                            TrackStringConstants.TOTAL
                        ),
                        valueList = listOf(
                            "${DecimalFormat("#.##").format(it.dailyAvg)} L",
                            "${DecimalFormat("#.##").format(it.totalAmt)} L"
                        )
                    )
                }
            }
        }


        // Daily Progress , Monthly Progress , Yearly Progress Line Charts
        waterTrackData.progress?.let { graphData ->
            item {
                AstaTitleElevatedCard(title = TrackStringConstants.PROGRESS) {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(graphData.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(graphData.xAxis)
                        )
                    )
                }
            }
        }


        // Ratio Donut Chart is drawn
        waterTrackData.ratio?.let {
            item {
                AstaTitleElevatedCard(title = TrackStringConstants.RATIO) {
                    CircularDonutChartRow.DonutChartRow(
                        circularData = CircularDonutListData(
                            itemsList = listOf(
                                Pair(TrackStringConstants.WATER, it.water),
                                Pair(TrackStringConstants.JUICE, it.juice),
                                Pair(TrackStringConstants.SOFT_DRINK, it.drink)
                            ),
                            siUnit = TrackStringConstants.LITRES_SI_UNIT,
                            cgsUnit = TrackStringConstants.LITRES_SI_UNIT,
                            conversionRate = { it }
                        )
                    )
                }
            }
        }


        // Double Line Chart is drawn here
        waterTrackData.beverageData?.let { graphData ->
            item {
                AstaTitleElevatedCard(title = TrackStringConstants.BEVERAGES) {
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