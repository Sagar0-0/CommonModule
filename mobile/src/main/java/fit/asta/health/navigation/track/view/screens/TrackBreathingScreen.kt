package fit.asta.health.navigation.track.view.screens

import android.graphics.drawable.BitmapDrawable
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
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.dev.anirban.chartlibrary.circular.CircularChart
import com.dev.anirban.chartlibrary.circular.center.CircularImageCenter
import com.dev.anirban.chartlibrary.circular.center.CircularRingTextCenter
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartRow
import com.dev.anirban.chartlibrary.circular.charts.CircularRingChart
import com.dev.anirban.chartlibrary.circular.data.CircularTargetDataBuilder
import com.dev.anirban.chartlibrary.circular.foreground.CircularDonutTargetForeground
import com.dev.anirban.chartlibrary.linear.LinearChart
import com.dev.anirban.chartlibrary.linear.colorconvention.LinearGridColorConvention
import com.dev.anirban.chartlibrary.linear.data.LinearEmojiData
import com.dev.anirban.chartlibrary.linear.data.LinearStringData
import com.dev.anirban.chartlibrary.util.ChartPoint
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.view.components.TrackTopTabBar
import fit.asta.health.navigation.track.view.components.TrackingChartCard
import fit.asta.health.navigation.track.view.components.TrackingDetailsCard
import fit.asta.health.navigation.track.view.util.TrackUiEvent
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall
import java.text.DecimalFormat

@Composable
fun TrackBreathingScreenControl(
    breathingTrackData: TrackingNetworkCall<BreathingResponse>,
    setUiEvent: (TrackUiEvent) -> Unit
) {

    // This is the Item which is selected in the Top Tab Bar Layout
    val selectedItem = remember { mutableIntStateOf(0) }

    val context = LocalContext.current

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

        when (breathingTrackData) {

            is TrackingNetworkCall.Initialized -> {
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }

            is TrackingNetworkCall.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            is TrackingNetworkCall.Success -> {
                if (breathingTrackData.data == null)
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                else {
                    Spacer(modifier = Modifier.height(8.dp))
                    TrackSuccessScreen(breathingTrackData.data.breathingData)
                }
            }

            is TrackingNetworkCall.Failure -> {
                Toast.makeText(context, breathingTrackData.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

@Composable
private fun TrackSuccessScreen(breathingData: BreathingResponse.BreathingData) {

    val context = LocalContext.current

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

        // Daily Progress
        breathingData.progress?.let {
            item {
                TrackingChartCard(title = "Daily Progress") {
                    CircularDonutChartRow.TargetDonutChart(
                        circularData = CircularTargetDataBuilder(
                            target = it.target,
                            achieved = it.achieved,
                            siUnit = "Hrs",
                            cgsUnit = "min",
                            conversionRate = { it / 60f }
                        )
                    )
                }
            }
        }


        // Weekly Progress
        breathingData.weekly?.let {
            item {
                val weekDaysString = listOf("M", "T", "W", "T", "F", "S", "S")
                TrackingChartCard(title = "Weekly Progress") {
                    Row {
                        it.forEachIndexed { index, weekly ->
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
                                        contentDescription = null
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


        // Progress Bar Chart
        breathingData.progressGraph?.let {
            item {
                TrackingChartCard(title = "Progress") {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Vitamin D Details Card
        breathingData.weather?.let {
            item {
                TrackingChartCard(title = "Vitamin D Details") {
                    TrackingDetailsCard(
                        imageList = listOf(
                            R.drawable.image_sun,
                            R.drawable.track_image_duration,
                            R.drawable.track_image_exposure
                        ),
                        headerTextList = listOf("Avg Vitamin D", "Avg Duration", "Avg Exposure"),
                        valueList = listOf(
                            "${DecimalFormat("#.##").format(it.vitD.avg)} ${it.vitD.unit}",
                            "${DecimalFormat("#.##").format(it.duration.dur)} ${it.duration.unit}",
                            "${DecimalFormat("#.##").format(it.exposure.avg)} ${it.exposure.unit}"
                        )
                    )
                }
            }
        }


        // Air Purity Single Circle Chart
        breathingData.air?.let {
            item {
                TrackingChartCard(title = "Air Purity") {
                    CircularRingChart.SingleRingChart(
                        circularData = CircularTargetDataBuilder(
                            target = it.meta.max,
                            achieved = it.lvl,
                            siUnit = "",
                            cgsUnit = "",
                            conversionRate = { it }
                        ),
                        circularCenter = CircularRingTextCenter(
                            title = it.unit,
                            centerValue = DecimalFormat("#.##").format(it.lvl),
                            status = it.sts
                        )
                    )
                }
            }
        }


        // Heart Health Double Circular Chart
        breathingData.health?.let {
            item {
                TrackingChartCard(title = "Heart Health") {
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
                                title = "Heart Rate",
                                centerValue = "${it.heartRate.rate} ${it.heartRate.unit}",
                                status = it.heartRate.sts
                            ),
                            CircularRingTextCenter(
                                title = "BP",
                                centerValue =
                                "${it.bloodPressure.mm}/${it.bloodPressure.hg} ${it.bloodPressure.unit}",
                                status = it.bloodPressure.sts
                            )
                        )
                    )
                }
            }
        }

        // TODO :- How is your mood today card


        // Breathing Details Card
        breathingData.breathDetail?.let {
            item {
                TrackingChartCard(title = "Breathing Details") {
                    TrackingDetailsCard(
                        imageList = listOf(
                            R.drawable.image_inhaled_quantity,
                            R.drawable.image_total_breathes,
                            R.drawable.image_calories
                        ),
                        headerTextList = listOf(
                            "Inhaled Quantity",
                            "Total Breathes",
                            "Calories"
                        ),
                        valueList = listOf(
                            "${DecimalFormat("#.##").format(it.inhaled.avg)} ${it.inhaled.unit}",
                            "${DecimalFormat("#.##").format(it.breath.avg)} ${it.breath.unit}",
                            "${DecimalFormat("#.##").format(it.calories.avg)} ${it.calories.unit}"
                        )
                    )
                }
            }
        }


        // Air Purity Line Chart
        breathingData.airGraph?.let {
            item {
                TrackingChartCard(title = "Air Purity") {
                    LinearChart.LineChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis),
                            yMarkerList = ChartPoint.pointDataBuilder(
                                "Hazardous",
                                "V.Unhealthy",
                                "Unhealthy",
                                "Moderate",
                                "Good"
                            ).toMutableList()
                        )
                    )
                }
            }
        }


        // Mood Graph Line Chart
        breathingData.moodGraph?.let {
            item {
                TrackingChartCard(title = "Mood Graph") {
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


        // Heart Rate Line Chart
        breathingData.heartRateGraph?.let {
            item {
                TrackingChartCard(title = "Heart Rate") {
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
        breathingData.bloodPressureGraph?.let { graphData ->
            item {
                TrackingChartCard(title = "Blood Pressure") {
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