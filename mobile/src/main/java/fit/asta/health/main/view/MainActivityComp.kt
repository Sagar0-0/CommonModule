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
import androidx.navigation.compose.composable
import fit.asta.health.R
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.maps.vm.MapsViewModel
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.shareApp
import fit.asta.health.main.Graph
import fit.asta.health.main.MainViewModel
import fit.asta.health.navigation.today.view.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.homeScreen(
    navController: NavController,
) {
    composable(Graph.Home.route) {
        val context = LocalContext.current

        val authViewModel: AuthViewModel = hiltViewModel()
        val mainViewModel: MainViewModel = hiltViewModel()
        val mapsViewModel: MapsViewModel = hiltViewModel()

        val isLocationEnabled by mapsViewModel.isLocationEnabled.collectAsStateWithLifecycle()
        val notificationEnabled by mainViewModel.notificationsEnabled.collectAsStateWithLifecycle()
        val currentAddressState by mapsViewModel.currentAddressStringState.collectAsStateWithLifecycle()

        val locationRequestLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == AppCompatActivity.RESULT_OK)
                    mapsViewModel.updateCurrentLocationData(context)
                else {
                    if (!isLocationEnabled) {
                        Toast.makeText(
                            context,
                            "Location services are required to update the location.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

        val permissionResultLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { perms ->
                perms.keys.forEach loop@{ perm ->
                    if ((perms[perm] == true) && (perm == Manifest.permission.ACCESS_FINE_LOCATION || perm == Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        PrefUtils.setLocationPermissionRejectedCount(context, 1)
                        mapsViewModel.enableLocationRequest(context) {
                            locationRequestLauncher.launch(it)
                        }
                        return@loop
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.location_access_required),
                            Toast.LENGTH_SHORT
                        ).show()
                        PrefUtils.setLocationPermissionRejectedCount(
                            context,
                            PrefUtils.getLocationPermissionRejectedCount(context) + 1
                        )
                    }
                }
            }


        fun enableLocationAndUpdateAddress() {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                mapsViewModel.enableLocationRequest(context) { intent ->
                    locationRequestLauncher.launch(intent)
                }
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
                    } else {
                        navController.navigate(Graph.Scheduler.route)
                    }
                }
            }
        }

        MainActivityLayout(
            currentAddressState = currentAddressState,
            profileImageUri = authViewModel.getUser()?.photoUrl,
            isNotificationEnabled = notificationEnabled,
            onNav = {
                when (it) {
                    Graph.Scheduler.route -> {
                        checkPermissionAndLaunchScheduler()
                    }

                    else -> {
                        navController.navigate(it)
                    }
                }
            },
            onSchedule = { hourMinAmPm ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = Utils.HourMinAmPmKey,
                    value = hourMinAmPm
                )
            },
            onClick = { key ->
                when (key) {
                    MainTopBarActions.Location -> {
                        enableLocationAndUpdateAddress()
//                        navController.navigate(Graph.Address.route)
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