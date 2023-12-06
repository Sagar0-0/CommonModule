package fit.asta.health.main.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
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
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.common.utils.shareReferralCode
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.feature.scheduler.ui.navigation.navigateToScheduler
import fit.asta.health.feature.settings.navigateToSettings
import fit.asta.health.feature.walking.nav.STEPS_GRAPH_ROUTE
import fit.asta.health.feature.walking.nav.navigateToStepsCounter
import fit.asta.health.feature.walking.nav.navigateToStepsCounterProgress
import fit.asta.health.main.Graph
import fit.asta.health.main.MainViewModel
import fit.asta.health.meditation.nav.navigateToMeditation
import fit.asta.health.navigation.today.ui.view.AlarmEvent
import fit.asta.health.navigation.today.ui.view.AllAlarms
import fit.asta.health.navigation.today.ui.vm.AllAlarmViewModel
import fit.asta.health.navigation.tools.ui.view.HomeScreenUiEvent
import fit.asta.health.payment.PaymentActivity
import fit.asta.health.subscription.remote.model.Offer
import fit.asta.health.subscription.view.ProceedToBuyScreen
import fit.asta.health.subscription.view.SubscriptionDurationsScreen
import fit.asta.health.subscription.vm.SubscriptionViewModel
import fit.asta.health.tools.breathing.nav.navigateToBreathing
import fit.asta.health.tools.exercise.nav.navigateToExercise
import fit.asta.health.tools.sunlight.nav.navigateToSunlight
import fit.asta.health.tools.water.nav.navigateToWater

const val HOME_GRAPH_ROUTE = "graph_home"
const val HOME_ROUTE = "home_route"
const val ALL_ALARMS_ROUTE = "all_alarms"
private const val SUBSCRIPTION_DURATION_ROUTE = "graph_duration_route/{subType}"
private const val SUBSCRIPTION_FINAL_SCREEN = "graph_final_route/{subType}/{durType}?offer={offer}"
fun NavController.navigateToSubscriptionDurations(subType: String) {
    Log.d("Main", "navigateToSubscriptionDurations: subtype = $subType")
    this.navigate(SUBSCRIPTION_DURATION_ROUTE.replace("subType", subType))
}

fun NavController.navigateToPaymentWithOffer(offer: Offer) {
    val gson: Gson = GsonBuilder().create()
    val offerJson = gson.toJson(offer).replace("/", "|")
    this.navigate(
        SUBSCRIPTION_FINAL_SCREEN
            .replace("subType", offer.sub.subType)
            .replace("durType", offer.sub.durType)
            .removeSuffix("{offer}")
                + "{$offerJson}"
    )
}

fun NavController.navigateToFinalPaymentScreen(subType: String, durType: String) {
    this.navigate(
        SUBSCRIPTION_FINAL_SCREEN
            .replace("subType", subType)
            .replace("durType", durType)
    )
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    if (navOptions == null) {
        this.navigate(HOME_GRAPH_ROUTE) {
            popUpToTop(this@navigateToHome)
        }
    } else {
        this.navigate(HOME_GRAPH_ROUTE, navOptions)
    }
}

fun NavGraphBuilder.homeScreen(
    navController: NavController,
) {
    navigation(
        route = HOME_GRAPH_ROUTE,
        startDestination = HOME_ROUTE,
    ) {
        composable(HOME_ROUTE) {

            val context = LocalContext.current

            val mainViewModel: MainViewModel = hiltViewModel()
            val subscriptionViewModel: SubscriptionViewModel =
                it.sharedViewModel(navController = navController)
            LaunchedEffect(
                key1 = Unit,
                block = {
                    mainViewModel.getReferralData()
                }
            )

            val subscriptionResponse =
                subscriptionViewModel.state.collectAsStateWithLifecycle().value
            Log.d("Main", "homeScreen: subscriptionResponse $subscriptionResponse")
            val notificationState by mainViewModel.notificationState.collectAsStateWithLifecycle()
            val sessionState by mainViewModel.sessionState.collectAsStateWithLifecycle()
            val currentAddressName by mainViewModel.currentAddressName.collectAsStateWithLifecycle()
            val locationPermissionRejectedCount by mainViewModel.locationPermissionRejectedCount.collectAsStateWithLifecycle()
            val isLocationEnabled by mainViewModel.isLocationEnabled.collectAsStateWithLifecycle()
            val referralDataState =
                mainViewModel.referralDataState.collectAsStateWithLifecycle().value
            val refCode = (referralDataState as? UiState.Success)?.data?.referralCode?.refCode ?: ""

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

            MainActivityLayout(
                currentAddressState = currentAddressName,
                refCode = refCode,
                subscriptionResponse = (subscriptionResponse as? UiState.Success)?.data,
                profileImageUri = mainViewModel.getUser()?.photoUrl,
                notificationState = notificationState,
                sessionState = sessionState,
                onLocation = { enableLocationAndUpdateAddress() },
                onEvent = { event ->
                    when (event) {
                        is HomeScreenUiEvent.NavigateToSubscriptionDurations -> {
                            navController.navigateToSubscriptionDurations(event.subType)
                        }

                        is HomeScreenUiEvent.NavigateWithOffer -> {
                            navController.navigateToPaymentWithOffer(event.offer)
                        }
                    }
                },
                onNav = {
                    when (it) {
                        SCHEDULER_GRAPH_ROUTE -> {
                            checkPermissionAndLaunchScheduler()
                        }

                        SUNLIGHT_GRAPH_ROUTE -> {
                            navController.navigateToSunlight()
                        }

                        WATER_GRAPH_ROUTE -> {
                            navController.navigateToWater()
                        }
//                    SLEEP_GRAPH_ROUTE->{navController}
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
                            navController.navigate(it)
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
                            navController.navigate(Graph.Profile.route)
                        }

                        MainTopBarActions.Share -> {
                            context.shareReferralCode(refCode, BuildConfig.APPLICATION_ID)
                        }

                        MainTopBarActions.Schedule -> {
                            navController.navigate(ALL_ALARMS_ROUTE)
                        }
                    }
                }
            )
        }
        composable(ALL_ALARMS_ROUTE) {
            val vm: AllAlarmViewModel = hiltViewModel()
            val list by vm.alarmList.collectAsStateWithLifecycle()
            val context = LocalContext.current
            val checkPermissionAndLaunchScheduler =
                checkPermissionAndLaunchScheduler(context, navController)
            AllAlarms(list = list,
                onEvent = {
                    when (it) {
                        is AlarmEvent.SetAlarmState -> {
                            vm.changeAlarmState(
                                state = it.state,
                                alarm = it.alarm,
                                context = it.context
                            )
                        }

                        is AlarmEvent.SetAlarm -> {
                            vm.setAlarmPreferences(999)
                        }

                        is AlarmEvent.EditAlarm -> {
                            vm.setAlarmPreferences(it.alarmId)
                            checkPermissionAndLaunchScheduler()
                        }

                        is AlarmEvent.NavSchedule -> {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = HourMinAmPmKey,
                                value = it.hourMinAmPm
                            )
                            checkPermissionAndLaunchScheduler()
                        }

                        is AlarmEvent.OnBack -> {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
        composable(SUBSCRIPTION_DURATION_ROUTE) {
            val context = LocalContext.current
            val subscriptionViewModel: SubscriptionViewModel =
                it.sharedViewModel(navController = navController)
            val subscriptionResponse =
                subscriptionViewModel.state.collectAsStateWithLifecycle().value
            val subType = it.arguments?.getString("subType")!!
            when (subscriptionResponse) {
                is UiState.Success -> {
                    val category =
                        subscriptionResponse.data.subscriptionPlans.categories.firstOrNull { cat ->
                            cat.subscriptionType == subType
                        }
                    category?.let {
                        SubscriptionDurationsScreen(
                            planSubscriptionPlanCategory = category,
                            onBack = { navController.popBackStack() },
                            onClick = { subType, durType ->
                                navController.navigateToFinalPaymentScreen(subType, durType)
                            }
                        )
                    }
                }

                else -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            "Something went wrong!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    }

                }
            }

        }

        composable(
            route = SUBSCRIPTION_FINAL_SCREEN,
            arguments = listOf(
                navArgument("offer") {
                    defaultValue = null
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val context = LocalContext.current

            val subType = it.arguments?.getString("subType")!!
            val durType = it.arguments?.getString("durType")!!
            val offerJson = it.arguments?.getString("offer")?.replace("|", "/")
            Log.d("Main", "homeScreen: offerJson: $offerJson")
            val gson = GsonBuilder().create()
            val offer = offerJson?.let {
                gson.fromJson(offerJson, Offer::class.java)
            }
            val subscriptionViewModel: SubscriptionViewModel =
                it.sharedViewModel(navController = navController)
            val subscriptionResponse =
                subscriptionViewModel.state.collectAsStateWithLifecycle().value
            when (subscriptionResponse) {
                is UiState.Success -> {
                    val category =
                        subscriptionResponse.data.subscriptionPlans.categories.firstOrNull { cat ->
                            cat.subscriptionType == subType
                        }
                    category?.let {
                        ProceedToBuyScreen(
                            offer = offer,
                            subscriptionPlanCategory = category,
                            durationType = durType,
                            onBack = { navController.popBackStack() },
                            onProceedToBuy = { order ->
                                PaymentActivity.launch(context, order) {
                                    navController.popBackStack()
                                }
                            }
                        )
                    }

                }

                else -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            "Something went wrong!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    }

                }
            }
        }
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
