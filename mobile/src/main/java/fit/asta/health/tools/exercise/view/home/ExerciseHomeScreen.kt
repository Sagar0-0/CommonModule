package fit.asta.health.tools.exercise.view.home

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.functional.CircularSliderInt
import fit.asta.health.designsystem.components.generic.AppBottomSheetScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.components.generic.ProgressBarInt
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.ButtonWithColor
import fit.asta.health.designsystem.components.CardItem
import fit.asta.health.tools.walking.view.home.SunlightCard

@OptIn(ExperimentalMaterial3Api::class)
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
    onSchedule: () -> Unit,
    onPlayer: () -> Unit,
    onBack: () -> Unit,
) {

    val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    AppBottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 240.dp,
        topBar = {
            AppTopBarWithHelp(
                title = "$screen Exercise Tool",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        },
        sheetContent = {
            DanceBottomSheet(
                scaffoldState = scaffoldState,
                start = uiState.start,
                screen = screen,
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
                onSchedule = onSchedule,
                onPlayer = onPlayer,
                event = event
            )
        },
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
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
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
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
                    Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)) {
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
fun DanceBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    start: Boolean,
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
    onSchedule: () -> Unit,
    onPlayer: () -> Unit,
    event: (HomeEvent) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)
    ) {

        Text(text = "PRACTICE", style = MaterialTheme.typography.titleSmall)
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
            columns = GridCells.Fixed(2)
        ) {
            item {
                CardItem(
                    name = "Style",
                    type = style,
                    id = R.drawable.baseline_music_note_24
                ) { onStyle() }
            }
            item {
                CardItem(
                    name = "Music",
                    type = music,
                    id = R.drawable.baseline_music_note_24,
                    onClick = { onMusic() }
                )
            }
        }

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
            ) {

                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
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

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) { onSchedule() }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = if (start) Color.Red else Color.Blue,
                text = if (start) "END" else "START"
            ) {
                if (start) {
                    event(HomeEvent.End(context))
                } else {
                    event(HomeEvent.Start(context))
                    onPlayer()
                }
            }
        }
    }
}