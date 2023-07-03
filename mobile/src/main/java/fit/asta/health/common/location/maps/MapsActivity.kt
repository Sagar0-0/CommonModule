package fit.asta.health.common.location.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.common.location.maps.modal.AddressesResponse.*
import fit.asta.health.common.location.maps.modal.MapScreens
import fit.asta.health.common.location.maps.ui.MapScreen
import fit.asta.health.common.location.maps.ui.SavedAddressesScreen
import fit.asta.health.common.ui.AppTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1934
    }

    private val locationPermissionGranted = mutableStateOf(false)
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Places.initialize(applicationContext, getString(R.string.MAPS_API_KEY))
        getLocationPermission()
        mapsViewModel.getAllAddresses()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mapsViewModel.getCurrentLatLng(this@MapsActivity)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mapsViewModel.currentLatLng.collect {
                    mapsViewModel.getCurrentAddress(this@MapsActivity)
                }
            }
        }
        setContent {
            AppTheme {
                val navController = rememberNavController()
                if (locationPermissionGranted.value) {
                    MapNavHost(navController)
                } else {
                    Text(text = "Need Permission")
                }
            }

        }
    }

    @Composable
    fun MapNavHost(navController: NavHostController) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = MapScreens.SavedAdd.route + "?search={search}"
        ) {
            composable(
                route = MapScreens.SavedAdd.route + "?search={search}",
                arguments = listOf(navArgument("search") {
                    defaultValue = false
                    type = NavType.BoolType
                })
            ) {
                val startSearch = it.arguments?.getBoolean("search") ?: false
                SavedAddressesScreen(
                    startSearch = startSearch,
                    navHostController = navController,
                    mapsViewModel = mapsViewModel
                ) {
                    finish()
                }
            }

            composable(
                route = MapScreens.Map.route + "/{address}?confirm={confirm}",
                arguments = listOf(navArgument("confirm") {
                    defaultValue = false
                    type = NavType.BoolType
                })
            )
            {
                val confirmAdd = it.arguments?.getBoolean("confirm") ?: false
                val gson: Gson = GsonBuilder().create()
                val addJson = it.arguments?.getString("address")!!.replace("|", "/")
                val addressItem = gson.fromJson(addJson, Address::class.java)
                MapScreen(confirmAdd, addressItem, navController, mapsViewModel) {
                    navController.navigateUp()
                }
            }
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted.value = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted.value = true
        }
    }
}