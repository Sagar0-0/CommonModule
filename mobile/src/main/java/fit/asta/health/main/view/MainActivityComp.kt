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
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.R
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.location.maps.MapsActivity
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.common.utils.PermissionResultListener
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.shareApp
import fit.asta.health.main.Graph
import fit.asta.health.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.mainActivityComp(
    navController: NavController,
) {
    composable(Graph.Home.route) {

        val context = LocalContext.current
        val permissionResultListener = object : PermissionResultListener {
            override fun onGranted(perm: String) {
                if (perm == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    Toast.makeText(
                        context,
                        "Fine Location is recommended for better functionality.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                PrefUtils.setLocationPermissionRejectedCount(context, 1)
                MapsActivity.launch(context)
            }

            override fun onDenied(perm: String) {
                if (perm == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    Toast.makeText(
                        context,
                        "Precise Location is recommended for better functionality.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.location_access_required),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                PrefUtils.setLocationPermissionRejectedCount(
                    context,
                    PrefUtils.getLocationPermissionRejectedCount(context) + 1
                )
            }
        }
        val permissionResultLauncher: ActivityResultLauncher<Array<String>> =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { perms ->
                kotlin.run lit@{
                    perms.keys.forEach {
                        if (it == Manifest.permission.ACCESS_FINE_LOCATION || it == Manifest.permission.ACCESS_COARSE_LOCATION) {
                            if (perms[it] == true) {
                                permissionResultListener.onGranted(it)
                            } else {
                                permissionResultListener.onDenied(it)
                            }
                            return@lit
                        }
                    }
                }
            }

        val checkPermissionAndLaunchMapsActivity = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                PrefUtils.setLocationPermissionRejectedCount(context, 1)
                MapsActivity.launch(context)
            } else {
                if (PrefUtils.getLocationPermissionRejectedCount(context) >= 2) {
                    Toast.makeText(
                        context,
                        "Please allow Location permission access.",
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
        }

        val authViewModel: AuthViewModel = hiltViewModel()
        val mainViewModel: MainViewModel = hiltViewModel()

        val notificationEnabled by mainViewModel.notificationsEnabled.collectAsStateWithLifecycle()
        val locationName by mainViewModel.locationName.collectAsStateWithLifecycle()

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
                    PrefUtils.setNotificationPermissionRejectedCount(context, 1)
                    navController.navigate(Graph.Scheduler.route)
                } else {
                    Toast.makeText(
                        context,
                        "Notification is recommended for better functionality.",
                        Toast.LENGTH_SHORT
                    ).show()
                    PrefUtils.setNotificationPermissionRejectedCount(
                        context,
                        PrefUtils.getNotificationPermissionRejectedCount(context) + 1
                    )
                }
            }

        val checkPermissionAndLaunchScheduler = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                PrefUtils.setNotificationPermissionRejectedCount(context, 1)
                navController.navigate(Graph.Scheduler.route)
            } else {
                if (PrefUtils.getNotificationPermissionRejectedCount(context) >= 2) {
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
                    } else{
                        navController.navigate(Graph.Scheduler.route)
                    }
                }
            }
        }

        MainActivityLayout(
            locationName = locationName,
            profileImageUri = authViewModel.getUser()?.photoUrl,
            isNotificationEnabled = notificationEnabled,
            onNav = {
                when (it) {
                    Graph.Settings -> {
                        navController.navigate(Graph.Settings.route)
                    }

                    Graph.BreathingTool -> {
                        navController.navigate(Graph.BreathingTool.route)
                    }

                    Graph.WaterTool -> {
                        navController.navigate(Graph.WaterTool.route)
                    }

                    Graph.MeditationTool -> {
                        navController.navigate(Graph.MeditationTool.route)
                    }

                    Graph.SunlightTool -> {
                        navController.navigate(Graph.SunlightTool.route)
                    }

                    Graph.Profile -> {
                        navController.navigate(Graph.Profile.route)
                    }

                    Graph.Testimonials -> {
                        navController.navigate(Graph.Testimonials.route)
                    }

                    Graph.SleepTool -> {}
                    Graph.WalkingTool -> {}
                    Graph.Dance -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=dance")
                    }

                    Graph.Hiit -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=HIIT")
                    }

                    Graph.Workout -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=workout")
                    }

                    Graph.Yoga -> {
                        navController.navigate(Graph.ExerciseTool.route + "?activity=yoga")
                    }

                    Graph.Scheduler -> {
                        checkPermissionAndLaunchScheduler()
                    }

                    else -> {}
                }
            },
            onClick = { key ->
                when (key) {
                    MainTopBarActions.Location -> {
                        checkPermissionAndLaunchMapsActivity()
                    }

                    MainTopBarActions.Notification -> {
                        mainViewModel.setNotificationStatus(!notificationEnabled)
                    }

                    MainTopBarActions.Settings -> {
                        navController.navigate(Graph.Settings.route)
                    }

                    MainTopBarActions.Profile -> {
                        navController.navigate(Graph.Profile.route)
                    }

                    MainTopBarActions.Share -> {
                        context.shareApp()
                    }
                }
            }
        )
    }
}