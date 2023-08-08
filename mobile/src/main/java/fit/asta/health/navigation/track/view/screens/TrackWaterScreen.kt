package fit.asta.health.navigation.track.view.screens

import android.util.Log.d
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.dev.anirban.chartlibrary.circular.CircularChart
import com.dev.anirban.chartlibrary.circular.center.CircularImageCenter
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartRow
import com.dev.anirban.chartlibrary.circular.data.CircularDonutListData
import com.dev.anirban.chartlibrary.circular.data.CircularTargetDataBuilder
import com.dev.anirban.chartlibrary.circular.foreground.CircularDonutTargetForeground
import com.dev.anirban.chartlibrary.linear.LinearChart
import com.dev.anirban.chartlibrary.linear.colorconvention.LinearGridColorConvention
import com.dev.anirban.chartlibrary.linear.data.LinearStringData
import com.dev.anirban.chartlibrary.util.ChartPoint
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import fit.asta.health.navigation.track.view.components.TrackTopTabBar
import fit.asta.health.navigation.track.view.components.TrackingChartCard
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall

@Composable
fun TrackWaterScreenControl(
    waterTrackData: TrackingNetworkCall<WaterResponse>,
    setTrackStatus: (Int) -> Unit
) {

    // This is the Item which is selected in the Top Tab Bar Layout
    val selectedItem = remember { mutableIntStateOf(0) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setTrackStatus(selectedItem.intValue)
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
                setTrackStatus(selectedItem.intValue)
            }
        }

        when (waterTrackData) {

            is TrackingNetworkCall.Initialized -> {
                setTrackStatus(selectedItem.intValue)
            }

            is TrackingNetworkCall.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            is TrackingNetworkCall.Success -> {
                if (waterTrackData.data == null)
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                else {
                    Spacer(modifier = Modifier.height(8.dp))
                    TrackSuccessScreen(waterTrackData.data.waterData)
                }
            }

            is TrackingNetworkCall.Failure -> {
                d("Water Track Screen", waterTrackData.message.toString())
                Toast.makeText(context, waterTrackData.message.toString(), Toast.LENGTH_SHORT)
                    .show()
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

        waterTrackData.progress?.let {
            item {
                TrackingChartCard(title = "Daily Progress") {
                    CircularDonutChartRow.TargetDonutChart(
                        circularData = CircularTargetDataBuilder(
                            target = it.target,
                            achieved = it.achieved,
                            siUnit = "L",
                            cgsUnit = "mL",
                            conversionRate = { it / 1000f }
                        )
                    )
                }
            }
        }

        waterTrackData.weekly?.let { weeklyData ->
            item {
                val weekDaysString = listOf("M", "T", "W", "T", "F", "S", "S")
                TrackingChartCard(title = "Weekly Progress") {
                    Row {
                        weeklyData.forEachIndexed { index, weekly ->
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(spacing.small),
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
                                        contentDescription = "Achieved"
                                    ),
                                    circularForeground = CircularDonutTargetForeground(strokeWidth = 10f)
                                )

                                Text(
                                    text = weekDaysString[index],

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

        waterTrackData.dailyProgress?.let { graphData ->
            item {
                TrackingChartCard(title = "Daily Progress") {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(
                                ChartPoint.pointDataBuilder(graphData.yData)
                            ),
                            xAxisReadings = ChartPoint.pointDataBuilder(graphData.xAxis)
                        )
                    )
                }
            }
        }


        waterTrackData.ratio?.let { ratioData ->
            item {
                TrackingChartCard(title = "Ratio") {
                    CircularDonutChartRow.DonutChartRow(
                        circularData = CircularDonutListData(
                            itemsList = listOf(
                                Pair("Water", ratioData.water),
                                Pair("Juice", ratioData.juice),
                                Pair("Soft Drink", ratioData.drink)
                            ),
                            siUnit = "L",
                            cgsUnit = "mL",
                            conversionRate = { it / 1000f }
                        )
                    )
                }
            }
        }

        waterTrackData.beverageData?.let { graphData ->
            item {
                TrackingChartCard(title = "Beverages") {
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