package fit.asta.health.main.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
    onNav: (Graph) -> Unit,
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

        MainActivityLayout(
            locationName = locationName,
            profileImageUri = authViewModel.getUser()?.photoUrl,
            isNotificationEnabled = notificationEnabled,
            onNav = onNav,
            onClick = { key ->
                when (key) {
                    MainTopBarActions.Location -> {
                        checkPermissionAndLaunchMapsActivity()
                    }

                    MainTopBarActions.Notification -> {
                        mainViewModel.setNotificationStatus(!notificationEnabled)
                    }

                    MainTopBarActions.Settings -> {
                        onNav(Graph.Settings)
                    }

                    MainTopBarActions.Profile -> {
                        onNav(Graph.Profile)
                    }

                    MainTopBarActions.Share -> {
                        context.shareApp()
                    }
                }
            }
        )
    }

}