package fit.asta.health.sunlight.feature.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import fit.asta.health.common.utils.Constants
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.atomic.AppLoadingScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.sunlight.feature.components.ErrorScreen
import fit.asta.health.sunlight.feature.screens.home.homeScreen.SunHomeScreen
import fit.asta.health.sunlight.feature.screens.selfcare_suggetion.selfcare_suggetion_screen.SelfCareSuggestionScreen
import fit.asta.health.sunlight.feature.screens.session_result_screen.SessionResultScreen
import fit.asta.health.sunlight.feature.screens.skin_conditions.SkinConditionScreen
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.getScreenIndex
import fit.asta.health.sunlight.feature.viewmodels.HomeViewModel
import fit.asta.health.sunlight.feature.viewmodels.SkinConditionViewModel
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR


@Composable
fun RootNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = "root_graph",
        startDestination = Constants.SUNLIGHT_GRAPH_ROUTE
    ) {
        sunlightNavigation(navController = navController)
    }
}

fun NavController.navigateToSunlight(navOptions: (NavOptionsBuilder) -> Unit = { }) {
    this.navigate(
        Constants.SUNLIGHT_GRAPH_ROUTE,
        navOptions
    )
}


fun NavGraphBuilder.sunlightNavigation(
    navController: NavHostController,
    onBack: () -> Unit = {}
) {
    navigation(
        route = Constants.SUNLIGHT_GRAPH_ROUTE,
        startDestination = SunlightScreens.SunlightHomeScreen.route
    ) {
        composable(SunlightScreens.SunlightHomeScreen.route) { stack ->
            val homeViewModel = stack.sharedViewModel<HomeViewModel>(navController = navController)
            val homeState = homeViewModel.homeState.collectAsState()
            val state = homeViewModel.sunlightDataState.collectAsState()
            LaunchedEffect(Unit) {
                homeViewModel.getHomeScreenData()
            }
            homeViewModel.navigateToCondition = {
                navController.navigateToSkinConditionScreen("-", navOptions = { navOptionsBuilder ->
                    navOptionsBuilder.popUpTo(SunlightScreens.SunlightHomeScreen.route) {
                        inclusive = true
                    }
                })
            }
            homeViewModel.navigateToResultScreen = {
                Log.d("navigateResult", "sunlightNavigation: ")
                navController.navigateToSessionResultScreen()
            }
            when (homeState.value) {
                is UiState.ErrorMessage -> {
                    ErrorScreen(
                        errorIcon = DrawR.drawable.server_error,
                        errorTitle = (homeState.value as UiState.ErrorMessage).resId,
                        errorMessage = "Please try again later"
                    )
                    /* LaunchedEffect(Unit) {
                         navController.navigateToSkinConditionScreen(
                             "-",
                             navOptions = { navOptionsBuilder ->
                                 navOptionsBuilder.popUpTo(SunlightScreens.SunlightHomeScreen.route) {
                                     inclusive = true
                                 }
                             })
                     }*/
                }

                is UiState.NoInternet -> {
                    ErrorScreen(
                        errorTitle = StringR.string.no_internet,
                        errorMessage = "Please check your internet connection and try again",
                        buttonTitle = "Retry",
                    ) {
                        //retry
                    }
                }

                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AppLoadingScreen()
                    }
                }

                is UiState.Success -> {
                    SunHomeScreen(
                        homeState = state,
                        navigateToSkinCondition = { screen ->
                            navController.navigateToSkinConditionScreen(screen)
                        },
                        navigateToHelpAndSuggestion = {
                            navController.navigateToHelpAndSuggestionScreen()
                        },
                        onEvent = homeViewModel::onEvent,
                        onBack = onBack
                    )
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }

        }
        composable(SunlightScreens.SessionResultScreen.route) { stack ->
            val homeViewModel = stack.sharedViewModel<HomeViewModel>(navController = navController)
            val state = homeViewModel.sunlightDataState.collectAsState()
            val sessionState = homeViewModel.sessionResultDataState.collectAsState()
            LaunchedEffect(Unit) {
                homeViewModel.getSessionResult()
            }
            if (sessionState.value.isLoading) {
                AppLoadingScreen()
            } else {
                SessionResultScreen(
                    state = state,
                    sessionState = sessionState
                ) {
                    navController.popBackStack()
                }
            }

        }
        composable(SunlightScreens.SkinConditionScreen.route + "?id={id}") { backStack ->
            var id = backStack.arguments?.getString("id")?.toInt()
            val skinConditionViewModel: SkinConditionViewModel = hiltViewModel()
            val homeViewModel =
                backStack.sharedViewModel<HomeViewModel>(navController = navController)
            val updateDataState =
                skinConditionViewModel.updateDataState.collectAsState()
            val exposureState = skinConditionViewModel.skinExposureState.collectAsState()
            skinConditionViewModel.popBackStack = {
                if (skinConditionViewModel.id.value.equals("000000000000000000000000") || skinConditionViewModel.id.value == null) {
                    navController.navigateToSunlight {
                        it.popUpTo(SunlightScreens.SkinConditionScreen.route) {
                            inclusive = true
                        }
                    }
                } else {
                    navController.popBackStack()
                }
            }
            if (id != null) {
                if (id == -1) {
                    skinConditionViewModel.isForUpdate.value = false
                    id = 0
                } else {
                    skinConditionViewModel.isForUpdate.value = true
                }
                LaunchedEffect(Unit) {
                    skinConditionViewModel.conditionUpdateData.clear()
                    skinConditionViewModel.conditionUpdateData.addAll(
                        homeViewModel.skinConditionData
                    )
                    skinConditionViewModel.supplementData.value = homeViewModel.supplementData.value
                    skinConditionViewModel.id.value =
                        if (homeViewModel.sunlightDataState.value.sunlightHomeResponse?.sunLightData?.uid?.equals(
                                "000000000000000000000000"
                            ) == true
                        ) {
                            null

                        } else {
                            homeViewModel.sunlightDataState.value.sunlightHomeResponse?.sunLightData?.id
                                ?: ""
                        }
                }
                SkinConditionScreen(
                    goto = id,
                    skinConditionViewModel = skinConditionViewModel,
                    skinConditionDataMapper = homeViewModel.skinConditionDataMapper,
                    updateDataState = updateDataState,
                    isForUpdate = skinConditionViewModel.isForUpdate,
                    navigateBack = { skinConditionViewModel.popBackStack.invoke() },
                    onEvent = skinConditionViewModel::onEvent,
                    exposureState = exposureState,
                    colorState = exposureState,
                    spfState = exposureState,
                    skinTypeState = exposureState
                )
            }
        }
        composable(SunlightScreens.HelpAndSuggestionScreen.route) { stack ->
            val homeViewModel = stack.sharedViewModel<HomeViewModel>(navController = navController)
            val state = homeViewModel.helpAndSuggestionState.collectAsState()
            LaunchedEffect(Unit) {
                homeViewModel.getSupplementAndFoodInfo()
            }
            when (state.value) {
                is UiState.ErrorMessage -> {
                    ErrorScreen(
                        errorIcon = DrawR.drawable.server_error,
                        errorTitle = (state.value as UiState.ErrorMessage).resId,
                        errorMessage = "Please try again later"
                    )
                }

                is UiState.Loading -> {
                    AppDotTypingAnimation()
                }

                is UiState.NoInternet -> {
                    ErrorScreen(
                        errorTitle = StringR.string.no_internet,
                        errorMessage = "Please check your internet connection and try again",
                        buttonTitle = "Retry",
                    ) {
                        //retry
                    }
                }

                is UiState.Success -> {
                    SelfCareSuggestionScreen(data = (state.value as UiState.Success<HelpAndNutrition>).data) {
                        navController.popBackStack()
                    }
                }

                else -> {}
            }


        }
    }


}

fun NavController.navigateToSkinConditionScreen(
    id: String,
    navOptions: (NavOptionsBuilder) -> Unit = {}
) {
    this.navigate(
        SunlightScreens.SkinConditionScreen.route + "?id=${id.getScreenIndex()}",
        navOptions
    )
}

fun NavController.navigateToSessionResultScreen(navOptions: NavOptions? = null) {
    this.navigate(
        SunlightScreens.SessionResultScreen.route,
        navOptions
    )
}

fun NavController.navigateToHelpAndSuggestionScreen(navOptions: (NavOptionsBuilder) -> Unit = {}) {
    this.navigate(
        SunlightScreens.HelpAndSuggestionScreen.route
    ) {
        navOptions(this)
    }
}



