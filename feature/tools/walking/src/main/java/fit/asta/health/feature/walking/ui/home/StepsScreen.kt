package fit.asta.health.feature.walking.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.smarttoolfactory.colorpicker.util.roundToTwoDigits
import fit.asta.health.common.health_data.ExerciseSessionData
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.formatTime
import fit.asta.health.common.utils.toDraw
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.walking.local.model.Day
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.token.AppSheetValue
import fit.asta.health.designsystem.atomic.token.checkState
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.CardItem
import fit.asta.health.designsystem.molecular.CircularSliderFloat
import fit.asta.health.designsystem.molecular.CircularSliderInt
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppSheetState
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.appRememberBottomSheetScaffoldState
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.walking.ui.component.InstalledMessage
import fit.asta.health.feature.walking.ui.component.NotInstalledMessage
import fit.asta.health.feature.walking.ui.component.NotSupportedMessage
import fit.asta.health.feature.walking.ui.component.StepsProgressCard
import fit.asta.health.feature.walking.vm.WalkingViewModel
import fit.asta.health.resources.drawables.R

@Composable
fun StepsScreen(
    healthConnectAvailability: Int,
    onResumeAvailabilityCheck: () -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    permissions: Set<String>,
    permissionsGranted: Boolean,
    firstTime: Boolean,
    sessionMetrics: ExerciseSessionData,
    uiState: WalkingViewModel.HealthUiState,
    onPermissionsResult: () -> Unit = {},
    onPermissionsLaunch: (Set<String>) -> Unit = {},
    list: List<Day>,
    state: UiState<StepsUiState>,
    selectedData: List<Prc>,
    onStart: () -> Unit,
    setTarget: (Float, Float) -> Unit,
    goToList: (Int, String) -> Unit,
    onScheduler: () -> Unit,
    onBack: () -> Unit,
) {
    val scaffoldState = appRememberBottomSheetScaffoldState(
        bottomSheetState = AppSheetState(
            initialValue = AppSheetValue.PartiallyExpanded,
            skipHiddenState = true,
        )
    )
    var showTargetDialogWithResult by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val currentOnAvailabilityCheck by rememberUpdatedState(onResumeAvailabilityCheck)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                currentOnAvailabilityCheck()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    LaunchedEffect(uiState) {
        // If the initial data load has not taken place, attempt to load the data.
        if (uiState is WalkingViewModel.HealthUiState.Uninitialized) {
            Log.d("rishi", "HealthConnect will Not allow: $healthConnectAvailability")
            onPermissionsResult()
        }
    }
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
                        animatedState = checkState(scaffoldState),
                        goToList = goToList,
                        onTarget = { showTargetDialogWithResult = true },
                        onStart = onStart,
                        onScheduler = onScheduler
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
                    Log.d("rishi", "HealthConnect : $healthConnectAvailability")
                    when (healthConnectAvailability) {
                        /*3*/ HealthConnectClient.SDK_AVAILABLE  -> if (!permissionsGranted) {
                            item {
                                InstalledMessage(onPermissionsLaunch = {
                                    onPermissionsLaunch(permissions)
                                })
                            }
                        }

                        /*2*/ HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> item {
                            if (firstTime) PlayStorePermsUI()
                            NotInstalledMessage()
                        }

                        /*1*/  HealthConnectClient.SDK_UNAVAILABLE -> item {
//                            BodyTexts.Level2(text = "Not Supported ")
                            NotSupportedMessage()
                        }
//                      
                    }
                    item {
                        AppSurface(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularSliderInt(
                                modifier = Modifier.size(200.dp),
                                isStarted = true,
                                appliedAngleDistanceValue = 110f,
                                indicatorValue = sessionMetrics.totalSteps?.toFloat()
                                    ?: state.data.stepCount.toFloat(),
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
                                titleValue = sessionMetrics.totalDistance?.inKilometers?.toFloat()
                                    ?.roundToTwoDigits()?.toString() ?: "0",
                                id = R.drawable.total_distance
                            )
                            StepsProgressCard(
                                modifier = Modifier.weight(.3f),
                                title = "Steps",
                                titleValue = sessionMetrics.totalSteps?.toInt()?.toString() ?: "0",
                                id = R.drawable.runing
                            )
                            StepsProgressCard(
                                modifier = Modifier.weight(.3f),
                                title = "Calories",
                                titleValue = (sessionMetrics.totalEnergyBurned?.inCalories?.toFloat()
                                    ?.div(1000f)?.roundToTwoDigits()?.toString()) ?: "0",
                                id = R.drawable.calories
                            )
                        }
                    }


                    item {
                        AnimatedContent(targetState = list.isNotEmpty(), label = "") {
                            if (it) {
                                TitleTexts.Level2(text = "Today Session Data ")
                            } else {
                                TitleTexts.Level2(text = "Start today session ")
                            }
                        }
                    }
                    items(list) {
                        DayItem(item = it)
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

@Composable
fun WalkingBottomSheet(
    selectedData: List<Prc>,
    animatedState: Boolean,
    goToList: (Int, String) -> Unit,
    onTarget: () -> Unit,
    onStart: () -> Unit,
    onScheduler: () -> Unit
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
                    modifier = Modifier.weight(0.5f),
                    color = Color.Green,
                    text = "SCHEDULE",
                    onClick = onScheduler
                )
                ButtonWithColor(
                    modifier = Modifier.weight(0.5f),
                    color = Color.Blue,
                    text = "START",
                    onClick = onStart
                )
            }
        }
    }
}

@Composable
fun SunlightCard(modifier: Modifier) {
    val checked = remember { mutableStateOf(true) }
    AppCard(
        modifier = modifier,
        //colors = CardDefaults.cardColors(containerColor = AppTheme.colors.background)
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
    onPositiveClick: (Float, Float) -> Unit,
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
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                BodyTexts.Level2(
                    text = "Recommend distance ${dialogData.recommendDistance} Recommend duration ${dialogData.recommendDuration}",
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.onSurface
                )
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        ButtonWithColor(
                            color = AppTheme.colors.error, text = "Close",
                            modifier = Modifier
                                .height(AppTheme.buttonSize.level7)
                                .fillMaxWidth()
                        ) {
                            onNegativeClick()
                        }
//
                    }
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        ButtonWithColor(
                            color = AppTheme.colors.error, text = "Save",
                            modifier = Modifier
                                .height(AppTheme.buttonSize.level7)
                                .fillMaxWidth()
                        ) {
                            onPositiveClick(distance, duration)
                        }
//
                    }
                }
            }
        }
    }
}

@Composable
fun DayItem(item: Day) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(.7f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIcon(imageVector = Icons.Default.AccessTime)
                    CaptionTexts.Level2(text = formatTime(item.startupTime))
                }
                TitleTexts.Level2(
                    text = "${
                        item.distanceTravelled.toFloat().roundToTwoDigits()
                    } km in ${item.duration} min"
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppIcon(imageVector = Icons.Default.DirectionsRun)
                        CaptionTexts.Level2(text = "${item.steps} steps")
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppIcon(imageVector = Icons.Default.LocalFireDepartment)
                        CaptionTexts.Level2(text = "${item.calorieBurned} calories")
                    }
                }
            }
            AppSurface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = AppTheme.colors.inverseOnSurface
            ) {
                Box(contentAlignment = Alignment.Center) {
                    AppIcon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.Default.DirectionsRun
                    )
                }
            }
        }
    }
}