package fit.asta.health.sunlight.feature.screens.skin_conditions


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.Value
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.sunlight.feature.event.SkinConditionEvents
import fit.asta.health.sunlight.feature.screens.skin_conditions.components.SkinConditionPagingNavigator
import fit.asta.health.sunlight.feature.screens.skin_conditions.select_medication.SelectMedicationScreen
import fit.asta.health.sunlight.feature.screens.skin_conditions.skin_color_screen.SelectSkinColorScreen
import fit.asta.health.sunlight.feature.screens.skin_conditions.skin_exposure_screen.SkinExposureScreen
import fit.asta.health.sunlight.feature.screens.skin_conditions.skin_type_screen.SkinTypeScreen
import fit.asta.health.sunlight.feature.screens.skin_conditions.spf_screen.SPFScreen
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionPager
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionState
import fit.asta.health.sunlight.feature.viewmodels.SkinConditionViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun SkinConditionScreen(
    goto: Int = 0,
    skinConditionViewModel: SkinConditionViewModel = hiltViewModel(),
    skinConditionDataMapper: MutableMap<String, Value?>,
    navigateBack: () -> Unit = {},
    isForUpdate: MutableState<Boolean>,
    updateDataState: State<UiState<PutResponse>>,
    onEvent: (SkinConditionEvents) -> Unit = {},
    exposureState: State<SkinConditionState>,
    colorState: State<SkinConditionState>,
    skinTypeState: State<SkinConditionState>,
    spfState: State<SkinConditionState>,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    DisposableEffect(isForUpdate, effect = {
        onDispose {
            isForUpdate.value = false
        }
    })
    LaunchedEffect(Unit) {
//        skinConditionViewModel.getSkinColor()
        skinConditionViewModel.supplementData.value?.let {
            skinConditionViewModel.apply {
                selectedUOM.value = it.unit ?: ""
                selectedDosage.value = (it.iu ?: 0).toString()
                selectedIntervalOption.value = it.prd ?: ""
            }
        }
    }
    val state = rememberPagerState(
        initialPage = goto,
        initialPageOffsetFraction = 0f
    ) {
        skinConditionViewModel.tabs.size
    }

    val onPageChange: (Int) -> Unit = { no ->
        if (no in 0..6) {
            scope.launch { state.animateScrollToPage(no) }
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = skinConditionViewModel.title.value,
                onBack = navigateBack,
                containerColor = AppTheme.colors.surface
            )
        },
        modifier = Modifier.background(AppTheme.colors.surface)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.spacing.level2)

        ) {
            AppHorizontalPager(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                    )
                    .fillMaxSize(),
                pagerState = state,
                userScrollEnabled = false,
            ) { page ->
                LaunchedEffect(state.currentPage) {
                    skinConditionViewModel.title.value =
                        context.getString(skinConditionViewModel.tabs[state.currentPage].title)
                }
                when (page) {
                    SkinConditionPager.SELECT_EXPOSURE -> {
                       /* LaunchedEffect(SkinConditionPager.SELECT_EXPOSURE) {
                            skinConditionViewModel.getSkinExposureData()
                        }*/
                        SkinExposureScreen(
                            state = exposureState,
                            skinConditionDataMapper = skinConditionDataMapper,
                            onEvent=onEvent
                        ) {
                            onEvent.invoke(
                                SkinConditionEvents.OnDataUpdate(
                                    SkinConditionPager.SELECT_EXPOSURE,
                                    it.toCommonPrc(
                                        screenName = skinConditionViewModel.title.value,
                                        screenCode = SkinConditionScreenCode.EXPOSURE_SCREEN
                                    )
                                )
                            )
                            /* skinConditionViewModel.conditionUpdateData[SkinConditionPager.SELECT_EXPOSURE] =
                                 it.toCommonPrc(
                                     screenName = skinConditionViewModel.title.value,
                                     screenCode = SkinConditionScreenCode.EXPOSURE_SCREEN
                                 )*/
                        }
                    }

                    SkinConditionPager.SELECT_COLOR -> {
                        SelectSkinColorScreen(
                            skinColorsData = skinConditionViewModel.skinColors,
                            skinColorState = skinConditionViewModel.skinColorState,
                            skinConditionDataMapper = skinConditionDataMapper,
                            onEvent=onEvent
                        ) {
                            onEvent.invoke(
                                SkinConditionEvents.OnDataUpdate(
                                    SkinConditionPager.SELECT_COLOR,
                                    it.toCommonPrc(
                                        screenName = skinConditionViewModel.title.value,
                                        screenCode = SkinConditionScreenCode.SKIN_COLOR_SCREEN
                                    )
                                )
                            )
                            /*skinConditionViewModel.conditionUpdateData[SkinConditionPager.SELECT_COLOR] =
                                it.toCommonPrc(
                                    screenName = skinConditionViewModel.title.value,
                                    screenCode = SkinConditionScreenCode.SKIN_COLOR_SCREEN
                                )*/
                        }
                    }

                    SkinConditionPager.SELECT_SKIN_TYPE -> {
                        LaunchedEffect(Unit) {
                            skinConditionViewModel.getSkinType()
                        }
                        SkinTypeScreen(
                            skinConditionViewModel.skinTypeState,
                            skinConditionDataMapper = skinConditionDataMapper
                        ) {
                            onEvent.invoke(
                                SkinConditionEvents.OnDataUpdate(
                                    SkinConditionPager.SELECT_SKIN_TYPE,
                                    it.toCommonPrc(
                                        screenName = skinConditionViewModel.title.value,
                                        screenCode = SkinConditionScreenCode.SKIN_TYPE_SCREEN
                                    )
                                )
                            )
                            /*skinConditionViewModel.conditionUpdateData[SkinConditionPager.SELECT_SKIN_TYPE] =
                                it.toCommonPrc(
                                    screenName = skinConditionViewModel.title.value,
                                    screenCode = SkinConditionScreenCode.SKIN_TYPE_SCREEN
                                )*/
                        }
                    }

                    SkinConditionPager.SELECT_SPF -> {
                        LaunchedEffect(Unit) {
                            skinConditionViewModel.getSpfScreen()
                        }
                        SPFScreen(
                            skinConditionViewModel.spfScreenState,
                            skinConditionDataMapper = skinConditionDataMapper
                        ) {
                            onEvent.invoke(
                                SkinConditionEvents.OnDataUpdate(
                                    SkinConditionPager.SELECT_SPF,
                                    it.toCommonPrc(
                                        screenName = skinConditionViewModel.title.value,
                                        screenCode = SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN
                                    )
                                )
                            )
                            /*skinConditionViewModel.conditionUpdateData[SkinConditionPager.SELECT_SPF] =
                                it.toCommonPrc(
                                    screenName = skinConditionViewModel.title.value,
                                    screenCode = SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN
                                )*/
                        }
                    }

                    SkinConditionPager.SELECT_SUPPLEMENT -> {
                        SelectMedicationScreen(
                            skinConditionViewModel = skinConditionViewModel,
                            onEvent = onEvent
                        )
                    }
                }
            }
            when (updateDataState.value) {
                UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AppDotTypingAnimation()
                    }
                }

                else -> {
                    //handle other cases
                }
            }
            if (skinConditionViewModel.isForUpdate.value) {
                //update the data
                AppFilledButton(
                    textToShow = "UPDATE",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    skinConditionViewModel.updateSunlightData()
                    // navigateBack.invoke()
                }

            } else {
                SkinConditionPagingNavigator(
                    state = state,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    uploadDataAndNavigate = {
                        //navigate to home after upload complete
                        skinConditionViewModel.updateSunlightData()
                        // navigateBack.invoke()
                    }) { page ->
                    onPageChange.invoke(page)
                }
            }
        }
    }
}