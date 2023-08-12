package fit.asta.health.common.maps.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.R
import fit.asta.health.common.maps.modal.AddressScreen
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.utils.LocationProviderChangedReceiver
import fit.asta.health.common.maps.vm.MapsViewModel
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.rememberLifecycleEvent
import fit.asta.health.main.Graph

fun NavGraphBuilder.addressScreens(navController: NavHostController) {
    composable(route = Graph.Address.route) {
        val nestedNavController = rememberNavController()
        val mapsViewModel: MapsViewModel = hiltViewModel()
        Setup(mapsViewModel, navController)

        NavHost(
            navController = nestedNavController,
            route = Graph.Address.route,
            startDestination = AddressScreen.SavedAdd.route
        ) {
            composable(
                route = AddressScreen.SavedAdd.route
            ) {
                LaunchedEffect(Unit) { mapsViewModel.getAllAddresses() }
                SavedAddressesScreen(
                    navHostController = nestedNavController,
                    mapsViewModel = mapsViewModel,
                    onBackPressed = navController::navigateUp
                )
            }

            composable(
                route = AddressScreen.Map.route + "/{address}"
            ) {
                val gson: Gson = GsonBuilder().create()
                val addJson = it.arguments?.getString("address")!!.replace("|", "/")
                val myAddressItem = gson.fromJson(addJson, AddressesResponse.MyAddress::class.java)
                MapScreen(
                    myAddressItem = myAddressItem,
                    mapsViewModel = mapsViewModel,
                    onBackPressed = nestedNavController::navigateUp
                )
            }
        }
    }
}

@Composable
private fun Setup(mapsViewModel: MapsViewModel, navController: NavHostController) {
    val context = LocalContext.current

    DisposableEffect(context) {
        val br = LocationProviderChangedReceiver(
            onEnabled = {
                mapsViewModel.isLocationEnabled.value = true
                mapsViewModel.updateCurrentLocationData(context)
            },
            onDisabled = {
                mapsViewModel.isLocationEnabled.value = false
            }
        )
        br.register(context)
        onDispose {
            br.unregister(context)
        }
    }

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        Log.e("TAG", lifecycleEvent.name)
        when (lifecycleEvent) {
            Lifecycle.Event.ON_RESUME -> {
                mapsViewModel.isPermissionGranted.value =
                    (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
            }

            Lifecycle.Event.ON_START -> {
                mapsViewModel.updateCurrentLocationData(context)
            }

            else -> {}
        }
    }

    val permissionResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            perms.keys.forEach loop@{ perm ->
                if ((perms[perm] == true) && (perm == Manifest.permission.ACCESS_FINE_LOCATION || perm == Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    PrefManager.setLocationPermissionRejectedCount(context, 1)
                    return@loop
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.location_access_required),
                        Toast.LENGTH_SHORT
                    ).show()
                    PrefManager.setLocationPermissionRejectedCount(
                        context,
                        PrefManager.getLocationPermissionRejectedCount(context) + 1
                    )
                    navController.navigateUp()
                }
            }
        }

    val locationRequestLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            if (activityResult.resultCode == AppCompatActivity.RESULT_OK)
                mapsViewModel.updateCurrentLocationData(context)
            else {
                if (!mapsViewModel.isLocationEnabled.value) {
                    Toast.makeText(
                        context,
                        "Location access is mandatory to use this feature!!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    navController.navigateUp()
                }
            }
        }

    val isLocationEnabled by mapsViewModel.isLocationEnabled.collectAsStateWithLifecycle()
    val isPermissionGranted by mapsViewModel.isPermissionGranted.collectAsStateWithLifecycle()

    LaunchedEffect(isPermissionGranted) {
        if (!isPermissionGranted) {
            if (PrefManager.getLocationPermissionRejectedCount(context) >= 2) {
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
                navController.navigateUp()
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

    LaunchedEffect(isLocationEnabled) {
        if (!isLocationEnabled) {
            mapsViewModel.enableLocationRequest(context) { intent ->
                locationRequestLauncher.launch(intent)
            }
        }
    }
}
