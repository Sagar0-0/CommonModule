package fit.asta.health.feature.exercise.view.home

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.token.AppSheetValue
import fit.asta.health.designsystem.atomic.token.checkState
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.CardItem
import fit.asta.health.designsystem.molecular.CircularSliderInt
import fit.asta.health.designsystem.molecular.ProgressBarInt
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppSheetState
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.appRememberBottomSheetScaffoldState
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

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

    val scaffoldState = appRememberBottomSheetScaffoldState(bottomSheetState = AppSheetState(
        initialValue = AppSheetValue.PartiallyExpanded
    ))

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
                animatedState = checkState(scaffoldState),
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
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            AppSurface(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CircularSliderInt(
                        modifier = Modifier.size(200.dp),
                        isStarted = uiState.start,
                        appliedAngleDistanceValue = if (uiState.start) uiState.progressAngle else uiState.targetAngle,
                        indicatorValue = uiState.consume,
                        maxIndicatorValue = 120f,
                        onChangeDistance = {
                            event(HomeEvent.SetTarget(it))
                        },
                        onChangeAngleDistance = {
                            event(HomeEvent.SetTargetAngle(it))
                        }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
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

@Composable
fun DanceBottomSheet(
    animatedState : Boolean,
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
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {

        TitleTexts.Level3(text = "PRACTICE")
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
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

        AnimatedVisibility(visible = animatedState) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {

                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
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

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
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

@Composable
fun SunlightCard(modifier: Modifier) {
    val checked = remember { mutableStateOf(true) }
    AppCard(
        modifier = modifier,
     //   colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    painter = painterResource(id = R.drawable.ic_ic24_notification),
                    contentDescription = null,
                )
                BodyTexts.Level2(text = "Sunlight")
                AppSwitch(
                    checked = checked.value,
                    modifier = Modifier.weight(0.5f),
                ) { checked.value = it }
            }
            CaptionTexts.Level3(
                maxLines = 3,
                text = "There will be addition of 500 ml to 1 Litre of water to your daily intake based on the weather temperature.",
            )
        }
    }
}