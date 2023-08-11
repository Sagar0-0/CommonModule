package fit.asta.health.navigation.track.view.screens


import android.widget.Toast
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
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartColumn
import com.dev.anirban.chartlibrary.circular.charts.CircularDonutChartRow
import com.dev.anirban.chartlibrary.circular.data.CircularDonutListData
import com.dev.anirban.chartlibrary.circular.data.CircularTargetDataBuilder
import com.dev.anirban.chartlibrary.circular.decoration.CircularDecoration
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.track.model.net.menu.HomeMenuResponse
import fit.asta.health.navigation.track.view.components.TrackingChartCard
import fit.asta.health.navigation.track.view.components.TrackingDetailsCard
import fit.asta.health.navigation.track.view.navigation.TrackNavRoute
import fit.asta.health.navigation.track.view.util.TrackOption
import fit.asta.health.navigation.track.view.util.TrackingNetworkCall

/**
 * This Screen shows all the Statistics Options that are there in the App for the user to choose
 * the specific Option they want to see
 *
 * @param homeMenuState This variable contains the state of the Home Menu Api call
 * @param loadHomeData This is the function which fetches the data from the server
 * @param navigator Controller which lets us navigate to a different Screen
 * @param setTrackOption This option sets the Track Option which is selected by the user
 */
@Composable
fun TrackMenuScreenControl(
    homeMenuState: TrackingNetworkCall<HomeMenuResponse>,
    loadHomeData: () -> Unit,
    setTrackOption: (TrackOption) -> Unit,
    navigator: (String) -> Unit
) {

    // Context Variable
    val context = LocalContext.current

    // This function loads the data for the Home Menu Screen
    LaunchedEffect(Unit) {
        loadHomeData()
    }

    // This conditional handles the State of the Api call and shows the UI according to the state
    when (homeMenuState) {

        // Initialized State
        is TrackingNetworkCall.Initialized -> {}

        // Loading State
        is TrackingNetworkCall.Loading -> {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        // Success State
        is TrackingNetworkCall.Success -> {
            if (homeMenuState.data != null)
                TrackMenuSuccessScreen(
                    homeMenuData = homeMenuState.data.homeMenuData,
                    setTrackOption = setTrackOption,
                    navigator = navigator
                )
            else
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
        }

        // failure State
        is TrackingNetworkCall.Failure -> {
            Toast.makeText(context, homeMenuState.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * This function Provides the UI for the Home Menu Screen when the api state is Success i.e the data
 * is fetched from the server successfully
 *
 * @param homeMenuData This function contains the data of the response of the Api call for the home
 * menu state
 * @param setTrackOption This function is used to set the track Option i.e Water , breathing , steps,
 * meditation etc
 * @param navigator This function is used to navigate from one screen to another
 */
@Composable
private fun TrackMenuSuccessScreen(
    homeMenuData: HomeMenuResponse.HomeMenuData,
    setTrackOption: (TrackOption) -> Unit,
    navigator: (String) -> Unit
) {

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

        // Time Spent Chart Card
        homeMenuData.timeSpent?.let {
            item {
                TrackingChartCard(title = "Time Spent") {
                    CircularDonutChartColumn.DonutChartColumn(
                        circularData = CircularDonutListData(
                            itemsList = listOf(
                                Pair("Meditation", it.meditation),
                                Pair("Steps", it.steps),
                                Pair("Sleep", it.sleep),
                                Pair("Sunlight", it.sunlight),
                                Pair("Breathing", it.breathing),
                                Pair("Water", it.water)
                            ),
                            siUnit = "Min",
                            cgsUnit = "Min",
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
                TrackingChartCard(title = "Heart Health") {
                    TrackingDetailsCard(
                        imageList = listOf(R.drawable.heartrate, R.drawable.pulse_rate),
                        headerTextList = listOf("Blood Pressure", "Heart Rate"),
                        valueList = listOf(
                            "${it.bloodPressure.mm}/${it.bloodPressure.hg} ${it.bloodPressure.unit}",
                            "${it.heartRate.rate} ${it.heartRate.unit}"
                        )
                    )
                }
            }
        }


        // TODO :- BMI Chart Details Card


        // All The Tools Data is drawn here
        homeMenuData.tools?.forEach {
            item {
                ToolsItemsCard(
                    title = it.title,
                    target = it.target,
                    achieved = it.achieved,
                    bodyDescription = it.description
                ) {

                    // checking the title before redirecting the user to the screen and setting it
                    when (it.title) {

                        "breathing" -> {
                            setTrackOption(TrackOption.BreathingOption)
                            navigator(TrackNavRoute.BreathingTrackDetail.route)
                        }

                        "meditation" -> {
                            setTrackOption(TrackOption.MeditationOption)
                            navigator(TrackNavRoute.MeditationTrackDetail.route)
                        }

                        "sleep" -> {
                            setTrackOption(TrackOption.SleepOption)
                            navigator(TrackNavRoute.SleepTrackDetail.route)
                        }

                        "sunlight" -> {
                            setTrackOption(TrackOption.SunlightOption)
                            navigator(TrackNavRoute.SunlightTrackDetail.route)
                        }

                        "yoga" -> {
                            // TODO :- No UI for it given
                        }

                        "dance" -> {
                            // TODO :- No UI for it given
                        }

                        "workout" -> {
                            // TODO :- No UI for it given
                        }

                        "hiit" -> {
                            // TODO :- No UI for it given
                        }
                    }
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
 * @param siUnit This contains the SI Unit for the Circular Chart
 * @param cgsUnit This contains the cgs Unit for the Circular Chart
 * @param bodyDescription This is the description given at the bottom of the Card beside the button
 * @param onOptionClick This function is executed when the Tool Button is clicked
 */
@Composable
private fun ToolsItemsCard(
    title: String,
    target: Float,
    achieved: Float,
    siUnit: String = "",
    cgsUnit: String = "",
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
                    text = title,

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
                        siUnit = siUnit,
                        cgsUnit = cgsUnit,
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