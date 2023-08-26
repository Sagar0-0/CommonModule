package fit.asta.health.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.BuildConfig
import fit.asta.health.R
import fit.asta.health.common.ui.navigateToWebView
import fit.asta.health.common.ui.webView
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.rateUs
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.feature.address.addressRoute
import fit.asta.health.feature.address.navigateToAddress
import fit.asta.health.feature.auth.AUTH_GRAPH_ROUTE
import fit.asta.health.feature.auth.authRoute
import fit.asta.health.feature.auth.navigateToAuth
import fit.asta.health.feature.feedback.feedbackRoute
import fit.asta.health.feature.feedback.navigateToFeedback
import fit.asta.health.feature.onboarding.ONBOARDING_GRAPH_ROUTE
import fit.asta.health.feature.onboarding.onboardingRoute
import fit.asta.health.feature.orders.navigateToOrders
import fit.asta.health.feature.orders.ordersRoute
import fit.asta.health.feature.profile.basicProfileRoute
import fit.asta.health.feature.profile.navigateToBasicProfile
import fit.asta.health.feature.settings.settingScreens
import fit.asta.health.feature.settings.view.SettingsUiEvent
import fit.asta.health.main.view.HOME_GRAPH_ROUTE
import fit.asta.health.main.view.homeScreen
import fit.asta.health.main.view.navigateToHome
import fit.asta.health.payment.PaymentActivity
import fit.asta.health.profile.CreateProfileLayout
import fit.asta.health.profile.ProfileContent
import fit.asta.health.referral.navigateToReferral
import fit.asta.health.referral.referralRoute
import fit.asta.health.scheduler.ui.navigation.schedulerNavigation
import fit.asta.health.subscription.navigateToSubscription
import fit.asta.health.subscription.subscriptionRoute
import fit.asta.health.testimonials.ui.testimonialsRoute
import fit.asta.health.tools.breathing.nav.breathingNavigation
import fit.asta.health.tools.exercise.nav.exerciseNavigation
import fit.asta.health.tools.meditation.nav.meditationNavigation
import fit.asta.health.tools.sunlight.nav.sunlightNavigation
import fit.asta.health.tools.water.nav.waterToolNavigation
import fit.asta.health.wallet.navigateToWallet
import fit.asta.health.wallet.walletRoute
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val deepLinkUrl: String = "https://www.asta.com"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun MainNavHost(isConnected: Boolean) {
    val navController = rememberNavController()
    if (!isConnected) {
        Box(modifier = Modifier.fillMaxSize()) {
            AppErrorScreen {}
        }
    }

    val mainViewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current

    val startDestination = if (mainViewModel.isAuth()) {
        HOME_GRAPH_ROUTE
    } else {
        val onboardingShown by mainViewModel.onboardingStatus.collectAsStateWithLifecycle()
        if (onboardingShown) {
            AUTH_GRAPH_ROUTE
        } else {
            ONBOARDING_GRAPH_ROUTE
        }
    }
    Log.d("TAG", "MainNavHost: $startDestination")

    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = startDestination
    ) {
        onboardingRoute(navController::navigateToAuth)
        authRoute(navController::navigateToBasicProfile, navController::navigateToWebView)
        basicProfileRoute(navController::navigateToHome)
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
        testimonialsRoute(navController)
        schedulerNavigation(navController, onBack = { navController.navigateUp() })

        settingScreens { key ->
            when (key) {
                SettingsUiEvent.Orders -> {
                    navController.navigateToOrders()
                }

                SettingsUiEvent.NavigateToAuth -> {
                    navController.navigateToAuth()
                }

                SettingsUiEvent.NavigateToSubscription -> {
                    navController.navigateToSubscription()
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

                SettingsUiEvent.RATE -> {
                    context.rateUs(BuildConfig.APPLICATION_ID)
                }


                SettingsUiEvent.FEEDBACK -> {
                    navController.navigateToFeedback(context.getString(R.string.application_fid))
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
        referralRoute(navController::popBackStack)
        walletRoute(navController::popBackStack)
        webView()
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}