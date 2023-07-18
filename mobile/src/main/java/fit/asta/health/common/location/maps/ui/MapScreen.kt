package fit.asta.health.common.location.maps.ui

import android.annotation.SuppressLint
import android.location.Address
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.common.location.maps.MapsViewModel
import fit.asta.health.common.location.maps.modal.AddressesResponse.MyAddress
import fit.asta.health.common.location.maps.modal.MapScreens
import fit.asta.health.common.ui.CustomTopBar
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResultState
import fit.asta.health.common.utils.getLocationName
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("MissingPermission")
fun MapScreen(
    confirmAddress: Boolean,
    myAddressItem: MyAddress,
    navHostController: NavHostController,
    mapsViewModel: MapsViewModel,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(myAddressItem.lat, myAddressItem.lon), 18f)
    }

    val scope = rememberCoroutineScope()

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val expandSheet = {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    val collapseSheet = {
        scope.launch {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }

    BottomSheetScaffold(
        sheetDragHandle = null,
        sheetPeekHeight = 150.dp,
        sheetShadowElevation = cardElevation.medium,
        sheetSwipeEnabled = false,
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopBar("Choose Location") {
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
                    (address as ResultState.Success<Address?>).data?.let {
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
                                    name = it.getAddressLine(0),
                                    area = getLocationName(it)
                                )

                                var saveEnabled by remember { mutableStateOf(true) }
                                OutlinedButton(
                                    enabled = saveEnabled,
                                    onClick = {
                                        saveEnabled = false
                                        val values = it.getAddressLine(0).split(", ")
                                        val newMyAddress = MyAddress(
                                            area = it.adminArea,
                                            selected = true,
                                            block = values[1],
                                            hn = values[0],
                                            id = "",
                                            lat = it.latitude,
                                            lon = it.longitude,
                                            loc = it.locality,
                                            nearby = values[2],
                                            name = "Home",
                                            pin = it.postalCode,
                                            ph = "Unknown",
                                            sub = it.subLocality,
                                            uid = mapsViewModel.uId
                                        )
                                        mapsViewModel.putAddress(newMyAddress) {
                                            Toast.makeText(
                                                context,
                                                "Address Saved Successfully",
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
                                sheetScaffoldState = scaffoldState,
                                address = it,
                                myAddressItem = myAddressItem,
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
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    mapToolbarEnabled = false,
                ),
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
                onClick = {
                    mapsViewModel.getCurrentLatLng(context)
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(mapsViewModel.currentLatLng.value, 18f)
                }
            ) {
                Text(
                    text = "Use current Location",
                    style = MaterialTheme.typography.titleLarge
                )
            }

        }
    }
}


