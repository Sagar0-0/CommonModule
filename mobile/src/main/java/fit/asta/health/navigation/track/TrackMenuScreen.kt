package fit.asta.health.navigation.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.chart.circular.charts.CircularDonutChartColumn
import fit.asta.chart.circular.charts.CircularDonutChartRow
import fit.asta.chart.circular.data.CircularDonutListData
import fit.asta.chart.circular.data.CircularTargetDataBuilder
import fit.asta.chart.circular.decoration.CircularDecoration
import fit.asta.chart.other.bmi.BmiChart
import fit.asta.chart.other.bmi.data.BmiData
import fit.asta.chart.util.ChartPoint
import fit.asta.health.R
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.ui.components.TrackDatePicker
import fit.asta.health.navigation.track.ui.components.TrackingChartCard
import fit.asta.health.navigation.track.ui.components.TrackingDetailsCard
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import fit.asta.health.navigation.track.ui.viewmodel.TrackViewModel
import java.text.DecimalFormat
import java.time.LocalDate
import kotlin.math.abs

@Composable
fun TrackMenuScreenControl() {

    val trackViewModel: TrackViewModel = hiltViewModel()

    // Home Menu State
    val homeMenuState = trackViewModel.homeScreenDetails
        .collectAsStateWithLifecycle().value

    // Calendar Data
    val calendarData = trackViewModel.calendarData
        .collectAsStateWithLifecycle().value

    // This function loads the data for the Home Menu Screen
    LaunchedEffect(Unit) {
        trackViewModel.getHomeDetails()
    }

    // This conditional handles the State of the Api call and shows the UI according to the state
    when (homeMenuState) {

        // Initialized State
        is UiState.Idle -> {
            trackViewModel.getHomeDetails()
        }

        // Loading State
        is UiState.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        // Success State
        is UiState.Success -> {
            TrackMenuSuccessScreen(
                homeMenuData = homeMenuState.data.homeMenuData,
                calendarData = calendarData,
                setUiEvent = { trackViewModel.uiEventListener(it) }
            )
        }

        // failure State
        is UiState.Error -> {
            AppErrorScreen(desc = homeMenuState.resId.toStringFromResId()) {
                trackViewModel.getHomeDetails()
            }
        }
    }
}


/**
 * This function Provides the UI for the Home Menu Screen when the api state is Success i.e the data
 * is fetched from the server successfully
 *
 * @param homeMenuData This function contains the data of the response of the Api call for the home
 * menu state
 * @param setUiEvent This function is used to set the track Option i.e Water , breathing , steps,
 * meditation etc
 */
@Composable
private fun TrackMenuSuccessScreen(
    homeMenuData: HomeMenuResponse.HomeMenuData,
    calendarData: LocalDate,
    setUiEvent: (TrackUiEvent) -> Unit
) {

    val context = LocalContext.current

    // Parent UI for all the composable
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

        // Date Picker
        item {
            TrackDatePicker(
                date = calendarData.dayOfMonth,
                month = calendarData.monthValue,
                year = calendarData.year,
                onPreviousButtonClick = { setUiEvent(TrackUiEvent.ClickedPreviousDateButton) },
                onNextButtonClick = { setUiEvent(TrackUiEvent.ClickedNextDateButton) },
                onDateChanged = { date, month, year ->
                    setUiEvent(TrackUiEvent.SetNewDate(date = date, month = month, year = year))
                }
            )
        }

        // Time Spent Chart Card
        homeMenuData.timeSpent?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.TIME_SPENT) {
                    CircularDonutChartColumn.DonutChartColumn(
                        circularData = CircularDonutListData(
                            itemsList = listOf(
                                Pair(TrackStringConstants.MEDITATION, it.meditation),
                                Pair(TrackStringConstants.STEPS, it.steps),
                                Pair(TrackStringConstants.SLEEP, it.sleep),
                                Pair(TrackStringConstants.SUNLIGHT, it.sunlight),
                                Pair(TrackStringConstants.BREATHING, it.breathing),
                                Pair(TrackStringConstants.WATER, it.water)
                            ),
                            siUnit = TrackStringConstants.TIME_CGS_UNIT,
                            cgsUnit = TrackStringConstants.TIME_CGS_UNIT,
                            conversionRate = { it }
                        ),
                        circularDecoration = CircularDecoration.donutChartDecorations(
                            colorList = listOf(
                                Color(0xFF90EFD8),
                                Color(0xFFF7AD1A),
                                Color(0xFF299F23),
                                Color(0xFFFF2E2E),
                                Color(0xFF6C27C7),
                                Color(0xFFB25FC0)
                            )
                        )
                    )
                }
            }
        }


        // Heart Health Details Card
        homeMenuData.healthDetail?.let {
            item {
                TrackingChartCard(title = TrackStringConstants.HEART_HEALTH) {
                    TrackingDetailsCard(
                        imageList = listOf(R.drawable.heartrate, R.drawable.pulse_rate),
                        headerTextList = listOf(
                            TrackStringConstants.BLOOD_PRESSURE,
                            TrackStringConstants.HEART_RATE
                        ),
                        valueList = listOf(
                            "${it.bloodPressure.mm}/${it.bloodPressure.hg} ${it.bloodPressure.unit}",
                            "${it.heartRate.rate} ${it.heartRate.unit}"
                        )
                    )
                }
            }
        }

        // BMI Chart Data
        homeMenuData.bmi?.let {

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


        // All The Tools Data is drawn here
        homeMenuData.tools?.forEach {
            item {
                ToolsItemsCard(
                    title = it.title,
                    target = it.detail.toolProgress.target,
                    achieved = it.detail.toolProgress.achieved,
                    bodyDescription = it.detail.description,
                    unit = it.detail.toolProgress.unit
                ) {

                    // Going to the Track Statistics Activity
                    TrackStatisticsActivity.launch(
                        context = context,
                        title = it.title
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }


        // Steps Card Item
        homeMenuData.walking?.let {
            item {
                ToolsItemsCard(
                    title = it.title,
                    target = it.target.steps.steps.toFloat(),
                    achieved = it.achieved.steps.steps.toFloat(),
                    bodyDescription = it.description.stepsDescription,
                    unit = TrackStringConstants.STEPS_STEP_UNIT
                ) {
                    // Going to the Track Statistics Activity
                    TrackStatisticsActivity.launch(
                        context = context,
                        title = it.title
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * This function provides the UI For all the tools data fetched from the server
 *
 * @param title This denotes the title of the Card or the tools type
 * @param target This denotes the target for the Circular chart
 * @param achieved This denotes the achieved for the Circular Chart
 * @param unit This contains the Unit for the Circular Chart
 * @param bodyDescription This is the description given at the bottom of the Card beside the button
 * @param onOptionClick This function is executed when the Tool Button is clicked
 */
@Composable
private fun ToolsItemsCard(
    title: String,
    target: Float,
    achieved: Float,
    unit: String,
    bodyDescription: String,
    onOptionClick: () -> Unit
) {

    // This function draws an elevated Card View
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {

                // Title of the Card
                Text(
                    text = title[0].uppercase() + title.substring(1),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),

                    // Text Features
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                // Donut Chart
                CircularDonutChartRow.TargetDonutChart(
                    circularData = CircularTargetDataBuilder(
                        target = target,
                        achieved = achieved,
                        siUnit = unit,
                        cgsUnit = unit,
                        conversionRate = { it }
                    )
                )


                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {

                    Row(
                        modifier = Modifier.weight(.8f),
                        horizontalArrangement = Arrangement.spacedBy(spacing.medium),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        // Leading Image of this row at the bottom of the Chart
                        Image(
                            painter = painterResource(id = R.drawable.track_image_info),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),

                            contentScale = ContentScale.FillBounds
                        )

                        // Description Text
                        Text(text = bodyDescription)
                    }

                    // Trailing Image of this row at the bottom of the Chart
                    Image(
                        painter = painterResource(id = R.drawable.track_image_go),
                        contentDescription = null,

                        modifier = Modifier
                            .weight(.2f)
                            .clickable { onOptionClick() }
                    )
                }
            }
        }
    }
}