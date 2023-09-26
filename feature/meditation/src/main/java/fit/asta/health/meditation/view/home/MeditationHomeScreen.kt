package fit.asta.health.meditation.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.functional.CircularSliderInt
import fit.asta.health.designsystem.components.generic.AppBottomSheetScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.components.generic.ProgressBarInt
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.resources.drawables.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationHomeScreen(
    Event: (MEvent) -> Unit,
    uiState: HomeUiState,
    level: String,
    language: String,
    instructor: String,
    music: String,
    onClickMusic: () -> Unit,
    onClickLanguage: () -> Unit,
    onClickLevel: () -> Unit,
    onClickInstructor: () -> Unit,
    onBack: () -> Unit,
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
                title = "Meditation Tool",
                onBack = onBack,
                onHelp = { /*TODO*/ },
            )
        },
        sheetContent = {
            MeditationBottomSheet(
                uiState = uiState,
                language = language,
                level = level,
                instructor = instructor,
                music = music,
                scaffoldState = scaffoldState,
                Event = Event,
                onClickMusic = onClickMusic,
                onClickLanguage = onClickLanguage,
                onClickLevel = onClickLevel,
                onClickInstructor = onClickInstructor
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularSliderInt(
                        modifier = Modifier.size(200.dp),
                        isStarted = uiState.start,
                        appliedAngleDistanceValue = if (uiState.start) uiState.progress_angle else uiState.targetAngle,
                        indicatorValue = uiState.consume,
                        maxIndicatorValue = 120f,
                        onChangeDistance = {
                            Event(MEvent.SetTarget(it))
                        },
                        onChangeAngleDistance = {
                            Event(MEvent.SetTargetAngle(it))
                        }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)) {
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
            AnimatedVisibility(modifier = Modifier.fillMaxWidth(), visible = uiState.start) {
                ButtonWithColor(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Green,
                    text = "Go To Music Screen"
                ) { onClickMusic() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationBottomSheet(
    uiState: HomeUiState,
    level: String,
    language: String,
    instructor: String,
    music: String,
    scaffoldState: BottomSheetScaffoldState,
    Event: (MEvent) -> Unit,
    onClickMusic: () -> Unit,
    onClickLanguage: () -> Unit,
    onClickLevel: () -> Unit,
    onClickInstructor: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .heightIn(min = 250.dp, max = 525.dp)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
        verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
    ) {

        Text(text = "PRACTICE", style = MaterialTheme.typography.titleSmall)
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
            verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
            columns = GridCells.Fixed(2)
        ) {
            item {
                CardItem(
                    name = "Music ",
                    type = music,
                    id = R.drawable.baseline_music_note_24
                ) { }
            }
            item {
                CardItem(
                    name = "Instructor",
                    type = instructor,
                    id = R.drawable.baseline_merge_type_24,
                    onClick = { onClickInstructor() }
                )
            }
        }

        AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
            ) {

                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
                    verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    columns = GridCells.Fixed(2)
                ) {

                    item {
                        CardItem(name = "Level",
                            type = level,
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { onClickLevel() })
                    }
                    item {
                        CardItem(name = "Language",
                            type = language,
                            id = R.drawable.baseline_language_24,
                            onClick = { onClickLanguage() })
                    }
                    item {
                        CardItem(name = "Offline",
                            type = "0 day",
                            id = R.drawable.round_filter_vintage_24,
                            onClick = { })
                    }
                }
                SunlightCard(modifier = Modifier)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) { }
            ButtonWithColor(
                modifier = Modifier.weight(0.5f),
                color = if (!uiState.start) Color.Blue else Color.Red,
                text = if (!uiState.start) "START" else "STOP"
            ) {
                if (!uiState.start) {
                    onClickMusic()
                    Event(MEvent.Start(context))
                } else {
                    Event(MEvent.End(context))
                }
            }
        }
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
            horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ic24_notification),
                contentDescription = null,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
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