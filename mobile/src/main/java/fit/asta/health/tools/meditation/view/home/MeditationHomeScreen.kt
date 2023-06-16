package fit.asta.health.tools.meditation.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.meditation.view.component.MeditationCircularSlider
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.walking.view.component.ButtonWithColor
import fit.asta.health.tools.walking.view.component.CardItem
import fit.asta.health.tools.walking.view.home.SunlightCard
import fit.asta.health.tools.water.view.screen.ProgressBarItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MeditationHomeScreen(
    Event: (MEvent) -> Unit,
    uiState: HomeUiState,
    level:String,
    language:String,
    instructor:String,
    onClickMusic: () -> Unit,
    onClickLanguage: () -> Unit,
    onClickLevel: () -> Unit,
    onClickInstructor: () -> Unit,
) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {

    }
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            MeditationBottomSheet(
                uiState = uiState,
                language = language,
                level = level,
                instructor = instructor,
                scaffoldState = scaffoldState,
                Event = Event,
                onClickMusic = onClickMusic,
                onClickLanguage = onClickLanguage,
                onClickLevel = onClickLevel,
                onClickInstructor = onClickInstructor
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
                        text = "Meditation Tool",
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
       Column(modifier = Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.spacedBy(spacing.medium),
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
                   verticalArrangement = Arrangement.spacedBy(spacing.medium),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   MeditationCircularSlider(
                       modifier = Modifier.size(200.dp),
                       isStarted = uiState.start,
                       appliedAngleDistanceValue = if (uiState.start) uiState.angle else uiState.targetAngle,
                       indicatorValue = uiState.consume,
                       maxIndicatorValue = 120f,
                       onChangeDistance = {
                           Event(MEvent.setTarget(it))
                           
                       },
                       onChangeAngleDistance = {
                           Event(MEvent.setTargetAngle(it))
                       }
                   )
                   Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
                       ProgressBarItem(
                           modifier = Modifier.weight(0.3f),
                           targetDistance = uiState.recommended.toFloat(),
                           progress = (uiState.consume / uiState.recommended),
                           name = "Recommended"
                       )
                       ProgressBarItem(
                           modifier = Modifier.weight(0.3f),
                           targetDistance = uiState.target.toFloat(),
                           progress = if(uiState.target==0) 0f else(uiState.consume / uiState.target),
                           name = "Goal"
                       )
                       ProgressBarItem(
                           modifier = Modifier.weight(0.3f),
                           targetDistance = uiState.target.toFloat(),
                           progress =if(uiState.target==0) 0f else 1f - (uiState.consume / uiState.target),
                           name = "Remaining"
                       )
                   }
               }
           }
       }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MeditationBottomSheet(
    uiState: HomeUiState,
    level:String,
    language:String,
    instructor:String,
    scaffoldState: BottomSheetScaffoldState,
    Event: (MEvent) -> Unit,
    onClickMusic: () -> Unit,
    onClickLanguage: () -> Unit,
    onClickLevel: () -> Unit,
    onClickInstructor: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.background(Color.Yellow)
            .heightIn(min = 250.dp, max = 525.dp)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {

        DividerLineCenter()
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            CardItem(
                modifier = Modifier.weight(0.5f),
                name = "Music ",
                type = "",
                id = R.drawable.baseline_music_note_24
            ) { onClickMusic() }

            CardItem(
                modifier = Modifier.weight(0.5f),
                name = "Instructor",
                type = instructor,
                id = R.drawable.baseline_merge_type_24,
                onClick = { onClickInstructor() }
            )

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

//        AnimatedVisibility(visible = scaffoldState.bottomSheetState.isCollapsed) {
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(spacing.medium)
//            ) {
//
//            }
//        }

        Row(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
            ButtonWithColor(
                modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
            ) { }
            if (true) {
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Blue,
                    text = "START"
                ) { }
            }
        }
    }
}