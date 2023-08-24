package fit.asta.health.navigation.track.ui.screens

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.dev.anirban.chartlibrary.other.bmi.BmiChart
import com.dev.anirban.chartlibrary.other.bmi.data.BmiData
import com.dev.anirban.chartlibrary.util.ChartPoint
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiState
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.ui.components.TrackTopTabBar
import fit.asta.health.navigation.track.ui.components.TrackingChartCard
import fit.asta.health.navigation.track.ui.components.TrackingDetailsCard
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import java.text.DecimalFormat
import kotlin.math.abs

@Composable
fun TrackStepsScreenControl(
    stepsTrackData: UiState<StepsResponse>,
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

        when (stepsTrackData) {

            is UiState.Idle -> {
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }

            is UiState.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            is UiState.Success -> {
                Spacer(modifier = Modifier.height(8.dp))
                TrackSuccessScreen(stepsTrackData = stepsTrackData.data.stepsData)
            }

            is UiState.Error -> {
                // TODO:-
            }
        }
    }
}

@Composable
private fun TrackSuccessScreen(stepsTrackData: StepsResponse.StepsData) {

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
        stepsTrackData.progress?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.DAILY_PROGRESS) {
                    CircularDonutChartRow.TargetDonutChart(
                        circularData = CircularTargetDataBuilder(
                            target = it.target.distance.dis,
                            achieved = it.achieved.distance.dis,
                            siUnit = it.target.distance.unit,
                            cgsUnit = it.target.distance.unit,
                            conversionRate = { it }
                        )
                    )
                }
            }
        }


        // Weekly Progress
        stepsTrackData.weekly?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.WEEKLY_PROGRESS) {
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


        // Vitamin D Details Card
        stepsTrackData.weather?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.VITAMIN_D_DETAILS) {
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


        // Steps details Card
        stepsTrackData.stepsDetail?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.STEPS_DETAILS) {
                    TrackingDetailsCard(
                        imageList = listOf(
                            R.drawable.image_total_distance,
                            R.drawable.track_image_duration,
                            R.drawable.image_walking_intensity
                        ),
                        headerTextList = listOf(
                            TrackStringConstants.DISTANCE,
                            TrackStringConstants.DURATION,
                            TrackStringConstants.STEPS
                        ),
                        valueList = listOf(
                            "${DecimalFormat("#.##").format(it.distance.dis)} ${it.distance.unit}",
                            "${DecimalFormat("#.##").format(it.duration.dur)} ${it.duration.unit}",
                            "${DecimalFormat("#.##").format(it.steps.steps)} ${it.steps.unit}"
                        )
                    )
                }
            }
        }


        // BMI Chart Data
        stepsTrackData.bmiData?.let {

            val idealWeight = DecimalFormat("#.##").format(it.idealWgt)
            val weight = DecimalFormat("#.##").format(it.weight)
            val difference = DecimalFormat("#.##").format(abs(it.weight - it.idealWgt))

            item {
                TrackingChartCard {
                    Column {
                        BmiChart.BMIChart(
                            bmiData = BmiData(
                                readingValue = ChartPoint(it.bmi),
                                idealWeight = "$idealWeight ${it.weightUnit}",
                                weight = "$weight ${it.weightUnit}",
                                bmiUnit = it.unit
                            )
                        )

                        // Text to be shown Under the Chart
                        val text = if (it.weight > it.idealWgt)
                            "You need to lose $difference ${it.weightUnit} to reach healthy BMI around " +
                                    "${DecimalFormat("#.##").format(it.idealBmi)} ${it.unit}"
                        else if (it.weight == it.idealWgt)
                            "You are at a healthy BMI"
                        else
                            "You need to gain $difference ${it.weightUnit} to reach healthy BMI around " +
                                    "${DecimalFormat("#.##").format(it.idealBmi)} ${it.unit}"

                        // Text Composable under BMI Chart
                        Text(
                            text = text,

                            modifier = Modifier
                                .padding(horizontal = 16.dp),

                            maxLines = 2,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                }
            }
        }


        // Speed Card Composable
        stepsTrackData.speed?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.SPEED_DETAILS) {
                    TrackingDetailsCard(
                        imageList = listOf(
                            R.drawable.image_walking_intensity,
                            R.drawable.image_exercise_intensity,
                            R.drawable.image_calories
                        ),
                        headerTextList = listOf(
                            TrackStringConstants.AVG_SPEED,
                            TrackStringConstants.AVG_INTENSITY,
                            TrackStringConstants.CALORIES
                        ),
                        valueList = listOf(
                            "${DecimalFormat("#.##").format(it.avgSpeed)} ${it.avgSpeedUnit}",
                            "${DecimalFormat("#.##").format(it.avgIntensity)} ${it.avgIntensityUnit}",
                            "${DecimalFormat("#.##").format(it.avgCalories)} ${it.avgCaloriesUnit}"
                        )
                    )
                }
            }
        }


        // Heart Health Double Circular Chart
        stepsTrackData.health?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.HEART_HEALTH) {
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


        // Steps Line Chart
        stepsTrackData.stepGraph?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.STEPS) {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Steps Line Chart
        stepsTrackData.distanceGraph?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.DISTANCE) {
                    LinearChart.LineChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Steps Line Chart
        stepsTrackData.intensityGraph?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.INTENSITY) {
                    LinearChart.BarChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // TODO :- Your Mood Composable Card Comes here


        // Mood Graph Line Chart
        stepsTrackData.moodGraph?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.MOOD_GRAPH) {
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


        // Blood Pressure Line Chart
        stepsTrackData.bloodPressureGraph?.let { graphData ->
            item {
                TrackingChartCard(title = TrackStringConstants.BLOOD_PRESSURE) {
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