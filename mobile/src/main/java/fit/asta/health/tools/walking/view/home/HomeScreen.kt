package fit.asta.health.tools.walking.view.home

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppBottomSheetScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.ButtonWithColor
import fit.asta.health.designsystem.components.CardItem
import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.nav.StepsCounterScreen
import fit.asta.health.tools.walking.view.component.*
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StepsHomeScreen(
    navController: NavController,
    homeViewModel: WalkingViewModel
) {

    val state = homeViewModel.homeUiState.value
    val apiState = homeViewModel.ApiState.value
    val startWorking = homeViewModel.startWorking
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    AppBottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 240.dp,
        topBar = {
            AppTopBarWithHelp(
                title = "Steps Tool",
                onBack = { navController.popBackStack() },
                onHelp = { /*TODO*/ }
            )
        },
        sheetContent = {
            WalkingBottomSheetView(
                homeViewModel,
                scaffoldState,
                navController,
                apiState,
                startWorking
            )
        }
    ) {
        HomeLayout(
            state = state, apiState = apiState,
            onTargetDistance = { homeViewModel.onUIEvent(StepCounterUIEvent.ChangeTargetDistance(it)) },
            onTargetDuration = { homeViewModel.onUIEvent(StepCounterUIEvent.ChangeTargetDuration(it)) },
            onChangeAngleDistance = {
                homeViewModel.onUIEvent(
                    StepCounterUIEvent.ChangeAngleDistance(
                        it
                    )
                )
            },
            onChangeAngleDuration = {
                homeViewModel.onUIEvent(
                    StepCounterUIEvent.ChangeAngelDuration(
                        it
                    )
                )
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeLayout(
    state: HomeUIState,
    apiState: WalkingTool,
    onTargetDistance: (Float) -> Unit,
    onTargetDuration: (Float) -> Unit,
    onChangeAngleDistance: (Float) -> Unit,
    onChangeAngleDuration: (Float) -> Unit
) {
    val scrollState = rememberScrollState()
    var isScrollEnabled by remember(state.start) { mutableStateOf(state.start) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState, enabled = isScrollEnabled),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        MainCircularSlider(
            apiState = apiState,
            state = state,
            onTargetDistance = onTargetDistance,
            onTargetDuration = onTargetDuration,
            onChangeAngleDuration = onChangeAngleDuration,
            onChangeAngleDistance = onChangeAngleDistance,
            onScroll = { isScrollEnabled = it }
        )
        StepsDetailsCard(
            modifier = Modifier
                .pointerInteropFilter {
                    isScrollEnabled = true
                    return@pointerInteropFilter true
                },
            distance = state.distance,
            duration = state.duration,
            steps = state.steps
        )
        DetailsCard(
            modifier = Modifier,
            heartRate = state.heartRate,
            calories = state.calories,
            weightLoosed = state.weightLoosed,
            bp = state.bp
        )
        VitaminCard(
            modifier = Modifier,
            recommendedValue = state.recommendedSunlight,
            achievedValue = state.achievedSunlight
        )
        Spacer(modifier = Modifier.height(200.dp))
    }
}


@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WalkingBottomSheetView(
    homeViewModel: WalkingViewModel,
    scaffoldState: BottomSheetScaffoldState,
    navController: NavController,
    apiState: WalkingTool,
    startWorking: MutableState<Boolean>
) {
    val selectedGoal by homeViewModel.selectedGoal.collectAsState(emptyList())
    val selectedWalkTypes by homeViewModel.selectedWalkTypes.collectAsState("")
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxHeight(.5f)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
            columns = GridCells.Fixed(2)
        ) {
            item {
                CardItem(
                    name = "Music ",
                    type = apiState.titleMusic,
                    id = R.drawable.baseline_music_note_24
                ) {}
            }
            item {
                CardItem(
                    name = "Types",
                    type = selectedWalkTypes.let {
                        it.ifBlank { apiState.valuesType }
                    },
                    id = R.drawable.baseline_merge_type_24,
                    onClick = { navController.navigate(route = StepsCounterScreen.TypesScreen.route) }
                )
            }
        }

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
            ) {
                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
                    columns = GridCells.Fixed(2)
                ) {

                    item {
                        CardItem(name = "Goals",
                            type = if (selectedGoal.isNotEmpty()) {
                                selectedGoal[0]
                            } else {
                                apiState.valuesGoal[0]
                            },
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { navController.navigate(route = StepsCounterScreen.GoalScreen.route) })
                    }
                }

                SunlightCard(modifier = Modifier)
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
        ) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) {
                homeViewModel.startSchedulerActivity(context = context)
            }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = if (!startWorking.value) {
                    Color.Blue
                } else {
                    Color.Red
                },
                text = if (!startWorking.value) {
                    "START"
                } else {
                    "END"
                }
            ) {
                if (startWorking.value) {
                    homeViewModel.onUIEvent(StepCounterUIEvent.StopButtonClicked)
                    homeViewModel.stopService(activity)
                    navController.navigate(route = StepsCounterScreen.DistanceScreen.route)
                } else {
                    homeViewModel.onUIEvent(StepCounterUIEvent.StartButtonClicked)
                    homeViewModel.startService(activity)
                }
            }
        }

    }
}


@Composable
fun MainCircularSlider(
    modifier: Modifier = Modifier,
    apiState: WalkingTool,
    state: HomeUIState,
    onTargetDistance: (Float) -> Unit,
    onTargetDuration: (Float) -> Unit,
    onChangeAngleDistance: (Float) -> Unit,
    onChangeAngleDuration: (Float) -> Unit,
    onScroll: (Boolean) -> Unit
) {

    var isDuration by remember {
        mutableStateOf(true)
    }
    var maxIndicatorValue by remember {
        mutableStateOf(120f)
    }
    var bigTextSuffix by remember {
        mutableStateOf("min")
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
    ) {
        Column(
            modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularSlider(
                modifier = Modifier.size(200.dp),
                isDuration = isDuration,
                isStarted = state.start,
                appliedAngleDurationValue = state.appliedAngleDuration,
                appliedAngleDistanceValue = state.appliedAngleDistance,
                maxIndicatorValue = maxIndicatorValue,
                indicatorValue = if (isDuration) state.durationProgress.toFloat() else state.distance.toFloat(),
                bigTextSuffix = bigTextSuffix,
                onChangeType = {
                    isDuration = !isDuration
                    if (isDuration) {
                        maxIndicatorValue = 120f
                        bigTextSuffix = "min"
                    } else {
                        maxIndicatorValue = 3.0f
                        bigTextSuffix = "km"
                    }
                },
                onChangeDuration = onTargetDuration,
                onChangeDistance = onTargetDistance,
                onChangeAngleDuration = onChangeAngleDuration,
                onChangeAngelDistance = onChangeAngleDistance,
                onScroll = onScroll
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
            ) {
                ProgressBarItem(
                    isDuration = isDuration,
                    modifier = Modifier.weight(0.3f),
                    targetDistance = apiState.distanceRecommend,
                    targetDuration = apiState.durationRecommend,
                    valueDistance = state.valueDistanceRecommendation.toInt(),
                    valueDuration = state.valueDurationRecommendation,
                    name = "Recommended"
                )
                ProgressBarItem(
                    isDuration = isDuration,
                    modifier = Modifier.weight(0.3f),
                    targetDistance = apiState.distanceTarget,
                    targetDuration = apiState.durationTarget,
                    valueDistance = state.valueDistanceGoal.toInt(),
                    valueDuration = state.valueDurationGoal,
                    name = "Goal"
                )
                ProgressBarItem(
                    isDuration = isDuration,
                    modifier = Modifier.weight(0.3f),
                    targetDistance = apiState.distanceTarget,
                    targetDuration = apiState.durationTarget,
                    valueDistance = 100 - state.valueDistanceGoal.toInt(),
                    valueDuration = 100 - state.valueDurationGoal,
                    name = "Remaining"
                )
            }
        }
    }
}


@Composable
fun StepsDetailsCard(modifier: Modifier, distance: Double, duration: Int, steps: Int) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DetailsItem(
                type = "Distance",
                value = "%.2f km".format(distance),
                id = R.drawable.total_distance
            ) {

            }

            DetailsItem(
                type = "Duration", value = "$duration min", id = R.drawable.heartrate
            ) {

            }
            DetailsItem(
                type = "Steps", value = "$steps", id = R.drawable.heartrate
            ) {

            }
        }
    }
}


@Composable
fun DetailsCard(
    modifier: Modifier,
    heartRate: Int,
    calories: Int,
    weightLoosed: Double,
    bp: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DetailsItem(
                type = "Heart Rate", value = "$heartRate bpm", id = R.drawable.heartrate
            ) {

            }
            DetailsItem(
                type = "Calories", value = "$calories kal", id = R.drawable.calories
            ) {

            }
            DetailsItem(
                type = "Weight Loosed",
                value = "%.1f kg".format(weightLoosed),
                id = R.drawable.pulse_rate
            ) {

            }
            DetailsItem(
                type = "BP", value = "$bp hhmg", id = R.drawable.pulse_rate
            ) {

            }
        }
    }
}


@Composable
fun DetailsItem(
    modifier: Modifier = Modifier,
    type: String,
    value: String,
    @DrawableRes id: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id),
            contentDescription = null
        )
        Text(text = type, fontSize = 12.sp)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun ProgressBarItem(
    modifier: Modifier,
    valueDistance: Int,
    valueDuration: Int,
    targetDistance: Double,
    targetDuration: Int,
    name: String,
    isDuration: Boolean
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isDuration) "$targetDuration min"
            else "%.1f km".format(targetDistance),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        CustomProgressBar(
            Modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .height(4.dp),
            100.dp,
            Color.Gray,
            Brush.horizontalGradient(listOf(Color(0xffFD7D20), Color(0xffFBE41A))),
            percent = if (isDuration) valueDuration else valueDistance,
        )
        Text(
            text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun CustomProgressBar(
    modifier: Modifier, width: Dp, backgroundColor: Color, foregroundColor: Brush, percent: Int
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .width(width)
    ) {
        Box(
            modifier = modifier
                .background(foregroundColor)
                .width(width * percent / 100)
        )
    }
}


@Composable
fun VitaminCard(modifier: Modifier, recommendedValue: Int, achievedValue: Int) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
            ) {
                RowItem(name = "Recommended", value = "$recommendedValue")
                RowItem(name = "Achieved", value = "$achievedValue")
            }
            Text(
                text = "You need to take $recommendedValue IU every day to overcome your Vitamin D deficiency",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun RowItem(name: String, value: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, fontSize = 12.sp)
        Text(text = "$value IU", fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun SunlightCard(modifier: Modifier) {
    val checked = remember { mutableStateOf(true) }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 22.dp, top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ic24_notification),
                contentDescription = null,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Sunlight", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "There will be addition of 500 ml to 1 Litre of water to your daily intake based on the weather temperature.",
                    fontSize = 12.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Switch(
                    checked = checked.value,
                    onCheckedChange = { checked.value = it },
                )
            }

        }
    }
}