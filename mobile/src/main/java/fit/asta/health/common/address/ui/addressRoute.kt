package fit.asta.health.common.address.ui

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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.R
import fit.asta.health.common.address.data.modal.AddressScreen
import fit.asta.health.common.address.data.modal.AddressesResponse
import fit.asta.health.common.address.data.utils.LocationProviderChangedReceiver
import fit.asta.health.common.address.ui.view.MapScreen
import fit.asta.health.common.address.ui.view.SavedAddressesScreen
import fit.asta.health.common.address.ui.vm.AddressViewModel
import fit.asta.health.common.utils.rememberLifecycleEvent

private const val ADDRESS_GRAPH_ROUTE = "graph_address"

fun NavController.navigateToAddress(navOptions: NavOptions?=null){
    this.navigate(ADDRESS_GRAPH_ROUTE,navOptions)
}

fun NavGraphBuilder.addressRoute(navController: NavHostController) {
    composable(ADDRESS_GRAPH_ROUTE) {
        val nestedNavController = rememberNavController()
        val addressViewModel: AddressViewModel = hiltViewModel()
        Setup(addressViewModel, navController)

        NavHost(
            navController = nestedNavController,
            route = ADDRESS_GRAPH_ROUTE,
            startDestination = AddressScreen.SavedAdd.route
        ) {
            composable(
                route = AddressScreen.SavedAdd.route
            ) {
                SavedAddressesScreen(
                    navHostController = nestedNavController,
                    addressViewModel = addressViewModel,
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
                    addressViewModel = addressViewModel,
                    onBackPressed = nestedNavController::navigateUp
                )
            }
        }
    }
}

@Composable
private fun Setup(addressViewModel: AddressViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val locationPermissionRejectedCount by addressViewModel.locationPermissionRejectedCount.collectAsStateWithLifecycle()

    DisposableEffect(context) {
        val br = LocationProviderChangedReceiver(
            onEnabled = {
                addressViewModel.isLocationEnabled.value = true
                addressViewModel.updateCurrentLocationData(context)
            },
            onDisabled = {
                addressViewModel.isLocationEnabled.value = false
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
                addressViewModel.isPermissionGranted.value =
                    (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
            }

            Lifecycle.Event.ON_START -> {
                addressViewModel.updateCurrentLocationData(context)
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
                    addressViewModel.updateLocationPermissionRejectedCount(1)
                    return@loop
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.location_access_required),
                        Toast.LENGTH_SHORT
                    ).show()
                    addressViewModel.updateLocationPermissionRejectedCount(
                        locationPermissionRejectedCount + 1
                    )
                    navController.popBackStack()
                }
            }
        }

    val locationRequestLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            if (activityResult.resultCode == AppCompatActivity.RESULT_OK)
                addressViewModel.updateCurrentLocationData(context)
            else {
                if (!addressViewModel.isLocationEnabled.value) {
                    Toast.makeText(
                        context,
                        "Location access is mandatory to use this feature!!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    navController.popBackStack()
                }
            }
        }

    val isLocationEnabled by addressViewModel.isLocationEnabled.collectAsStateWithLifecycle()
    val isPermissionGranted by addressViewModel.isPermissionGranted.collectAsStateWithLifecycle()

    LaunchedEffect(isPermissionGranted) {
        if (!isPermissionGranted) {
            if (locationPermissionRejectedCount >= 2) {
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
                navController.popBackStack()
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
            addressViewModel.enableLocationRequest(context) { intent ->
                locationRequestLauncher.launch(intent)
            }
        }
    }
}
