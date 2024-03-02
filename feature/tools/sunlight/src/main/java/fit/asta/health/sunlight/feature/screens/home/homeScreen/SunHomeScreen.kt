package fit.asta.health.sunlight.feature.screens.home.homeScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SheetValue
//import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.atomic.token.AppSheetValue
import fit.asta.health.designsystem.atomic.token.checkState
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppSheetState
import fit.asta.health.designsystem.molecular.background.AppSnackBarDuration
import fit.asta.health.designsystem.molecular.background.appRememberBottomSheetScaffoldState
import fit.asta.health.designsystem.molecular.background.appRememberStandardBottomSheetState
import fit.asta.health.designsystem.molecular.background.appSnackBarHostState
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.sunlight.feature.components.SunlightTopBar
import fit.asta.health.sunlight.feature.components.UvBarChartCard
import fit.asta.health.sunlight.feature.event.OnHomeMenu
import fit.asta.health.sunlight.feature.event.SunlightHomeEvent
import fit.asta.health.sunlight.feature.screens.bottom_sheet.SunlightBottomSheet
import fit.asta.health.sunlight.feature.screens.home.components.DurationContent
import fit.asta.health.sunlight.feature.screens.home.components.HomePageSugBanner
import fit.asta.health.sunlight.feature.screens.home.components.SpfDialogueContent
import fit.asta.health.sunlight.feature.screens.home.components.UvIndexLocationCard
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.feature.utils.DateUtil
import kotlinx.coroutines.launch


@Composable
fun SunHomeScreen(
    navigateToSkinCondition: (String) -> Unit = {},
    navigateToHelpAndSuggestion: () -> Unit = {},
    onEvent: (SunlightHomeEvent) -> Unit = {},
    onBack: () -> Unit = {},
    homeState: State<SunlightHomeState>
) {
    val scrollState = rememberScrollState()
    val sheetState = appRememberStandardBottomSheetState()
    val scaffoldState = appRememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    val snackbarHostState = appSnackBarHostState()
    var showDialogue by remember {
        mutableStateOf(false)
    }
    BackHandler(scaffoldState.bottomSheetState.currentValue == AppSheetValue.Expanded) {
        scope.launch {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }
    AppScaffold(snackBarHostState = snackbarHostState) {
        AppBottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            sheetPeekHeight = 120.dp,
            topBar = {
                SunlightTopBar(
                    title = "Sunlight Tool",
                    onBack = onBack,
                    onHelp = { onMenu ->
                        when (onMenu) {
                            OnHomeMenu.OnSkinConditionEdit -> {
                                if (!homeState.value.sunlightHomeResponse?.sunLightData?.prc.isNullOrEmpty()) {
                                    navigateToSkinCondition.invoke("-")
                                }
                            }

                            OnHomeMenu.OnHelpAndSuggestion -> {
                                navigateToHelpAndSuggestion.invoke()
                            }
                        }
                    },
                )
            },
            sheetContent = {
                SunlightBottomSheet(
                    selectedData = homeState.value.skinConditionData,
                    timerState = homeState.value.isTimerRunning,
                    timerPauseState = homeState.value.isTimerPaused,
                    animatedState = checkState(scaffoldState),
                    supplementData = homeState.value.supplementData,
                    goToList = { _, code ->
                        navigateToSkinCondition.invoke(code)
                    },
                    onTarget = {
                        navigateToSkinCondition.invoke(SkinConditionScreenCode.SUPPLEMENT_SCREEN)
                    },
                    onStart = {
                        val time =
                            homeState.value.sunlightHomeResponse?.sunLightProgressData?.rem ?: 0
                        if (homeState.value.isTimerRunning.value) {
                            onEvent(SunlightHomeEvent.OnStopTimer)
                        } else {
                            if (!DateUtil.isCurrentTimeLaterThan(
                                    homeState.value.sunlightHomeResponse?.sunSlotData?.toChartData()
                                        ?.lastOrNull()?.time ?: ""
                                )
                            ) {
                                onEvent(SunlightHomeEvent.OnStartTimer(time))
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Timer can be only started during the given time slots.Thank you.",
                                        duration = AppSnackBarDuration.Short,
                                        withDismissAction = true
                                    )
                                }
                            }
                        }
                    },
                    onPause = {
                        if (homeState.value.isTimerPaused.value) {
                            onEvent(SunlightHomeEvent.OnResume)
                        } else {
                            onEvent(SunlightHomeEvent.OnPause)
                        }
                    }
                ) {
                    //schedule
                    //showDialogue = true
                }
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                /*AppLocalImage(
                painter = painterResource(id = DrawR.drawable.after_noon),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )*/
                Column(
                    modifier = Modifier
                        .padding(
                            top = padding.calculateTopPadding(),
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 120.dp
                        )
                        .verticalScroll(scrollState)
                ) {
                    AnimatedVisibility(homeState.value.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(600.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AppDotTypingAnimation()
                        }
                    }

                    UvIndexLocationCard(homeState.value.sunlightHomeResponse?.sunSlotData) {
                        showDialogue = true
                    }
//                GaugeChart()
                    //  WeatherContent(homeState.value.sunlightHomeResponse?.sunSlotData)
                    DurationContent(homeState)
                    if (!homeState.value.sunlightHomeResponse?.tips?.tips.isNullOrEmpty()) {
                        HomePageSugBanner(homeState.value.sunlightHomeResponse?.tips?.tips)
                        UvBarChartCard(
                            homeState.value.sunlightHomeResponse?.sunSlotData,
                        )
                    }
                }
            }
        }
        if (showDialogue) {
            AppDialog(onDismissRequest = { showDialogue = false }) {
                SpfDialogueContent(onContinue = { showDialogue = false }, onUpdate = {
                    showDialogue = false
                    navigateToSkinCondition.invoke(SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN)
                })
            }
        }
    }
}
