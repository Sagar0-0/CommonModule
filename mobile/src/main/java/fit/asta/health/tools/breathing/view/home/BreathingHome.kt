package fit.asta.health.tools.breathing.view.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.common.ui.components.ButtonWithColor
import fit.asta.health.common.ui.components.CircularSliderInt
import fit.asta.health.common.ui.components.ProgressBarInt
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.exercise.view.components.CardItem
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.walking.view.home.SunlightCard


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BreathingHomeScreen(
    uiState: UiState,
    exercise: List<String>,
    goals: List<String>,
    music: String,
    pace: String,
    break_time: String,
    level: String,
    language: String,
    instructor: String,
    onExercise: () -> Unit,
    onMusic: () -> Unit,
    onLevel: () -> Unit,
    onLanguage: () -> Unit,
    onPace: () -> Unit,
    onBreakTime: () -> Unit,
    onGoals: () -> Unit,
    onInstructor: () -> Unit,
    onSchedule: () -> Unit,
    onPlayer: () -> Unit,
    event: (UiEvent) -> Unit
) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            BreathingBottomSheet(
                scaffoldState = scaffoldState,
                start = uiState.start,
                music = music,
                pace = pace,
                break_time = break_time,
                goals = goals,
                instructor = instructor,
                language = language,
                exercise = exercise,
                level = level,
                onPlayer = onPlayer,
                onSchedule = onSchedule,
                onMusic = onMusic,
                onLevel = onLevel,
                onBreakTime = onBreakTime,
                onExercise = onExercise,
                onGoals = onGoals,
                onInstructor = onInstructor,
                onLanguage = onLanguage,
                onPace = onPace,
                event = event
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
                        text = "Breathing Tool",
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
                            event(UiEvent.SetTarget(it))
                        },
                        onChangeAngleDistance = {
                            event(UiEvent.SetTargetAngle(it))
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
fun BreathingBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    start: Boolean,
    exercise: List<String>,
    goals: List<String>,
    music: String,
    pace: String,
    break_time: String,
    level: String,
    language: String,
    instructor: String,
    onExercise: () -> Unit,
    onMusic: () -> Unit,
    onLevel: () -> Unit,
    onLanguage: () -> Unit,
    onPace: () -> Unit,
    onBreakTime: () -> Unit,
    onGoals: () -> Unit,
    onInstructor: () -> Unit,
    onSchedule: () -> Unit,
    onPlayer: () -> Unit,
    event: (UiEvent) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(Color.Yellow)
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
            item {
                CardItem(
                    name = "Exercises",
                    type = exercise[0],
                    id = R.drawable.baseline_music_note_24
                ) { onExercise() }
            }
            item {
                CardItem(
                    name = "Pace",
                    type = pace,
                    id = R.drawable.baseline_music_note_24,
                    onClick = { onPace() }
                )
            }
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
                        CardItem(name = "Level",
                            type = level,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onLevel() })
                    }
                    item {
                        CardItem(name = "Music",
                            type = music,
                            id = R.drawable.baseline_language_24,
                            onClick = { onMusic() })
                    }
                    item {
                        CardItem(name = "Language",
                            type = language,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onLanguage() })
                    }
                    item {
                        CardItem(name = "Break Time",
                            type = break_time,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onBreakTime() })
                    }
                    item {
                        CardItem(name = "Goals",
                            type = goals[0],
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onGoals() })
                    }
                    item {
                        CardItem(name = "Instructor",
                            type = instructor,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onInstructor() })
                    }
                }
                SunlightCard(modifier = Modifier)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) { onSchedule() }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = if (start) Color.Red else Color.Blue,
                text = if (start) "END" else "START"
            ) {
                if (start) {
                    event(UiEvent.End(context))
                } else {
                    event(UiEvent.Start(context))
                    onPlayer()
                }
            }
        }
    }
}