package fit.asta.health.tools.breathing.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.CircularSliderInt
import fit.asta.health.designsystem.molecular.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.AppTopBar
import fit.asta.health.designsystem.molecular.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.CardItem
import fit.asta.health.designsystem.molecular.ProgressBarInt
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.tools.walking.view.home.SunlightCard


@OptIn(ExperimentalMaterial3Api::class)
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
    event: (UiEvent) -> Unit,onBack:()->Unit
) {

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
                title = "Breathing Tool",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        },
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
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            AppSurface(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
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
                    Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)) {
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

@OptIn(ExperimentalMaterial3Api::class)
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
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        TitleTexts.Level3(text = "PRACTICE")
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
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

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Test() {
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
        sheetPeekHeight = 200.dp,
        topBar = {
            AppTopBar(title = "Breathing Tool")
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                TitleTexts.Level3(text = "PRACTICE")
                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
                    columns = GridCells.Fixed(2)
                ) {
                    item {
                        CardItem(
                            name = "Exercises",
                            type =" exercise[0]",
                            id = R.drawable.baseline_music_note_24
                        ) { }
                    }
                    item {
                        CardItem(
                            name = "Pace",
                            type = "pace",
                            id = R.drawable.baseline_music_note_24,
                            onClick = {  }
                        )
                    }
                }
                AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue==SheetValue.Expanded) {
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
                                CardItem(name = "Level",
                                    type = "level",
                                    id = R.drawable.round_filter_vintage_24,
                                    onClick = {  })
                            }
                            item {
                                CardItem(name = "Music",
                                    type = "music",
                                    id = R.drawable.baseline_language_24,
                                    onClick = {  })
                            }
                            item {
                                CardItem(name = "Language",
                                    type = "language",
                                    id = R.drawable.round_filter_vintage_24,
                                    onClick = {  })
                            }
                            item {
                                CardItem(name = "Break Time",
                                    type = "break_time",
                                    id = R.drawable.round_filter_vintage_24,
                                    onClick = {  })
                            }
                            item {
                                CardItem(name = "Goals",
                                    type =" goals[0",
                                    id = R.drawable.round_filter_vintage_24,
                                    onClick = {  })
                            }
                            item {
                                CardItem(name = "Instructor",
                                    type = "instructor",
                                    id = R.drawable.round_filter_vintage_24,
                                    onClick = {  })
                            }
                        }
                        SunlightCard(modifier = Modifier)
                    }
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTexts.Level2(
                text = "Breathing Tool",
                color = AppTheme.colors.onBackground,
                textAlign = TextAlign.Center,
            )
        }
    }
}