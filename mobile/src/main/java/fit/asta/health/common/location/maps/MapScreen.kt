package fit.asta.health.common.location.maps

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googlemapsapp.maps.MapBottomSheet
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.MainActivity
import fit.asta.health.common.ui.components.MapTopBar
import fit.asta.health.common.ui.components.SearchBar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
@Composable
fun MapScreen(onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val fusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var markerLatLng by rememberSaveable {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerLatLng, 18f)
    }


    var bottomSheetText by rememberSaveable {
        mutableStateOf("")
    }

    val getCurrentLocation = {
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(context as MainActivity) { task ->
            if (task.isSuccessful) {
                // Set the map's camera position to the current location of the device.
                val lastKnownLocation = task.result
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(
                    lastKnownLocation!!.latitude,
                    lastKnownLocation.longitude,
                    1
                )

                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    bottomSheetText =
                        "${address.featureName}, ${address.subLocality}, ${address.locality}, ${address.adminArea}"
                }
                markerLatLng = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                cameraPositionState.position = CameraPosition.fromLatLngZoom(markerLatLng, 18f)
            } else {
                Log.d(TAG, "Current location is null. Using defaults.")
                Log.e(TAG, "Exception: %s", task.exception)
            }
        }
    }

    if (markerLatLng.latitude == 0.0 && markerLatLng.longitude == 0.0) {
        getCurrentLocation()
    }

    var bottomSheetState by rememberSaveable {
        mutableStateOf(BottomSheetValue.Collapsed)
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = bottomSheetState)
    )

    val scope = rememberCoroutineScope()

    val expandSheet = {
        bottomSheetState = BottomSheetValue.Expanded
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

    val collapseSheet = {
        bottomSheetState = BottomSheetValue.Collapsed
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        sheetElevation = 10.dp,
        sheetGesturesEnabled = false,
        scaffoldState = bottomSheetScaffoldState,
        topBar = {
            MapTopBar("Choose Location") {
                onBackPressed()
            }
        },
        sheetPeekHeight = 150.dp,
        sheetBackgroundColor = if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) Color.Transparent else Color.Black.copy(
            0.2f
        ),
        sheetContent = {
            MapBottomSheet(
                sheetScaffoldState = bottomSheetScaffoldState,
                locationName = bottomSheetText,
                onButtonClick = { expandSheet() },
                onCollapse = { collapseSheet() })
        }) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                onMapLongClick = {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(
                        it.latitude,
                        it.longitude,
                        1
                    )

                    if (addresses != null && addresses.isNotEmpty()) {
                        val address = addresses[0]
                        bottomSheetText =
                            "${address.featureName}, ${address.subLocality}, ${address.locality}, ${address.adminArea}"
                    }
                    markerLatLng = it
                }
            ) {
                MarkerInfoWindowContent(
                    state = MarkerState(
                        position = markerLatLng
                    )
                ) { marker ->
                    Text(marker.title ?: "You", color = Color.Red)
                }
            }

            SearchBar {
                bottomSheetText = it.name as String
                markerLatLng = it.latLng as LatLng
                cameraPositionState.position = CameraPosition.fromLatLngZoom(markerLatLng, 18f)
            }

            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { getCurrentLocation() }
                    .background(Color.Red)
                    .padding(10.dp)
                    .align(Alignment.BottomCenter),
                text = "Use current Location",
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}


