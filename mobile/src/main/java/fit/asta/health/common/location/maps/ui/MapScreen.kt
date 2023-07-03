package fit.asta.health.common.location.maps.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.common.location.maps.MapsActivity
import fit.asta.health.common.location.maps.MapsViewModel
import fit.asta.health.common.location.maps.modal.AddressesResponse.Address
import fit.asta.health.common.location.maps.modal.MapScreens
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResultState
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("MissingPermission")
fun MapScreen(
    confirmAddress: Boolean,
    addressItem: Address,
    navHostController: NavHostController,
    mapsViewModel: MapsViewModel,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(addressItem.lat, addressItem.lon), 18f)
    }

    val fusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val locationResult = fusedLocationProviderClient.lastLocation
    val getCurrentLocation = {
        locationResult.addOnCompleteListener(context as MapsActivity) { task ->
            if (task.isSuccessful) {
                try {
                    val lastKnownLocation = task.result
                    val latLng = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 18f)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Turn on your locations and try re-starting the app!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                Log.d(TAG, "Current location is null. Using defaults.")
                Log.e(TAG, "Exception: %s", task.exception)
            }
        }
    }

    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false
        )
    )

    val expandSheet = {
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

    val collapseSheet = {
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.partialExpand()
        }
    }

    BottomSheetScaffold(
        sheetDragHandle = null,
        sheetPeekHeight = 150.dp,
        sheetShadowElevation = cardElevation.medium,
        sheetSwipeEnabled = false,
        scaffoldState = bottomSheetScaffoldState,
        topBar = {
            MapTopBar("Choose Location") {
                onBackPressed()
            }
        },
        sheetContent = {
            LaunchedEffect(cameraPositionState.position.target) {
                mapsViewModel.getMarkerAddressDetails(
                    cameraPositionState.position.target.latitude,
                    cameraPositionState.position.target.longitude,
                    context
                )
            }
            val address by mapsViewModel.markerAddressDetail.collectAsStateWithLifecycle()
            when (address) {
                is ResultState.Loading -> {
                    LinearProgressIndicator(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                is ResultState.Success -> {
                    (address as ResultState.Success<android.location.Address?>).data?.let {
                        if (confirmAddress) {
                            Column(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = customSize.medium,
                                            topEnd = customSize.medium
                                        )
                                    )
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = spacing.medium),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CurrentLocationUi(
                                    name = (if (addressItem.area.isNotEmpty()) addressItem.area + ", " else "") + (it.locality
                                        ?: it.subAdminArea
                                        ?: it.adminArea ?: ""),
                                    area = (it.adminArea ?: "") + ", " + (it.countryName ?: "")
                                )

                                OutlinedButton(
                                    onClick = {
                                        val newAddress = Address(
                                            area = it.adminArea,
                                            selected = true,
                                            block = it.locality ?: "",
                                            hn = it.subLocality,
                                            id = "",
                                            lat = addressItem.lat,
                                            lon = addressItem.lon,
                                            loc = it.locality,
                                            nearby = "",
                                            name = "Home",
                                            pin = it.postalCode,
                                            ph = "Unknown",
                                            sub = it.subLocality,
                                            uid = mapsViewModel.uId
                                        )
                                        Log.d(TAG, "MapScreen: onClick Save $newAddress")
                                        mapsViewModel.putAddress(newAddress) {
                                            Toast.makeText(
                                                context,
                                                "Address Saved",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navHostController.navigateUp()
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(bottom = spacing.medium)
                                        .fillMaxWidth(),
                                    shape = MaterialTheme.shapes.medium,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    )
                                ) {
                                    Text(
                                        maxLines = 1,
                                        text = "Confirm Location",
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        } else {
                            MapBottomSheet(
                                navHostController = navHostController,
                                sheetScaffoldState = bottomSheetScaffoldState,
                                address = it,
                                addressItem = addressItem,
                                mapsViewModel = mapsViewModel,
                                onButtonClick = { expandSheet() },
                                onCollapse = { collapseSheet() }
                            )

                        }
                    }
                }

                else -> {
                    LaunchedEffect(cameraPositionState.position.target) {
                        mapsViewModel.getMarkerAddressDetails(
                            cameraPositionState.position.target.latitude,
                            cameraPositionState.position.target.longitude,
                            context
                        )
                    }
                }
            }

        }) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = MarkerState(
                        position = cameraPositionState.position.target
                    )
                )
            }


            OutlinedTextField(
                maxLines = 1,
                enabled = false,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
                    .clickable {
                        navHostController.navigate(MapScreens.SavedAdd.route + "?search=true") {
                            popUpTo(0)
                        }
                    },
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        text = "Search for area,street name..",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.primary,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedButton(
                modifier = Modifier
                    .padding(spacing.medium)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { getCurrentLocation() }
            ) {
                Text(
                    text = "Use current Location",
                    style = MaterialTheme.typography.titleLarge
                )
            }

        }
    }
}


