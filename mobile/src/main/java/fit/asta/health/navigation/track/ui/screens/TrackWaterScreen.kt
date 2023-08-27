package fit.asta.health.navigation.track.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import fit.asta.health.navigation.track.ui.components.TrackTopTabBar
import fit.asta.health.navigation.track.ui.components.TrackingChartCard
import fit.asta.health.navigation.track.ui.components.TrackingDetailsCard
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import java.text.DecimalFormat

@Composable
fun TrackWaterScreenControl(
    waterTrackData: UiState<WaterResponse>,
    setUiEvent: (TrackUiEvent) -> Unit
) {

    // This is the Item which is selected in the Top Tab Bar Layout
    val selectedItem = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(
                    ColorUtils.blendARGB(
                        MaterialTheme.colorScheme.surface.toArgb(),
                        MaterialTheme.colorScheme.onSurface.toArgb(),
                        0.08f
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // This Function makes the Tab Layout UI
        TrackTopTabBar(selectedItem = selectedItem.intValue) {

            if (selectedItem.intValue != it) {

                // Checking which tab option is selected by the User and showing the UI Accordingly
                selectedItem.intValue = it
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }
        }

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

            is UiState.Error -> {
                AppErrorScreen(desc = waterTrackData.resId.toStringFromResId()) {
                    setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
                }
            }
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
                        MaterialTheme.colorScheme.surface.toArgb(),
                        MaterialTheme.colorScheme.onSurface.toArgb(),
                        0.08f
                    )
                )
            )
    ) {

        // Daily Progress Circular Target Chart 
        waterTrackData.dailyProgress?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.DAILY_PROGRESS) {
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
                TrackingChartCard(title = TrackStringConstants.WEEKLY_PROGRESS) {
                    Row {
                        weeklyData.forEachIndexed { index, weekly ->
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(spacing.small),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                fit.asta.chart.circular.CircularChart.DonutChartImage(
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
                                    color = MaterialTheme.colorScheme.onSurface,
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
                TrackingChartCard(title = TrackStringConstants.AMOUNT_CONSUMED) {
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
                TrackingChartCard(title = TrackStringConstants.PROGRESS) {
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
                TrackingChartCard(title = TrackStringConstants.RATIO) {
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
                TrackingChartCard(title = TrackStringConstants.BEVERAGES) {
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