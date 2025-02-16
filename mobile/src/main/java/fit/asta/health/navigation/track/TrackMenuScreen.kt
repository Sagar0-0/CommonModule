package fit.asta.health.navigation.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.ui.util.TrackOption
import fit.asta.health.navigation.track.ui.util.TrackStringConstants
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import fit.asta.health.navigation.track.ui.viewmodel.TrackViewModel
import fit.asta.health.ui.common.AppDatePicker
import fit.asta.health.ui.common.AppTitleElevatedCard
import fit.asta.health.ui.common.AppTopTabBar
import fit.asta.health.ui.track.TrackingDetailsCard
import java.text.DecimalFormat
import java.time.LocalDate
import kotlin.math.abs

@Composable
fun TrackMenuScreenControl() {

    val trackViewModel: TrackViewModel = hiltViewModel()

    // Home Menu State
    val homeMenuState = trackViewModel.homeScreenDetails
        .collectAsStateWithLifecycle().value

    // Calendar Chosen Date Data from the View Model
    val localDate = trackViewModel.calendarData
        .collectAsStateWithLifecycle().value

    val setUiEvent = trackViewModel::uiEventListener

    // This is the Item which is selected in the Top Tab Bar Layout
    val selectedItem = remember { mutableIntStateOf(0) }

    // This function loads the data for the Home Menu Screen
    LaunchedEffect(Unit) {
        setUiEvent(TrackUiEvent.SetTrackOption(TrackOption.HomeMenuOption))
        setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
    }

    // Drag Flags to keep a check of how the user has dragged
    val isLeftDrag = remember { mutableStateOf(false) }
    val isRightDrag = remember { mutableStateOf(false) }

    val tabList = listOf("DAY", "WEEK", "MONTH", "YEAR")

    AppScreen {
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
                            if (isRightDrag.value && selectedItem.intValue > 0 && homeMenuState !is UiState.Loading) {

                                // Checking which tab option is selected by the User and showing the UI Accordingly
                                selectedItem.intValue = selectedItem.intValue - 1
                                setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))

                                // Resetting the drag flags
                                isRightDrag.value = false
                            }

                            // If the drag is a left drag
                            if (isLeftDrag.value && selectedItem.intValue < tabList.size - 1 && homeMenuState !is UiState.Loading) {

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

                if (selectedItem.intValue != it && homeMenuState !is UiState.Loading) {

                    // Checking which tab option is selected by the User and showing the UI Accordingly
                    selectedItem.intValue = it
                    setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
                }
            }

            // Date Picker
            AppDatePicker(
                localDate = localDate,
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

            // This conditional handles the State of the Api call and shows the UI according to the state
            when (homeMenuState) {

                // Initialized State
                is UiState.Idle -> {
                    setUiEvent(TrackUiEvent.SetTrackOption(TrackOption.HomeMenuOption))
                    setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
                }

                // Loading State
                is UiState.Loading -> {
                    AppDotTypingAnimation(modifier = Modifier.fillMaxSize())
                }

                // Success State
                is UiState.Success -> {
                    TrackMenuSuccessScreen(
                        homeMenuData = homeMenuState.data,
                        localDate = localDate
                    )
                }

                // failure State
                is UiState.ErrorMessage -> {
                    AppErrorScreen(
                        text = homeMenuState.resId.toStringFromResId()
                    ) {
                        setUiEvent(TrackUiEvent.SetTrackOption(TrackOption.HomeMenuOption))
                        setUiEvent(TrackUiEvent.SetTrackStatus(selectedItem.intValue))
                    }
                }

                else -> {}
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
 */
@Composable
private fun TrackMenuSuccessScreen(
    homeMenuData: HomeMenuResponse,
    localDate: LocalDate
) {

    val context = LocalContext.current

    // Parent UI for all the composable
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

        // Time Spent Chart Card
        homeMenuData.timeSpent?.let {
            item {
                AppTitleElevatedCard(title = TrackStringConstants.TIME_SPENT) {
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
                AppTitleElevatedCard(title = TrackStringConstants.HEART_HEALTH) {
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
                AppTitleElevatedCard {
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
                        TitleTexts.Level2(
                            text = text,

                            modifier = Modifier
                                .padding(horizontal = 16.dp),

                            maxLines = 2
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
                        title = it.title,
                        localDate = localDate
                    )
                }
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
                        title = it.title,
                        localDate = localDate
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
    AppElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            // Title of the Card
            HeadingTexts.Level3(
                text = title[0].uppercase() + title.substring(1),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),

                // Text Feature
                textAlign = TextAlign.Start
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
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {

                Row(
                    modifier = Modifier.weight(.8f),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
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
                    TitleTexts.Level2(text = bodyDescription)
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