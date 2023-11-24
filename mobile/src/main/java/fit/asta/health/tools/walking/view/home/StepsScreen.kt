package fit.asta.health.tools.walking.view.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.colorpicker.util.roundToTwoDigits
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.formatTime
import fit.asta.health.common.utils.toDraw
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.CardItem
import fit.asta.health.designsystem.molecular.CircularSliderFloat
import fit.asta.health.designsystem.molecular.CircularSliderInt
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R
import fit.asta.health.tools.walking.core.domain.model.Day
import fit.asta.health.tools.walking.view.component.StepsProgressCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsScreen(
    list: SnapshotStateList<Day>,
    state: UiState<StepsUiState>,
    selectedData: SnapshotStateList<Prc>,
    onStart: () -> Unit,
    setTarget: (Float, Int) -> Unit,
    goToList: (Int, String) -> Unit,
    onBack: () -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    var showTargetDialogWithResult by remember { mutableStateOf(false) }
    val context = LocalContext.current
    when (state) {
        UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppCircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            if (showTargetDialogWithResult) {
                ShowTargetDialog(
                    onDismiss = { showTargetDialogWithResult = false },
                    onNegativeClick = { showTargetDialogWithResult = false },
                    onPositiveClick = { dis, dur ->
                        setTarget(dis, dur)
                        showTargetDialogWithResult = false
                    },
                    dialogData = state.data
                )
            }
            AppBottomSheetScaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState,
                sheetPeekHeight = 240.dp,
                topBar = {
                    AppTopBarWithHelp(
                        title = "Walking Tool",
                        onBack = onBack,
                        onHelp = { /*TODO*/ },
                    )
                },
                sheetContent = {
                    WalkingBottomSheet(
                        selectedData = selectedData,
                        scaffoldState = scaffoldState,
                        goToList = goToList,
                        onTarget = { showTargetDialogWithResult = true },
                        onStart = onStart
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        AppSurface(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularSliderInt(
                                modifier = Modifier.size(200.dp),
                                isStarted = true,
                                appliedAngleDistanceValue = 1500f,
                                indicatorValue = state.data.stepCount.toFloat(),
                                maxIndicatorValue = 10000f,
                                bigTextSuffix = "Steps",
                                onChangeDistance = {},
                                onChangeAngleDistance = {}
                            )
                        }

                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StepsProgressCard(
                                modifier = Modifier.weight(.3f),
                                title = "Kilometer",
                                titleValue = "${state.data.distance.roundToTwoDigits()}",
                                id = R.drawable.total_distance
                            )
                            StepsProgressCard(
                                modifier = Modifier.weight(.3f),
                                title = "Steps",
                                titleValue = "${state.data.stepCount}",
                                id = R.drawable.runing
                            )
                            StepsProgressCard(
                                modifier = Modifier.weight(.3f),
                                title = "Calories",
                                titleValue = "${state.data.caloriesBurned}",
                                id = R.drawable.calories
                            )
                        }
                    }


                    item {
                        AppCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                TitleTexts.Level2(text = "Today Session Data ")
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TitleTexts.Level2(text = "on")
                                    TitleTexts.Level2(text = "steps")
                                    TitleTexts.Level2(text = "cal")
                                    TitleTexts.Level2(text = "dis")
                                    TitleTexts.Level2(text = "dur")
                                }
                                list.forEach {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        TitleTexts.Level2(text = formatTime(it.startupTime))
                                        TitleTexts.Level2(text = it.steps.toString())
                                        TitleTexts.Level2(
                                            text = it.calorieBurned.toFloat().roundToTwoDigits()
                                                .toString()
                                        )
                                        TitleTexts.Level2(
                                            text = it.distanceTravelled.toFloat().roundToTwoDigits()
                                                .toString()
                                        )
                                        TitleTexts.Level2(text = it.duration.toString())
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(200.dp))
                    }
                }
            }
        }

        is UiState.ErrorMessage -> {
            val errorMessage = state.resId.toStringFromResId()
            LaunchedEffect(state) {
                Toast.makeText(
                    context,
                    "ERROR MSG: $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        else -> {}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkingBottomSheet(
    selectedData: SnapshotStateList<Prc>,
    scaffoldState: BottomSheetScaffoldState,
    goToList: (Int, String) -> Unit,
    onTarget: () -> Unit,
    onStart: () -> Unit,
) {

    if (selectedData.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            TitleTexts.Level3(text = "PRACTICE")
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                columns = GridCells.Fixed(2)
            ) {
                item {
                    CardItem(
                        name = "Target",
                        type = "Dis,Dur",
                        id = "tgt".toDraw()
                    ) { onTarget() }
                }
                item {
                    CardItem(
                        name = selectedData.first().ttl,
                        type = selectedData[0].values.first().ttl,
                        id = selectedData[0].code.toDraw(),
                        onClick = {
                            goToList(0, selectedData[0].code)
                        }
                    )
                }
            }

            AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {

                    LazyVerticalGrid(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                        columns = GridCells.Fixed(2)
                    ) {
                        selectedData.forEachIndexed { index, prc ->
                            if (index > 0) {
                                item {
                                    CardItem(name = prc.ttl,
                                        type = prc.values.first().ttl,
                                        id = prc.code.toDraw(),
                                        onClick = {
                                            goToList(index, prc.code)
                                        })
                                }
                            }
                        }
                    }
                    SunlightCard(modifier = Modifier.fillMaxWidth())
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f), color = Color.Green, text = "SCHEDULE"
                ) { }
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Blue,
                    text = "START"
                ) {
                    onStart()
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
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background)
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

@Composable
fun ShowTargetDialog(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Float, Int) -> Unit,
    dialogData: StepsUiState,
) {
    var distance by remember {
        mutableFloatStateOf(0f)
    }
    var duration by remember {
        mutableFloatStateOf(0f)
    }
    AppDialog(onDismissRequest = onDismiss) {
        AppCard {
            Column(
                modifier = Modifier.padding(AppTheme.spacing.level2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleTexts.Level2(
                    text = "Select Distance and Duration",
                    modifier = Modifier.padding(top = AppTheme.spacing.level0),
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                BodyTexts.Level2(
                    text = "Recommend distance ${dialogData.recommendDistance} Recommend duration ${dialogData.recommendDuration}",
                    modifier = Modifier.padding(top = AppTheme.spacing.level0),
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                CircularSliderInt(
                    modifier = Modifier.size(200.dp),
                    isStarted = false,
                    appliedAngleDistanceValue = 10f,
                    indicatorValue = duration,
                    maxIndicatorValue = 120f,
                    bigTextSuffix = "Min",
                    onChangeDistance = { duration = it },
                    onChangeAngleDistance = {}
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                CircularSliderFloat(
                    modifier = Modifier.size(200.dp),
                    isStarted = false,
                    appliedAngleDistanceValue = 1f,
                    indicatorValue = distance,
                    maxIndicatorValue = 10f,
                    bigTextSuffix = "Km",
                    onChangeDistance = { distance = it },
                    onChangeAngleDistance = {}
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        AppFilledButton(
                            onClick = onNegativeClick,
                            modifier = Modifier
                                .height(AppTheme.buttonSize.level7)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.error,
                            )
                        ) {
                            CaptionTexts.Level3(text = "Close", color = AppTheme.colors.onError)
                        }
                    }
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        AppFilledButton(
                            onClick = { onPositiveClick(distance, duration.toInt()) },
                            modifier = Modifier
                                .height(AppTheme.buttonSize.level7)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.error,
                            )
                        ) {
                            CaptionTexts.Level3(text = "Save", color = AppTheme.colors.onError)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }
    }
}