package fit.asta.health.feature.address

import android.Manifest
import android.content.Intent
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.onLifecycleEvent
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.address.modal.MyAddress
import fit.asta.health.data.address.utils.LocationProviderChangedReceiver
import fit.asta.health.feature.address.view.AddressDestination
import fit.asta.health.feature.address.view.MapScreen
import fit.asta.health.feature.address.view.MapScreenUiEvent
import fit.asta.health.feature.address.view.SavedAddressUiEvent
import fit.asta.health.feature.address.view.SavedAddressesScreen
import fit.asta.health.feature.address.vm.AddressViewModel
import fit.asta.health.resources.strings.R

private const val ADDRESS_GRAPH_ROUTE = "graph_address"

fun NavController.navigateToAddress(navOptions: NavOptions? = null) {
    this.navigate(ADDRESS_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.addressRoute(onBackPress: () -> Unit) {
    composable(ADDRESS_GRAPH_ROUTE) {
        val nestedNavController = rememberNavController()
        val addressViewModel: AddressViewModel = hiltViewModel()
        Setup(addressViewModel, onBackPress)

        val savedAddressListState by addressViewModel.savedAddressListState.collectAsStateWithLifecycle()
        val putAddressState by addressViewModel.putAddressState.collectAsStateWithLifecycle()
        val deleteAddressState by addressViewModel.deleteAddressState.collectAsStateWithLifecycle()
        val selectAddressState by addressViewModel.selectAddressState.collectAsStateWithLifecycle()
        val currentAddressState by addressViewModel.currentAddressState.collectAsStateWithLifecycle()
        val searchResultState by addressViewModel.searchResultState.collectAsStateWithLifecycle()
        val markerAddressState by addressViewModel.markerAddressState.collectAsStateWithLifecycle()

        NavHost(
            navController = nestedNavController,
            route = ADDRESS_GRAPH_ROUTE,
            startDestination = AddressDestination.SavedAdd.route
        ) {
            composable(
                route = AddressDestination.SavedAdd.route
            ) {
                LaunchedEffect(Unit) {
                    addressViewModel.getSavedAddresses()
                }
                SavedAddressesScreen(savedAddressListState = savedAddressListState,
                    deleteAddressState = deleteAddressState,
                    selectAddressState = selectAddressState,
                    currentAddressState = currentAddressState,
                    searchResultState = searchResultState,
                    onUiEvent = { event ->
                        Log.d("TAG", "addressRoute: $event")
                        when (event) {
                            SavedAddressUiEvent.UpdateCurrentLocation -> {
                                addressViewModel.checkPermissionAndUpdateCurrentAddress()
                            }

                            SavedAddressUiEvent.ResetDelete -> {
                                addressViewModel.resetDeleteState()
                            }

                            SavedAddressUiEvent.ResetSelect -> {
                                addressViewModel.resetSelectAddressState()
                            }

                            is SavedAddressUiEvent.Search -> {
                                addressViewModel.search(event.query)
                            }

                            SavedAddressUiEvent.ClearSearch -> {
                                addressViewModel.clearSearchResponse()
                            }

                            is SavedAddressUiEvent.SetSelectedAddress -> {
                                addressViewModel.setSelectedAdId(event.id)
                            }

                            is SavedAddressUiEvent.SelectAddress -> {
                                addressViewModel.selectAddress(event.address)
                            }

                            is SavedAddressUiEvent.DeleteAddress -> {
                                addressViewModel.deleteAddress(event.id)
                            }

                            SavedAddressUiEvent.GetSavedAddress -> {
                                addressViewModel.getSavedAddresses()
                            }

                            is SavedAddressUiEvent.NavigateToMaps -> {
                                val gson: Gson = GsonBuilder().create()
                                val json = gson.toJson(event.address).replace("/", "|")
                                nestedNavController.navigate(AddressDestination.Map.route + "/$json")
                            }

                            SavedAddressUiEvent.Back -> {
                                onBackPress()
                            }
                        }
                    })
            }

            composable(
                route = AddressDestination.Map.route + "/{address}"
            ) {
                val gson: Gson = GsonBuilder().create()
                val addJson = it.arguments?.getString("address")!!.replace("|", "/")
                val myAddressItem = gson.fromJson(addJson, MyAddress::class.java)
                MapScreen(myAddressItem = myAddressItem,
                    searchResultState = searchResultState,
                    markerAddressState = markerAddressState,
                    currentAddressState = currentAddressState,
                    putAddressState = putAddressState,
                    onUiEvent = { event ->
                        when (event) {
                            MapScreenUiEvent.ResetPutState -> {
                                addressViewModel.resetPutState()
                            }

                            is MapScreenUiEvent.GetMarkerAddress -> {
                                addressViewModel.getMarkerAddressDetails(event.latLng)
                            }

                            is MapScreenUiEvent.Back -> {
                                nestedNavController.popBackStack()
                            }

                            is MapScreenUiEvent.Search -> {
                                addressViewModel.search(event.query)
                            }

                            is MapScreenUiEvent.ClearSearch -> {
                                addressViewModel.clearSearchResponse()
                            }

                            is MapScreenUiEvent.PutAddress -> {
                                addressViewModel.putAddress(event.myAddress)
                            }

                            is MapScreenUiEvent.UseCurrentLocation -> {
                                addressViewModel.checkPermissionAndUpdateCurrentAddress()
                            }
                        }
                    })
            }
        }
    }
}

@Composable
private fun Setup(addressViewModel: AddressViewModel, onBackPress: () -> Unit) {
    val context = LocalContext.current

    val isPermissionGranted by addressViewModel.isPermissionGranted.collectAsStateWithLifecycle()
    val locationPermissionRejectedCount by addressViewModel.locationPermissionRejectedCount.collectAsStateWithLifecycle()

    val lifecycleEvent = onLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        Log.e("TAG", lifecycleEvent.name)
        when (lifecycleEvent) {
            Lifecycle.Event.ON_START -> {
                addressViewModel.setIsPermissionGranted()
            }

            else -> {}
        }
    }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        var gotAccess = true
        perms.keys.forEach loop@{ perm ->
            if ((perms[perm] == true) && (perm == Manifest.permission.ACCESS_FINE_LOCATION || perm == Manifest.permission.ACCESS_COARSE_LOCATION)) {
                addressViewModel.updateLocationPermissionRejectedCount(1)
                addressViewModel.setIsPermissionGranted()
                gotAccess = true
                return@loop
            } else {
                gotAccess = false
            }
        }
        if (!gotAccess) {
            Toast.makeText(
                context, context.getString(R.string.location_access_required), Toast.LENGTH_SHORT
            ).show()
            addressViewModel.updateLocationPermissionRejectedCount(
                locationPermissionRejectedCount + 1
            )
            onBackPress()
        }
    }

    LaunchedEffect(isPermissionGranted) {
        if (!isPermissionGranted) {
            if (locationPermissionRejectedCount >= 2) {
                Toast.makeText(
                    context,
                    R.string.please_allow_location_permissions.toStringFromResId(context),
                    Toast.LENGTH_SHORT
                ).show()
                with(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)) {
                    data = Uri.fromParts("package", context.packageName, null)
                    addCategory(Intent.CATEGORY_DEFAULT)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    context.startActivity(this)
                }
                onBackPress()
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

    //on location service change - update the vm
    val isLocationEnabled by addressViewModel.isLocationEnabled.collectAsStateWithLifecycle()
    DisposableEffect(context) {
        val br = LocationProviderChangedReceiver(onToggleLocation = {
            addressViewModel.setIsLocationEnabled()
        })
        br.register(context)
        onDispose {
            br.unregister(context)
            Log.e("TAG", "br dispose: Context was cleared")
        }
    }
    val locationRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        addressViewModel.setIsLocationEnabled()
        if (activityResult.resultCode != AppCompatActivity.RESULT_OK) {
            if (!isLocationEnabled) {
                Toast.makeText(
                    context,
                    R.string.location_access_required.toStringFromResId(context),
                    Toast.LENGTH_SHORT
                ).show()
                onBackPress()
            }
        }
    }
    LaunchedEffect(isLocationEnabled) {
        if (!isLocationEnabled) {
            addressViewModel.enableLocationRequest { intent ->
                locationRequestLauncher.launch(intent)
            }
        }
    }

    LaunchedEffect(isPermissionGranted, isLocationEnabled) {
        if (isLocationEnabled && isPermissionGranted) {
            addressViewModel.checkPermissionAndUpdateCurrentAddress()
        }
    }
}
