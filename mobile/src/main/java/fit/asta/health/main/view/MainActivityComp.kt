package fit.asta.health.main.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.BuildConfig
import fit.asta.health.R
import fit.asta.health.common.utils.Constants.BREATHING_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.EXERCISE_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.HourMinAmPmKey
import fit.asta.health.common.utils.Constants.MEDITATION_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.SCHEDULER_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.SUNLIGHT_GRAPH_ROUTE
import fit.asta.health.common.utils.Constants.WATER_GRAPH_ROUTE
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.shareReferralCode
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.feature.breathing.nav.navigateToBreathing
import fit.asta.health.feature.exercise.nav.navigateToExercise
import fit.asta.health.feature.profile.profile.navigateToProfile
import fit.asta.health.feature.scheduler.ui.navigation.navigateToScheduler
import fit.asta.health.feature.settings.navigateToSettings
import fit.asta.health.feature.sleep.view.navigation.SLEEP_GRAPH_ROUTE
import fit.asta.health.feature.sleep.view.navigation.navigateToSleep
import fit.asta.health.feature.sunlight.nav.navigateToSunlight
import fit.asta.health.feature.walking.nav.STEPS_GRAPH_ROUTE
import fit.asta.health.feature.walking.nav.navigateToStepsCounter
import fit.asta.health.feature.walking.nav.navigateToStepsCounterProgress
import fit.asta.health.feature.water.nav.navigateToWater
import fit.asta.health.main.Graph
import fit.asta.health.main.MainViewModel
import fit.asta.health.meditation.nav.navigateToMeditation
import fit.asta.health.navigation.alarms.navigateToAllAlarms
import fit.asta.health.navigation.tools.ui.view.ToolsHomeUiEvent
import fit.asta.health.navigation.tools.ui.viewmodel.HomeViewModel
import fit.asta.health.subscription.SubscriptionViewModel
import fit.asta.health.subscription.navigateToFinalPaymentScreen
import fit.asta.health.subscription.navigateToSubscriptionDurations

const val HOME_ROUTE = "home_route"

fun NavGraphBuilder.homeScreen(
    navController: NavController,
) {
    composable(HOME_ROUTE) {

        val context = LocalContext.current

        val mainViewModel: MainViewModel = hiltViewModel()
        val referralDataState by mainViewModel.referralDataState.collectAsStateWithLifecycle()

        val subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
        val subscriptionCategoryState by
        subscriptionViewModel.subscriptionCategoryDataState.collectAsStateWithLifecycle()
        val offersDataState by
        subscriptionViewModel.offersDataState.collectAsStateWithLifecycle()

        val homeViewModel: HomeViewModel = hiltViewModel()
        val toolsHomeDataState by homeViewModel.toolsHomeDataState.collectAsStateWithLifecycle()

        LaunchedEffect(
            key1 = Unit,
            block = {
                if (referralDataState !is UiState.Success) {
                    mainViewModel.getReferralData()
                }
            }
        )

        val notificationState by mainViewModel.notificationState.collectAsStateWithLifecycle()
        val sessionState by mainViewModel.sessionState.collectAsStateWithLifecycle()
        val currentAddressName by mainViewModel.currentAddressName.collectAsStateWithLifecycle()
        val locationPermissionRejectedCount by mainViewModel.locationPermissionRejectedCount.collectAsStateWithLifecycle()
        val isLocationEnabled by mainViewModel.isLocationEnabled.collectAsStateWithLifecycle()
        val refCode =
            (referralDataState as? UiState.Success)?.data?.referralDetails?.refCode ?: ""

        val permissionResultLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { perms ->
                perms.keys.forEach loop@{ perm ->
                    if ((perms[perm] == true) && (perm == Manifest.permission.ACCESS_FINE_LOCATION || perm == Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        mainViewModel.updateLocationPermissionRejectedCount(1)
                        mainViewModel.checkPermissionAndUpdateCurrentAddress()
                        return@loop
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.location_access_required),
                            Toast.LENGTH_SHORT
                        ).show()
                        mainViewModel.updateLocationPermissionRejectedCount(
                            locationPermissionRejectedCount + 1
                        )
                    }
                }
            }
        val locationRequestLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == AppCompatActivity.RESULT_OK)
                    mainViewModel.checkPermissionAndUpdateCurrentAddress()
                else {
                    if (!mainViewModel.setIsLocationEnabled()) {
                        Toast.makeText(
                            context,
                            R.string.location_access_required.toStringFromResId(context),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

        fun enableLocationAndUpdateAddress() {
            if (mainViewModel.isPermissionGranted() && mainViewModel.setIsLocationEnabled()) {
                mainViewModel.checkPermissionAndUpdateCurrentAddress()
            } else {
                if (!mainViewModel.isPermissionGranted()) {
                    if (locationPermissionRejectedCount >= 2) {
                        Toast.makeText(
                            context,
                            R.string.grant_locations_permission_to_continue.toStringFromResId(
                                context
                            ),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        with(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)) {
                            data = Uri.fromParts("package", context.packageName, null)
                            addCategory(Intent.CATEGORY_DEFAULT)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                            context.startActivity(this)
                        }
                    } else {
                        permissionResultLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }
                if (!isLocationEnabled) {
                    mainViewModel.enableLocationRequest { intent ->
                        locationRequestLauncher.launch(intent)
                    }
                }
            }
        }

        val checkPermissionAndLaunchScheduler =
            checkPermissionAndLaunchScheduler(context, navController)

        HomeScreensLayout(
            currentAddressState = currentAddressName,
            refCode = refCode,
            subscriptionCategoryState = subscriptionCategoryState,
            offersDataState = offersDataState,
            toolsHomeDataState = toolsHomeDataState,
            profileImageUri = mainViewModel.getUser()?.photoUrl,
            notificationState = notificationState,
            sessionState = sessionState,
            onLocation = { enableLocationAndUpdateAddress() },
            onEvent = { event ->
                when (event) {
                    is ToolsHomeUiEvent.NavigateToSubscriptionDurations -> {
                        navController.navigateToSubscriptionDurations(event.categoryId)
                    }

                    ToolsHomeUiEvent.LoadToolsData -> {
                        homeViewModel.loadHomeData()
                    }

                    is ToolsHomeUiEvent.NavigateToFinalPayment -> {
                        navController.navigateToFinalPaymentScreen(
                            event.categoryId,
                            event.productId
                        )
                    }

                    is ToolsHomeUiEvent.LoadSubscriptionCategoryData -> {
                        subscriptionViewModel.getSubscriptionCategoryData()
                    }

                    is ToolsHomeUiEvent.LoadOffersData -> {
                        subscriptionViewModel.getOffersData()
                    }
                }
            },
            onNav = { destination ->
                when (destination) {
                    SCHEDULER_GRAPH_ROUTE -> {
                        checkPermissionAndLaunchScheduler()
                    }

                    SUNLIGHT_GRAPH_ROUTE -> {
                        navController.navigateToSunlight()
                    }

                    WATER_GRAPH_ROUTE -> {
                        navController.navigateToWater()
                    }

                    SLEEP_GRAPH_ROUTE -> {
                        navController.navigateToSleep()
                    }

                    MEDITATION_GRAPH_ROUTE -> {
                        navController.navigateToMeditation()
                    }

                    BREATHING_GRAPH_ROUTE -> {
                        navController.navigateToBreathing()
                    }

                    EXERCISE_GRAPH_ROUTE -> {
                        navController.navigateToExercise()
                    }

                    STEPS_GRAPH_ROUTE -> {
                        navController.navigateToStepsCounter()
                    }

                    else -> {
                        navController.navigate(destination)
                    }
                }
            },
            onWalkingTool = { navController.navigateToStepsCounterProgress() },
            onSchedule = { hourMinAmPm ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = HourMinAmPmKey,
                    value = hourMinAmPm
                )
            },
            onClick = { key ->
                when (key) {
                    MainTopBarActions.Location -> {
                        enableLocationAndUpdateAddress()
                    }

                    MainTopBarActions.Notification -> {
                        mainViewModel.setNotificationStatus(!notificationState)
                    }

                    MainTopBarActions.Settings -> {
                        navController.navigateToSettings()
                    }

                    MainTopBarActions.Profile -> {
                        navController.navigateToProfile()
                    }

                    MainTopBarActions.Share -> {
                        context.shareReferralCode(refCode, BuildConfig.APPLICATION_ID)
                    }

                    MainTopBarActions.Schedule -> {
                        navController.navigateToAllAlarms()
                    }
                }
            }
        )
    }
}

@Composable
fun checkPermissionAndLaunchScheduler(
    context: Context,
    navController: NavController
): () -> Unit {
    val notificationPermissionResultLauncher: ActivityResultLauncher<String> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { perms ->
            if (perms) {
                Toast.makeText(
                    context,
                    "Notification is recommended for better functionality.",
                    Toast.LENGTH_SHORT
                ).show()
                PrefManager.setNotificationPermissionRejectedCount(context, 0)
                navController.navigate(Graph.Scheduler.route)
            } else {
                Toast.makeText(
                    context,
                    "Notification is recommended for better functionality.",
                    Toast.LENGTH_SHORT
                ).show()
                PrefManager.setNotificationPermissionRejectedCount(
                    context,
                    PrefManager.getNotificationPermissionRejectedCount(context) + 1
                )

            }
        }

    val checkPermissionAndLaunchScheduler = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            PrefManager.setNotificationPermissionRejectedCount(context, 0)
            navController.navigateToScheduler()
        } else {
            if (PrefManager.getNotificationPermissionRejectedCount(context) > 2) {
                Toast.makeText(
                    context,
                    "Please allow Notification permission access.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                with(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)) {
                    data = Uri.fromParts("package", context.packageName, null)
                    addCategory(Intent.CATEGORY_DEFAULT)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    context.startActivity(this)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    navController.navigateToScheduler()
                }
            }
        }
    }
    return checkPermissionAndLaunchScheduler
}
