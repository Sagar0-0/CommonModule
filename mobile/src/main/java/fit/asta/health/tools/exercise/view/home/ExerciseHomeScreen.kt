package fit.asta.health.tools.exercise.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.common.ui.components.CircularSliderInt
import fit.asta.health.common.ui.components.ProgressBarInt
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.exercise.view.components.CardItem
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.common.ui.components.ButtonWithColor
import fit.asta.health.tools.walking.view.home.SunlightCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseHomeScreen(
    screen: String,
    uiState: ExerciseUiState,
    event: (HomeEvent) -> Unit,
    style: String,
    duration: String,
    equipment: String,
    music: String,
    level: String,
    quickDance: String,
    bodyParts: String,
    challenges: String,
    bodyStretch: String,
    onStyle: () -> Unit,
    onLevel: () -> Unit,
    onQuickDance: () -> Unit,
    onBodyParts: () -> Unit,
    onBodyStretch: () -> Unit,
    onChallenges: () -> Unit,
    onEquipment: () -> Unit,
    onDuration: () -> Unit,
    onMusic: () -> Unit,
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
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            DanceBottomSheet(
                scaffoldState = scaffoldState,
                screen=screen,
                style = style,
                music = music,
                level = level,
                equipment = equipment,
                duration = duration,
                quickDance = quickDance,
                bodyParts = bodyParts,
                challenges = challenges,
                bodyStretch = bodyStretch,
                onStyle = onStyle,
                onLevel = onLevel,
                onQuickDance = onQuickDance,
                onBodyParts = onBodyParts,
                onBodyStretch = onBodyStretch,
                onChallenges = onChallenges,
                onEquipment = onEquipment,
                onDuration = onDuration,
                onMusic = onMusic,
                )
        },
        sheetPeekHeight = 200.dp,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(elevation = 10.dp, backgroundColor = MaterialTheme.colorScheme.onPrimary) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null,
                            Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "$screen Exercise Tool",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_physique),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CircularSliderInt(
                        modifier = Modifier.size(200.dp),
                        isStarted = uiState.start,
                        appliedAngleDistanceValue = if (uiState.start) uiState.progress_angle else uiState.targetAngle,
                        indicatorValue = uiState.consume,
                        maxIndicatorValue = 120f,
                        onChangeDistance = {
                            event(HomeEvent.SetTarget(it))
                        },
                        onChangeAngleDistance = {
                            event(HomeEvent.SetTargetAngle(it))
                        }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                        ProgressBarInt(
                            modifier = Modifier.weight(0.3f),
                            targetDistance = uiState.recommended.toFloat(),
                            progress = (uiState.consume / uiState.recommended),
                            name = "Recommended",
                            postfix = "min"
                        )
                        ProgressBarInt(
                            modifier = Modifier.weight(0.3f),
                            targetDistance = uiState.target.toFloat(),
                            progress = if (uiState.target == 0) 0f else (uiState.consume / uiState.target),
                            name = "Goal",
                            postfix = "min"
                        )
                        ProgressBarInt(
                            modifier = Modifier.weight(0.3f),
                            targetDistance = uiState.target.toFloat(),
                            progress = if (uiState.target == 0) 0f else 1f - (uiState.consume / uiState.target),
                            name = "Remaining",
                            postfix = "min"
                        )
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DanceBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    screen: String,
    style: String,
    music: String,
    duration: String,
    equipment: String,
    level: String,
    quickDance: String,
    bodyParts: String,
    challenges: String,
    bodyStretch: String,
    onStyle: () -> Unit,
    onMusic: () -> Unit,
    onLevel: () -> Unit,
    onQuickDance: () -> Unit,
    onBodyParts: () -> Unit,
    onBodyStretch: () -> Unit,
    onChallenges: () -> Unit,
    onDuration: () -> Unit,
    onEquipment: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.background(Color.Yellow)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {

        DividerLineCenter()
        Text(text = "PRACTICE", style = MaterialTheme.typography.titleSmall)
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(spacing.small),
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            columns = GridCells.Fixed(2)
        ) {
            item {  CardItem(
                name = "Style",
                type = style,
                id = R.drawable.baseline_music_note_24
            ) { onStyle() } }
            item {  CardItem(
                name = "Music",
                type = music,
                id = R.drawable.baseline_music_note_24,
                onClick = { onMusic() }
            ) }
        }
        AnimatedVisibility(visible = scaffoldState.bottomSheetState.isExpanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {

                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(spacing.small),
                    verticalArrangement = Arrangement.spacedBy(spacing.medium),
                    columns = GridCells.Fixed(2)
                ) {

                    item {
                        CardItem(name = "Duration",
                            type = duration,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onDuration() })
                    }

                    item {
                        CardItem(name = "Level",
                            type = level,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onLevel() })
                    }
                    item {
                        CardItem(name = "Quick $screen",
                            type = quickDance,
                            id = R.drawable.baseline_language_24,
                            onClick = { onQuickDance() })
                    }
                    item {
                        CardItem(name = "Body Parts",
                            type = bodyParts,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onBodyParts() })
                    }
                    item {
                        CardItem(name = "Challenges",
                            type = challenges,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onChallenges() })
                    }
                    item {
                        CardItem(name = "Body Stretch",
                            type = bodyStretch,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onBodyStretch() })
                    }
                    if (screen == "HIIT") {
                        item {
                            CardItem(name = "Equipments",
                                type = equipment,
                                id = R.drawable.round_filter_vintage_24,
                                onClick = { onEquipment() })
                        }
                    }
                }
                SunlightCard(modifier = Modifier)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) { }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = Color.Blue,
                text = "START"
            ) { }
        }
    }
}