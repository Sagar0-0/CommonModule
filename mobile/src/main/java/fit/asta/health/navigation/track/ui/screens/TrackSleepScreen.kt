package fit.asta.health.navigation.track.ui.screens

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
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartColumn
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartRow
import com.dev.anirban.chartlibrary.circular.data.CircularDonutListData
import com.dev.anirban.chartlibrary.circular.data.CircularTargetDataBuilder
import com.dev.anirban.chartlibrary.circular.foreground.CircularDonutTargetForeground
import com.dev.anirban.chartlibrary.linear.LinearChart
import com.dev.anirban.chartlibrary.linear.data.LinearEmojiData
import com.dev.anirban.chartlibrary.linear.data.LinearStringData
import com.dev.anirban.chartlibrary.linear.plots.LinearGradientLinePlot
import com.dev.anirban.chartlibrary.util.ChartPoint
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.ui.components.TrackTopTabBar
import fit.asta.health.navigation.track.ui.components.TrackingChartCard
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import fit.asta.health.navigation.track.ui.util.TrackingNetworkCall

@Composable
fun TrackSleepScreenControl(
    sleepTrackData: TrackingNetworkCall<SleepResponse>,
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

        when (sleepTrackData) {

            is TrackingNetworkCall.Initialized -> {
                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
            }

            is TrackingNetworkCall.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            is TrackingNetworkCall.Success -> {
                if (sleepTrackData.data == null)
                    Toast.makeText(context, TrackStringConstants.NO_DATA, Toast.LENGTH_SHORT).show()
                else {
                    Spacer(modifier = Modifier.height(8.dp))
                    TrackSuccessScreen(sleepData = sleepTrackData.data.sleepData)
                }
            }

            is TrackingNetworkCall.Failure -> {
                Toast.makeText(context, sleepTrackData.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

@Composable
fun TrackSuccessScreen(sleepData: SleepResponse.SleepData) {

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
        sleepData.progress?.let {
            item {
                TrackingChartCard(title = "Daily Progress") {
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
        sleepData.weekly?.let {
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


        // Sleep Duration Line Chart
        sleepData.sleepDurationGraph?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.SLEEP_DURATION) {
                    LinearChart.LineChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis)
                        )
                    )
                }
            }
        }


        // Sleep Regularity
        sleepData.sleepRegularityGraph?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.SLEEP_REGULARITY) {
                    LinearChart.GradientChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis),
                            yMarkerList = ChartPoint.pointDataBuilder(
                                TrackStringConstants.AWAKE,
                                TrackStringConstants.DEEP_SLEEP,
                                TrackStringConstants.SLEEP,
                                TrackStringConstants.ASLEEP,
                                TrackStringConstants.BED_TIME
                            ).toMutableList()
                        ),
                        plot = LinearGradientLinePlot(
                            colorList = listOf(
                                Color(0xFFE5E787).copy(alpha = .6f),
                                Color(0xFF85DE50).copy(alpha = .6f),
                                Color(0xFF57D6BF).copy(alpha = .6f),
                                Color(0xFF43B4E4).copy(alpha = .6f),
                                Color(0xFF3A60E6).copy(alpha = .6f),
                                Color(0xFF57D6BF).copy(alpha = .6f),
                                Color(0xFFD02596).copy(alpha = .6f)
                            )
                        )
                    )
                }
            }
        }


        // Sleep Ratio Circular Graph
        sleepData.sleepRatio?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.SLEEP_RATIO) {
                    CircularDonutChartColumn.DonutChartColumn(
                        circularData = CircularDonutListData(
                            itemsList = listOf(
                                Pair(TrackStringConstants.NORMAL, it.normal),
                                Pair(TrackStringConstants.DEEP, it.deep),
                                Pair(TrackStringConstants.DELAY, it.delay),
                                Pair(TrackStringConstants.DISTURBED, it.disturbed)
                            ),
                            siUnit = TrackStringConstants.TIME_SI_UNIT,
                            cgsUnit = TrackStringConstants.TIME_CGS_UNIT,
                            conversionRate = { it / 60f }
                        )
                    )
                }
            }
        }


        // TODO :- Your Mood Card


        // Mood Line Graph Card
        sleepData.moodGraph?.let {
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


        // Goals Graph
        sleepData.goalGraph?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.GOALS) {
                    LinearChart.LineChart(
                        linearData = LinearStringData(
                            yAxisReadings = listOf(ChartPoint.pointDataBuilder(it.yData)),
                            xAxisReadings = ChartPoint.pointDataBuilder(it.xAxis),
                            yMarkerList = ChartPoint.pointDataBuilder(
                                TrackStringConstants.SOUND_SLEEP,
                                TrackStringConstants.CLEAR_MIND,
                                TrackStringConstants.FALL_ASLEEP,
                                TrackStringConstants.DE_STRESS,
                                TrackStringConstants.GOAL
                            ).toMutableList()
                        )
                    )
                }
            }
        }
    }
}
