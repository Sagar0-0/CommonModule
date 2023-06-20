package fit.asta.health.navigation.track.view.screens

import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dev.anirban.chartlibrary.circular.center.CircularRingTextCenter
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartColumn
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartRow
import com.dev.anirban.chartlibrary.circular.charts.CircularRingChart
import com.dev.anirban.chartlibrary.circular.data.CircularDonutListData
import com.dev.anirban.chartlibrary.circular.data.CircularTargetDataBuilder
import com.dev.anirban.chartlibrary.linear.LinearChart
import com.dev.anirban.chartlibrary.linear.colorconvention.LinearGridColorConvention
import com.dev.anirban.chartlibrary.linear.data.LinearEmojiData
import com.dev.anirban.chartlibrary.linear.data.LinearStringData
import com.dev.anirban.chartlibrary.linear.plots.LinearGradientLinePlot
import com.dev.anirban.chartlibrary.util.ChartPoint
import fit.asta.health.R
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.navigation.track.view.components.TrackingChangeInPlanCard
import fit.asta.health.navigation.track.view.components.TrackingWeatherCard
import fit.asta.health.navigation.track.view.components.TrackingNoteCard
import fit.asta.health.navigation.track.view.components.TrackingChartCard
import fit.asta.health.navigation.track.view.components.TrackingDetailsCard
import fit.asta.health.navigation.track.view.components.TrackingYourMoodCard
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

// Preview Composable Function
@Preview(
    "Light",
    heightDp = 2000,
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 2000,
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            TrackStatisticsUI(
                trackViewModel = TrackViewModel()
            )
        }
    }
}

@Composable
fun TrackStatisticsUI(
    trackViewModel: TrackViewModel
) {

    // This Column contains the body of the screen
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {

        TrackingChartCard(
            title = "Daily Progress"
        ) {

            Column {
                CircularDonutChartRow.TargetDonutChart(
                    circularData = CircularTargetDataBuilder(
                        target = 4000f,
                        achieved = 5000f,
                        siUnit = "L",
                        cgsUnit = "mL",
                        conversionRate = { it / 1000f }
                    )
                )

                // Draw Over Hydration Text in the UI
                TrackingNoteCard(
                    labelIcon = R.drawable.image_note,
                    title = "Over hydration",
                    secondaryTitle = "You have crossed your daily intake",
                    bodyText = "Over hydration can lead to water intoxication. This occurs when " +
                            "the amount of salt and other electrolytes in your body become " +
                            "too diluted."
                )
            }
        }

        TrackingChartCard {
            TrackingWeatherCard(
                temperature = "22°C",
                location = "Bengaluru",
                image = R.drawable.image_sun
            )
        }

        TrackingChartCard {
            TrackingChangeInPlanCard(
                startLabelIcon = R.drawable.image_sunny,
                endLabelIcon = R.drawable.image_upward_arrow,
                value = "500 mL",
                bodyText = "Due to high rise in temperature extra 500 ml " +
                        " of water is added to the Target. "
            ) {

                // Sunny Text and the temperature in Degree
                Column {

                    // Sunny Text
                    Text(
                        text = "Sunny",

                        // Text and Font Properties
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
//                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp
                    )

                    // Degree Text
                    Text(
                        text = "32 °C",

                        // Text and Font Properties
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
//                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 18.sp
                    )
                }
            }
        }

        TrackingChartCard {
            TrackingChangeInPlanCard(
                startLabelIcon = R.drawable.image_night_shift,
                endLabelIcon = R.drawable.image_upward_arrow,
                value = "500 mL",
                bodyText = "Based on the weather conditions and" +
                        " your work shift extra 500 ml of water is added to the target."
            ) {

                Text(
                    text = "Weather & Work Timings",

                    // Text and Font Properties
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface,
//                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp
                )
            }
        }

        TrackingChartCard(
            title = "Breathing Details",
        ) {
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
                    "11,000 litres",
                    "6620",
                    "258 kcal"
                )
            )
        }

        TrackingChartCard(title = "Air Purity") {
            CircularRingChart.SingleRingChart(
                circularData = CircularTargetDataBuilder(
                    target = 500f,
                    achieved = 193f,
                    siUnit = "",
                    cgsUnit = "",
                    conversionRate = { it }
                ),
                circularCenter = CircularRingTextCenter(
                    title = "AQI",
                    centerValue = "193",
                    status = "Moderate"
                )
            )
        }

        TrackingChartCard(title = "Heart Health") {
            CircularRingChart.MultipleRingChart(
                circularData = listOf(
                    CircularTargetDataBuilder(
                        target = 120f,
                        achieved = 75f,
                        siUnit = "bpm",
                        cgsUnit = "bpm",
                        conversionRate = { it }
                    ),
                    CircularTargetDataBuilder(
                        target = 150f,
                        achieved = 120f,
                        siUnit = "mmhg",
                        cgsUnit = "mmhg",
                        conversionRate = { it }
                    )
                ),
                circularCenter = listOf(
                    CircularRingTextCenter(
                        title = "Heart Rate",
                        centerValue = "75 bpm",
                        status = "Normal"
                    ),
                    CircularRingTextCenter(
                        title = "BP",
                        centerValue = "120/80 mmhg",
                        status = "Normal"
                    )
                )
            )
        }

        TrackingChartCard(
            title = "Daily Progress"
        ) {
            LinearChart.BarChart(
                linearData = LinearStringData(
                    yAxisReadings = listOf(
                        ChartPoint.pointDataBuilder(5f, 10f, 6f, 4.2f, 8f, 10f, 6f)
                    ),
                    xAxisReadings = ChartPoint.pointDataBuilder(
                        "6-7", "8-9", "10-11", "12-1", "2-3", "4-5", "6-7"
                    )
                )
            )
        }

        TrackingChartCard(title = "Heart Rate") {
            LinearChart.LineChart(
                linearData = LinearStringData(
                    yAxisReadings = listOf(
                        ChartPoint.pointDataBuilder(78f, 68f, 59f, 78f, 88f, 83f, 78f)
                    ),
                    xAxisReadings = ChartPoint.pointDataBuilder(
                        "6-7", "8-9", "10-11", "12-1", "2-3", "4-5", "6-7"
                    )
                )
            )
        }

        TrackingChartCard(
            title = "Sleep Regularity"
        ) {

            LinearChart.GradientChart(
                linearData = LinearStringData(
                    yAxisReadings = listOf(
                        ChartPoint.pointDataBuilder(4f, 0f, 1.7f, 1.9f, 2f, 4f)
                    ),
                    xAxisReadings = ChartPoint.pointDataBuilder(
                        "8-9", "10-11", "12-1", "2-3", "4-5", "6-7"
                    ),
                    yMarkerList = ChartPoint.pointDataBuilder(
                        "Awake",
                        "Deep Sleep",
                        "Sleep",
                        "Asleep After",
                        "Bed Time"
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

        TrackingChartCard(
            title = "Sleep Ratio"
        ) {

            CircularDonutChartColumn.DonutChartColumn(
                circularData = CircularDonutListData(
                    itemsList = listOf(
                        Pair("Normal", 450f),
                        Pair("Deep", 180f),
                        Pair("Delay", 30f),
                        Pair("Distributed", 60f)
                    ),
                    siUnit = "Hrs",
                    cgsUnit = "Min",
                    conversionRate = { it / 60f }
                )
            )
        }


        TrackingChartCard {
            TrackingYourMoodCard(
                moodImage = R.drawable.image_happy_face,
                moodText = "I feel Happy Today"
            )
        }


        TrackingChartCard(
            title = "Mood Graph"
        ) {
            LinearChart.EmojiLineChart(
                linearData = LinearEmojiData(
                    yAxisReadings = listOf(
                        ChartPoint.pointDataBuilder(
                            6f, 4f, 2f, 0f, 3f, 5f, 6f
                        )
                    ),
                    xAxisReadings = ChartPoint.pointDataBuilder(
                        "Jan", "Mar", "May", "Jul", "Sep", "Nov", "Dec"
                    ),
                    yMarkerList = ChartPoint.pointDataBuilder(
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.emoji_furious
                        ) as BitmapDrawable,
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.emoji_angry
                        ) as BitmapDrawable,
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.emoji_sad
                        ) as BitmapDrawable,
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.emoji_depressed
                        ) as BitmapDrawable,
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.emoji_confused
                        ) as BitmapDrawable,
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.emoji_calm
                        ) as BitmapDrawable,
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            R.drawable.emoji_happy
                        ) as BitmapDrawable
                    ).toMutableList()
                )
            )
        }

        TrackingChartCard(title = "Heart Rate") {
            LinearChart.LineChart(
                linearData = LinearStringData(
                    yAxisReadings = listOf(
                        ChartPoint.pointDataBuilder(78f, 68f, 59f, 78f, 88f, 83f, 78f)
                    ),
                    xAxisReadings = ChartPoint.pointDataBuilder(
                        "6-7", "8-9", "10-11", "12-1", "2-3", "4-5", "6-7"
                    )
                )
            )
        }

        TrackingChartCard(title = "Blood Pressure") {
            LinearChart.LineChart(
                linearData = LinearStringData(
                    yAxisReadings = listOf(
                        ChartPoint.pointDataBuilder(40f, 80f, 85f, 76f, 86f, 94f, 108f),
                        ChartPoint.pointDataBuilder(80f, 84f, 70f, 42f, 100f, 100f, 112f)
                    ),
                    xAxisReadings = ChartPoint.pointDataBuilder(
                        "Jan", "Mar", "May", "Jul", "Sep", "Nov", "Dec"
                    )
                ),
                colorConvention = LinearGridColorConvention(
                    textList = listOf("Systolic", "Diastolic")
                )
            )
        }

        Spacer(modifier = Modifier.height(124.dp))
    }
}