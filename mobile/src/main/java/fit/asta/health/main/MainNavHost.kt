package fit.asta.health.main

import android.app.AlarmManager
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import fit.asta.health.BuildConfig
import fit.asta.health.common.ui.navigateToWebView
import fit.asta.health.common.ui.webView
import fit.asta.health.common.utils.getBaseUrl
import fit.asta.health.common.utils.getCurrentBuildVersion
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.shareReferralCode
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.feature.address.addressRoute
import fit.asta.health.feature.address.navigateToAddress
import fit.asta.health.feature.auth.authRoute
import fit.asta.health.feature.auth.navigateToAuth
import fit.asta.health.feature.breathing.nav.breathingNavigation
import fit.asta.health.feature.exercise.nav.exerciseNavigation
import fit.asta.health.feature.feedback.feedbackRoute
import fit.asta.health.feature.orders.navigateToOrders
import fit.asta.health.feature.orders.ordersRoute
import fit.asta.health.feature.profile.basic.basicProfileRoute
import fit.asta.health.feature.profile.profile.profileRoute
import fit.asta.health.feature.scheduler.ui.navigation.navigateToScheduler
import fit.asta.health.feature.scheduler.ui.navigation.schedulerNavigation
import fit.asta.health.feature.settings.settingScreens
import fit.asta.health.feature.settings.view.SettingsUiEvent
import fit.asta.health.feature.sleep.view.navigation.sleepNavGraph
import fit.asta.health.feature.testimonials.navigation.testimonialNavGraph
import fit.asta.health.feature.walking.nav.STEPS_GRAPH_ROUTE
import fit.asta.health.feature.walking.nav.stepsCounterNavigation
import fit.asta.health.feature.water.nav.waterToolNavigation
import fit.asta.health.main.view.AlarmPermDialogue
import fit.asta.health.main.view.homeScreen
import fit.asta.health.meditation.nav.meditationNavigation
import fit.asta.health.navigation.alarms.allAlarmsRoute
import fit.asta.health.payment.PaymentActivity
import fit.asta.health.referral.navigateToReferral
import fit.asta.health.referral.referralRoute
import fit.asta.health.resources.strings.R
import fit.asta.health.subscription.navigateToSubscription
import fit.asta.health.subscription.subscriptionCheckoutRoute
import fit.asta.health.subscription.subscriptionDurationRoute
import fit.asta.health.sunlight.feature.navigation.sunlightNavigation
import fit.asta.health.wallet.navigateToWallet
import fit.asta.health.wallet.walletRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainNavHost(
    isConnected: Boolean,
    startDestination: String
) {
//    if (!isConnected) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            AppInternetErrorDialog {}
//        }
//    }
    var showDialogue by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
            showDialogue = alarmManager?.canScheduleExactAlarms() == false
        }
    }
    if (showDialogue)
        AppDialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.SecureOn
            )
        ) {
            AlarmPermDialogue {
                Intent().also { intent ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        context.startActivity(intent)
                    }
                }
            }
        }
    MainNavHost(startDestination)
}

@Composable
private fun MainNavHost(startDestination: String) {
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = startDestination
    ) {
        authRoute(navController, navController::navigateToWebView)
        basicProfileRoute()

        homeScreen(navController)

        allAlarmsRoute(navController)

        subscriptionDurationRoute(navController)
        subscriptionCheckoutRoute(navController)

        profileRoute(
            navController = navController,
            navigateToOrders = {
                navController.navigateToOrders()
            },
            navigateToWallet = {
                navController.navigateToWallet()
            },
            navigateToSubscription = {
                Toast.makeText(context, "Feature not implemented yet!", Toast.LENGTH_SHORT).show()
//                navController.navigateToSubscription()
            }
        )

        breathingNavigation(navController, onBack = { navController.navigateUp() })
        waterToolNavigation(navController, onBack = { navController.navigateUp() })
        meditationNavigation(navController, onBack = { navController.navigateUp() })
        sunlightNavigation(navController, onBack = { navController.navigateUp() })
        sleepNavGraph(navController, onBack = { navController.navigateUp() })
        exerciseNavigation(navController, onBack = { navController.navigateUp() })
        testimonialNavGraph(navController, onBack = { navController.navigateUp() })
        schedulerNavigation(navController, onBack = { navController.navigateUp() })
        stepsCounterNavigation(
            navController = navController, sessionState = startDestination == STEPS_GRAPH_ROUTE,
            onScheduler = { navController.navigateToScheduler() },
            onBack = { navController.navigateUp() })

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
                        getBaseUrl(context.getString(R.string.url_terms_of_use)),
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigateToWebView(url)
                }

                SettingsUiEvent.PRIVACY -> {
                    val url = URLEncoder.encode(
                        getBaseUrl(context.getString(R.string.url_privacy_policy)),
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
        ordersRoute(navController)

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

