package fit.asta.health.meditation.view.home

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toDraw
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.CardItem
import fit.asta.health.designsystem.molecular.CircularSliderInt
import fit.asta.health.designsystem.molecular.DNDCard
import fit.asta.health.designsystem.molecular.ProgressBarInt
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationHomeScreen(
    state: UiState<Unit>,
    event: (MEvent) -> Unit,
    uiState: ToolUiState,
    selectedData: SnapshotStateList<Prc>,
    onClickMusic: () -> Unit,
    onDNDPermission: () -> Boolean,
    onBack: () -> Unit,
    goToList: (Int) -> Unit,
    onClickSchedule : () -> Unit
) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            event(MEvent.SetDNDMode(true))
        }
    }
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
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
                        selectedData = selectedData,
                        scaffoldState = scaffoldState,
                        event = event,
                        goToList = goToList,
                        onClickSchedule = onClickSchedule
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppSurface(
                        modifier = Modifier.fillMaxWidth()
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
                                    event(MEvent.SetTarget(it))
                                },
                                onChangeAngleDistance = {
                                    event(MEvent.SetTargetAngle(it))
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
                    DNDCard(modifier = Modifier.fillMaxWidth(), mCheckedState = uiState.dndMode,
                        onCheckClicked = {
                            if (onDNDPermission()) {
                                event(MEvent.SetDNDMode(it))
                            } else {
                                val intent =
                                    Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                                requestPermissionLauncher.launch(intent)
                            }
                        })
                    AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = uiState.start
                    ) {
                        ButtonWithColor(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Green,
                            text = "Go To Music Screen"
                        ) { onClickMusic() }
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
fun MeditationBottomSheet(
    uiState: ToolUiState,
    selectedData: SnapshotStateList<Prc>,
    scaffoldState: BottomSheetScaffoldState,
    goToList: (Int) -> Unit,
    event: (MEvent) -> Unit,
    onClickSchedule:() -> Unit
) {
    val context = LocalContext.current
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
                        name = selectedData.first().ttl,
                        type = selectedData.first().values.first().ttl,
                        id = selectedData.first().code.toDraw()
                    ) { }
                }
                item {
                    CardItem(
                        name = selectedData[1].ttl,
                        type = selectedData[1].values.first().ttl,
                        id = selectedData[1].code.toDraw(),
                        onClick = {
                            event(MEvent.GetSheetData(selectedData[1].code))
                            goToList(1)
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
                            if (index > 1) {
                                item {
                                    CardItem(name = prc.ttl,
                                        type = prc.values.first().ttl,
                                        id = prc.code.toDraw(),
                                        onClick = {
                                            event(MEvent.GetSheetData(prc.code))
                                            goToList(index)
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
                ) {
                    onClickSchedule()
                }
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = if (!uiState.start) Color.Blue else Color.Red,
                    text = if (!uiState.start) "START" else "STOP"
                ) {
                    if (!uiState.start) {
                        event(MEvent.Start(context))
                    } else {
                        event(MEvent.End(context))
                    }
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