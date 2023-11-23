package fit.asta.health.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.BuildConfig
import fit.asta.health.common.ui.navigateToWebView
import fit.asta.health.common.ui.webView
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.shareReferralCode
import fit.asta.health.datastore.ScreenCode
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.feature.address.addressRoute
import fit.asta.health.feature.address.navigateToAddress
import fit.asta.health.feature.auth.AUTH_GRAPH_ROUTE
import fit.asta.health.feature.auth.authRoute
import fit.asta.health.feature.auth.navigateToAuth
import fit.asta.health.feature.feedback.feedbackRoute
import fit.asta.health.feature.orders.navigateToOrders
import fit.asta.health.feature.orders.ordersRoute
import fit.asta.health.feature.profile.BASIC_PROFILE_GRAPH_ROUTE
import fit.asta.health.feature.profile.ProfileContent
import fit.asta.health.feature.profile.basicProfileRoute
import fit.asta.health.feature.profile.create.CreateProfileLayout
import fit.asta.health.feature.scheduler.ui.navigation.schedulerNavigation
import fit.asta.health.feature.settings.settingScreens
import fit.asta.health.feature.settings.view.SettingsUiEvent
import fit.asta.health.feature.testimonials.navigation.testimonialNavRoute
import fit.asta.health.main.view.HOME_GRAPH_ROUTE
import fit.asta.health.main.view.homeScreen
import fit.asta.health.meditation.nav.meditationNavigation
import fit.asta.health.payment.PaymentActivity
import fit.asta.health.referral.navigateToReferral
import fit.asta.health.referral.referralRoute
import fit.asta.health.resources.strings.R
import fit.asta.health.subscription.navigateToSubscription
import fit.asta.health.subscription.subscriptionRoute
import fit.asta.health.tools.breathing.nav.breathingNavigation
import fit.asta.health.tools.exercise.nav.exerciseNavigation
import fit.asta.health.tools.sunlight.nav.sunlightNavigation
import fit.asta.health.tools.walking.nav.stepsCounterNavigation
import fit.asta.health.tools.water.nav.waterToolNavigation
import fit.asta.health.wallet.navigateToWallet
import fit.asta.health.wallet.walletRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainNavHost(isConnected: Boolean, mainViewModel: MainViewModel) {

    if (!isConnected) {
        Box(modifier = Modifier.fillMaxSize()) {
            AppInternetErrorDialog {}
        }
    }
    val screenCode by mainViewModel.screenCode.collectAsStateWithLifecycle()
    when (screenCode) {
        is UiState.Success -> {
            val startDestination = when ((screenCode as UiState.Success<Int>).data) {
                ScreenCode.Auth.code -> {
                    AUTH_GRAPH_ROUTE
                }

                ScreenCode.BasicProfile.code -> {
                    BASIC_PROFILE_GRAPH_ROUTE
                }

                else -> {
                    HOME_GRAPH_ROUTE
                }
            }
            Log.d("TAG", "MainNavHost: $startDestination")
            MainNavHost(startDestination)
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainNavHost(startDestination: String) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = startDestination
    ) {
        authRoute(navController, navController::navigateToWebView)
        basicProfileRoute()
        homeScreen(navController)

        composable(route = Graph.Profile.route) {
            ProfileContent(onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Graph.CreateProfile.route) })
        }
        composable(route = Graph.CreateProfile.route) {
            CreateProfileLayout(onBack = { navController.popBackStack() })
        }

        breathingNavigation(navController, onBack = { navController.navigateUp() })
        waterToolNavigation(navController, onBack = { navController.navigateUp() })
        meditationNavigation(navController, onBack = { navController.navigateUp() })
        sunlightNavigation(navController, onBack = { navController.navigateUp() })
        //sleepNavGraph(navController,  onBack = { navController.navigateUp() })
        exerciseNavigation(navController, onBack = { navController.navigateUp() })
        testimonialNavRoute(navController, onBack = { navController.navigateUp() })
        schedulerNavigation(navController, onBack = { navController.navigateUp() })
        stepsCounterNavigation(navController, onBack = { navController.navigateUp() })

        settingScreens { key ->
            when (key) {
                SettingsUiEvent.Orders -> {
                    navController.navigateToOrders()
                }

                SettingsUiEvent.NavigateToAuthScreen -> {
                    navController.navigateToAuth()
                }

                SettingsUiEvent.NavigateToSubscription -> {
                    navController.navigateToSubscription()
                }

                SettingsUiEvent.NavigateToOrders -> {
                    navController.navigateToOrders()
                }

                SettingsUiEvent.REFERRAL -> {
                    navController.navigateToReferral()
                }

                SettingsUiEvent.WALLET -> {
                    navController.navigateToWallet()
                }

                SettingsUiEvent.ADDRESS -> {
                    navController.navigateToAddress()
                }

                SettingsUiEvent.BACK -> {
                    navController.popBackStack()
                }

                SettingsUiEvent.SHARE -> {
                    context.shareApp(BuildConfig.APPLICATION_ID)
                }

                SettingsUiEvent.BUG -> {
                    context.sendBugReportMessage()
                }

                SettingsUiEvent.TERMS -> {
                    val url = URLEncoder.encode(
                        getImgUrl(context.getString(R.string.url_terms_of_use)),
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigateToWebView(url)
                }

                SettingsUiEvent.PRIVACY -> {
                    val url = URLEncoder.encode(
                        getImgUrl(context.getString(R.string.url_privacy_policy)),
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigateToWebView(url)
                }

                SettingsUiEvent.VERSION -> {
                    Toast.makeText(
                        context,
                        context.getCurrentBuildVersion(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
        feedbackRoute(navController::popBackStack)
        addressRoute(navController::popBackStack)
        ordersRoute(navController::popBackStack)

        subscriptionRoute(
            onBackPress = navController::popBackStack,
            onLaunchPayments = PaymentActivity::launch
        )
        referralRoute(navController::popBackStack) {
            context.shareReferralCode(it, BuildConfig.APPLICATION_ID)
        }
        walletRoute(
            onProceedToAdd = PaymentActivity::launch,
            onBackPress = navController::popBackStack
        )
        webView()
    }
}

