package fit.asta.health.common.maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.common.maps.modal.AddressesResponse.MyAddress
import fit.asta.health.common.maps.modal.MapScreens
import fit.asta.health.common.maps.utils.LocationProviderChangedReceiver
import fit.asta.health.common.maps.view.MapScreen
import fit.asta.health.common.maps.view.SavedAddressesScreen
import fit.asta.health.common.maps.vm.MapsViewModel
import fit.asta.health.common.ui.AppTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            Intent(context, MapsActivity::class.java)
                .apply {
                    context.startActivity(this)
                }
        }
    }

    private val mapsViewModel: MapsViewModel by viewModels()
    private var br: LocationProviderChangedReceiver? = null
    private var locationRequestLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeScreen()
        registerLocationRequestLauncher()
        registerBroadcastReceiver()
        setContent {
            AppTheme {
                val isLocationEnabled by mapsViewModel.isLocationEnabled.collectAsStateWithLifecycle()
                val isPermissionGranted by mapsViewModel.isPermissionGranted.collectAsStateWithLifecycle()
                if (!isPermissionGranted) {
                    Toast.makeText(this, "Location permission access denied!!", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                if (!isLocationEnabled) {
                    mapsViewModel.enableLocationRequest(this@MapsActivity) {
                        locationRequestLauncher?.launch(it)
                    }
                }
                val navController = rememberNavController()
                MapNavHost(navController)
            }
        }
    }

    private fun registerBroadcastReceiver() {
        br = LocationProviderChangedReceiver()
        br!!.init(
            object : LocationProviderChangedReceiver.LocationListener {
                override fun onEnabled() {
                    mapsViewModel.isLocationEnabled.value = true
                    updateCurrentLocation()
                }

                override fun onDisabled() {
                    mapsViewModel.isLocationEnabled.value = false
                }
            }
        )
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(br, filter)
    }

    private fun initializeScreen() {
        Places.initialize(applicationContext, getString(R.string.MAPS_API_KEY))
        mapsViewModel.getAllAddresses()
        updateCurrentLocation()
    }

    private fun updateCurrentLocation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mapsViewModel.updateCurrentLocationData(this@MapsActivity)
            }
        }
    }

    private fun registerLocationRequestLauncher() {
        locationRequestLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK)
                    mapsViewModel.updateCurrentLocationData(this)
                else {
                    if (!mapsViewModel.isLocationEnabled.value) {
                        Toast.makeText(
                            this,
                            "Location access is mandatory to use this feature!!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (br != null) unregisterReceiver(br)
    }

    override fun onStart() {
        super.onStart()
        mapsViewModel.isPermissionGranted.value =
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)

    }

    @Composable
    fun MapNavHost(navController: NavHostController) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = MapScreens.SavedAdd.route
        ) {
            composable(
                route = MapScreens.SavedAdd.route
            ) {
                SavedAddressesScreen(
                    navHostController = navController,
                    mapsViewModel = mapsViewModel,
                    onBackPressed = ::finish
                )
            }

            composable(
                route = MapScreens.Map.route + "/{address}"
            ) {
                val gson: Gson = GsonBuilder().create()
                val addJson = it.arguments?.getString("address")!!.replace("|", "/")
                val myAddressItem = gson.fromJson(addJson, MyAddress::class.java)
                MapScreen(
                    myAddressItem = myAddressItem,
                    mapsViewModel = mapsViewModel,
                    onBackPressed = navController::navigateUp
                )
            }
        }
    }
}