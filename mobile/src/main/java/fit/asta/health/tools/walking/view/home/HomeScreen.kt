package fit.asta.health.tools.walking.view.home

import android.util.Log
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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.R
import fit.asta.health.tools.walking.nav.StepsCounterScreen
import fit.asta.health.tools.walking.view.component.*
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import fit.asta.health.common.ui.theme.spacing
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeScreen(
    navController: NavController, homeViewModel: WalkingViewModel
) {

    androidx.compose.material3.Scaffold(topBar = {
        BottomNavigation(
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    androidx.compose.material3.IconButton(onClick = { navController.popBackStack() }) {
                        androidx.compose.material3.Icon(
                            painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null,
                            Modifier.size(24.dp)
                        )
                    }
                    androidx.compose.material3.Text(
                        text = "Step Counter",
                        fontSize = 20.sp,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
                        androidx.compose.material3.Icon(
                            painter = painterResource(id = R.drawable.ic_physique),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            elevation = 10.dp,
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
        )
    }, content = {
        StepsBottomSheet(
            navController = navController, homeViewModel = homeViewModel, paddingValues = it
        )
    })

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun StepsBottomSheet(
    navController: NavController = rememberNavController(),
    homeViewModel: WalkingViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    var visible by remember {
        mutableStateOf(false)
    }
    BottomSheetScaffold(
        sheetContent = {
            WalkingBottomSheetView(homeViewModel, visible, navController)
        },
        sheetBackgroundColor = Color.Gray,
        backgroundColor = Color.DarkGray,
        sheetPeekHeight = 200.dp,
        scaffoldState = scaffoldState
    ) {
        visible = !sheetState.isCollapsed
        HomeLayout(paddingValues = paddingValues)
    }
}

@Composable
fun HomeLayout(paddingValues: PaddingValues) {

    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp, vertical = paddingValues.calculateTopPadding()
            )
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        MainCircularSlider() { }
        StepsDetailsCard(modifier = Modifier)
        DetailsCard(modifier = Modifier)
        VitaminCard(modifier = Modifier, recommendedValue = "600", achievedValue = "500")
        Spacer(modifier = Modifier.height(200.dp))
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WalkingBottomSheetView(
    homeViewModel: WalkingViewModel, visible: Boolean, navController: NavController
) {
    val selectedGoal by homeViewModel.selectedGoal.collectAsState(emptyList())
    val selectedWalkTypes by homeViewModel.selectedWalkTypes.collectAsState("")
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        BottomSheetDragHandle()
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            CardItem(
                modifier = Modifier.weight(0.5f),
                name = "Music ",
                type = "Spotify",
                id = R.drawable.baseline_music_note_24
            ) {}

            CardItem(
                modifier = Modifier.weight(0.5f),
                name = "Types",
                type = selectedWalkTypes.let {
                it.ifBlank { "Select Types" }
            },
                id = R.drawable.baseline_merge_type_24,
                onClick = { navController.navigate(route = StepsCounterScreen.TypesScreen.route) }
            )

        }

        AnimatedVisibility(visible = visible) {
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                verticalArrangement = Arrangement.spacedBy(spacing.medium),
                columns = GridCells.Fixed(2)
            ) {

                item {
                    CardItem(name = "Goals",
                        type = if (selectedGoal.isNotEmpty()) {
                            selectedGoal[0]
                        } else {
                            "Select Goals"
                        },
                        id = R.drawable.round_filter_vintage_24,
                        onClick = { navController.navigate(route = StepsCounterScreen.GoalScreen.route) })
                }
            }
        }
        AnimatedVisibility(visible = visible) {
            SunlightCard(modifier = Modifier)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
        ) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) {

            }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Blue, text = "START"
            ) {
                homeViewModel.onUIEvent(StepCounterUIEvent.StartButtonClicked)
                navController.navigate(route = StepsCounterScreen.DistanceScreen.route)
            }
        }

    }
}

@Composable
fun MainCircularSlider(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val isDuration = remember {
        mutableStateOf(true)
    }
    Surface(
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
    ) {
        Column(
            modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularSlider(modifier = Modifier.size(160.dp),
                scaleRange = 60f,
                isDuration = isDuration.value,
                onChange = {
                    Log.d("progress", it.toString())
                },
                onChangeType = { isDuration.value = !isDuration.value })

            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                ProgressBarItem(
                    isDuration = isDuration.value,
                    modifier = Modifier.weight(0.3f),
                    value = 57,
                    name = "Recommended"
                )
                ProgressBarItem(
                    isDuration = isDuration.value,
                    modifier = Modifier.weight(0.3f),
                    value = 10,
                    name = "Goal"
                )
                ProgressBarItem(
                    isDuration = isDuration.value,
                    modifier = Modifier.weight(0.3f),
                    value = 30,
                    name = "Achieved"
                )
            }
        }
    }
}

@Composable
fun StepsDetailsCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DetailsItem(
                type = "Distance", value = "5 Km", id = R.drawable.total_distance
            ) {

            }

            DetailsItem(
                type = "Duration", value = "90 min", id = R.drawable.heartrate
            ) {

            }
            DetailsItem(
                type = "Steps", value = "1000", id = R.drawable.heartrate
            ) {

            }
        }
    }
}

@Composable
fun DetailsCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DetailsItem(
                type = "Heart Rate", value = "78 bpm", id = R.drawable.heartrate
            ) {

            }
            DetailsItem(
                type = "Calories", value = "400 kal", id = R.drawable.calories
            ) {

            }
            DetailsItem(
                type = "Weight Loosed", value = "0.4 kg", id = R.drawable.pulse_rate
            ) {

            }
            DetailsItem(
                type = "BP", value = "120/80 hhmg", id = R.drawable.pulse_rate
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
        verticalArrangement = Arrangement.spacedBy(spacing.small),
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
fun ProgressBarItem(modifier: Modifier, value: Int, name: String, isDuration: Boolean) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$value" + if (isDuration) " min" else " Km",
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
            value
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
fun VitaminCard(modifier: Modifier, recommendedValue: String, achievedValue: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
            ) {
                RowItem(name = "Recommended", value = recommendedValue)
                RowItem(name = "Achieved", value = achievedValue)
            }
            Text(
                text = "You need to take $recommendedValue IU every day to overcome your Vitamin D deficiency",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun RowItem(name: String, value: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.small),
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
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 22.dp, top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.small),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ic24_notification),
                contentDescription = null,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.small),
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