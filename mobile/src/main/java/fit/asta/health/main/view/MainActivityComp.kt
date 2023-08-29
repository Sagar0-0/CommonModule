package fit.asta.health.main.view

import android.Manifest
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
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
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.common.utils.shareApp
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.feature.scheduler.ui.navigation.navigateToScheduler
import fit.asta.health.feature.settings.navigateToSettings
import fit.asta.health.main.Graph
import fit.asta.health.main.MainViewModel
import fit.asta.health.tools.breathing.nav.navigateToBreathing
import fit.asta.health.tools.exercise.nav.navigateToExercise
import fit.asta.health.tools.meditation.nav.navigateToMeditation
import fit.asta.health.tools.sunlight.nav.navigateToSunlight
import fit.asta.health.tools.water.nav.navigateToWater

const val HOME_GRAPH_ROUTE = "graph_home"
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
    composable(HOME_GRAPH_ROUTE) {

        val context = LocalContext.current

        val mainViewModel: MainViewModel = hiltViewModel()

        val notificationEnabled by mainViewModel.notificationsEnabled.collectAsStateWithLifecycle()
        val currentAddressName by mainViewModel.currentAddressName.collectAsStateWithLifecycle()
        val locationPermissionRejectedCount by mainViewModel.locationPermissionRejectedCount.collectAsStateWithLifecycle()
        val isLocationEnabled by mainViewModel.isLocationEnabled.collectAsStateWithLifecycle()
        val isPermissionGranted by mainViewModel.isPermissionGranted.collectAsStateWithLifecycle()

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
                    mainViewModel.setIsLocationEnabled()
                    if (!isLocationEnabled) {
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
            mainViewModel.setIsPermissionGranted()
            mainViewModel.setIsLocationEnabled()
            if (isPermissionGranted && isLocationEnabled) {
                mainViewModel.checkPermissionAndUpdateCurrentAddress()
            } else {
                if (!isPermissionGranted) {
                    if (locationPermissionRejectedCount >= 2) {
                        Toast.makeText(
                            context,
                            R.string.location_access_required.toStringFromResId(context),
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
                    PrefManager.setNotificationPermissionRejectedCount(context, 1)
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
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                PrefManager.setNotificationPermissionRejectedCount(context, 1)
                navController.navigateToScheduler()
            } else {
                if (PrefManager.getNotificationPermissionRejectedCount(context) >= 2) {
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

        MainActivityLayout(
            currentAddressState = currentAddressName,
            profileImageUri = mainViewModel.getUser()?.photoUrl,
            isNotificationEnabled = notificationEnabled,
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

                    else -> {
                        navController.navigate(it)
                    }
                }
            },
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
                        mainViewModel.setNotificationStatus(!notificationEnabled)
                    }

                    MainTopBarActions.Settings -> {
                        navController.navigateToSettings()
                    }

                    MainTopBarActions.Profile -> {
                        navController.navigate(Graph.Profile.route)
                    }

                    MainTopBarActions.Share -> {
                        context.shareApp(context.packageName)
                    }
                }
            }
        )
    }
}
