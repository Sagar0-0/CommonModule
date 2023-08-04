package fit.asta.health.common.maps.view

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.maps.modal.AddressScreen
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.utils.LocationProviderChangedReceiver
import fit.asta.health.common.maps.vm.MapsViewModel
import fit.asta.health.common.utils.rememberLifecycleEvent
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel

fun NavGraphBuilder.addressScreens(navController: NavHostController) {
    navigation(
        route = Graph.Address.route,
        startDestination = AddressScreen.SavedAdd.route
    ) {
        composable(
            route = AddressScreen.SavedAdd.route
        ) {
            val mapsViewModel: MapsViewModel = it.sharedViewModel(navController)

            setup(mapsViewModel, navController)

            LaunchedEffect(key1 = Unit, block = { mapsViewModel.getAllAddresses() })
            SavedAddressesScreen(
                navHostController = navController,
                mapsViewModel = mapsViewModel,
                onBackPressed = navController::navigateUp
            )
        }

        composable(
            route = AddressScreen.Map.route + "/{address}"
        ) {
            val mapsViewModel: MapsViewModel = it.sharedViewModel(navController)

            val gson: Gson = GsonBuilder().create()
            val addJson = it.arguments?.getString("address")!!.replace("|", "/")
            val myAddressItem = gson.fromJson(addJson, AddressesResponse.MyAddress::class.java)
            MapScreen(
                myAddressItem = myAddressItem,
                mapsViewModel = mapsViewModel,
                onBackPressed = navController::navigateUp
            )
        }
    }
}

@Composable
private fun setup(mapsViewModel: MapsViewModel, navController: NavHostController) {
    val context = LocalContext.current

    val br = LocationProviderChangedReceiver()

    LaunchedEffect(Unit) {
        br.init(
            object : LocationProviderChangedReceiver.LocationListener {
                override fun onEnabled() {
                    mapsViewModel.isLocationEnabled.value = true
                    mapsViewModel.updateCurrentLocationData(context)
                }

                override fun onDisabled() {
                    mapsViewModel.isLocationEnabled.value = false
                }

            }
        )
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(br, intentFilter)
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

            Lifecycle.Event.ON_STOP -> {
                try {
                    context.unregisterReceiver(br)
                } catch (e: Exception) {

                }
            }

            else -> {

            }
        }
    }

//            Places.initialize(context.applicationContext, context.getString(R.string.MAPS_API_KEY)) TODO: ONLY FOR SEARCHING

    val locationRequestLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
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
            Toast.makeText(context, "Location permission access denied!!", Toast.LENGTH_SHORT)
                .show()
            navController.navigateUp()
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
