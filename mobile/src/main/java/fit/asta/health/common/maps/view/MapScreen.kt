package fit.asta.health.common.maps.view

import android.location.Address
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.common.maps.modal.AddressesResponse.MyAddress
import fit.asta.health.common.maps.vm.MapsViewModel
import fit.asta.health.common.ui.components.generic.AppBottomSheetScaffold
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.iconButtonSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getLocationName
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    myAddressItem: MyAddress,
    mapsViewModel: MapsViewModel,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val searchResponseState by mapsViewModel.searchResponseState.collectAsStateWithLifecycle()
    var searchSheetVisible by rememberSaveable { mutableStateOf(false) }
    var fillAddressSheetVisible by rememberSaveable { mutableStateOf(false) }
    val markerAddress by mapsViewModel.markerAddressDetail.collectAsStateWithLifecycle()
    val currentLatLng by mapsViewModel.currentLatLng.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(myAddressItem.lat, myAddressItem.lon), 18f)
    }
    LaunchedEffect(cameraPositionState.position.target) {
        mapsViewModel.getMarkerAddressDetails(
            cameraPositionState.position.target.latitude,
            cameraPositionState.position.target.longitude,
            context
        )
    }

    AppBottomSheetScaffold(
        topBar = {
            AppTopBar(title = "Choose Location", onBack = onBackPressed)
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        scaffoldState = scaffoldState,
        sheetDragHandle = null,
        sheetContent = {}
    ) { padding ->

        if (searchSheetVisible) SearchBottomSheet(
            modifier = Modifier.padding(padding),
            onSearch = mapsViewModel::search,
            searchResponseState = searchResponseState,
            onResultClick = { addressItem ->
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(LatLng(addressItem.lat, addressItem.lon), 18f)
            },
            onClose = {
                searchSheetVisible = false
            }
        )
        if (fillAddressSheetVisible) FillAddressSheet(
            address = markerAddress,
            myAddressItem = myAddressItem,
            onCloseSheet = { fillAddressSheetVisible = false },
            onGoBack = onBackPressed,
            onSaveAddress = mapsViewModel::putAddress
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
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

            AnimatedVisibility(!searchSheetVisible) {
                OutlinedTextField(
                    maxLines = 1,
                    enabled = false,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small)
                        .clickable {
                            searchSheetVisible = true
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
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                AppButtons.AppIconButton(
                    modifier = Modifier
                        .padding(spacing.medium)
                        .align(Alignment.End)
                        .size(iconButtonSize.large),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        mapsViewModel.updateCurrentLocationData(context)
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(currentLatLng, 18f)
                    }
                ) {
                    Icon(imageVector = Icons.Default.MyLocation, contentDescription = "My Location")
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = spacing.medium,
                                topEnd = spacing.medium
                            )
                        )
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = spacing.medium),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (markerAddress) {
                        is ResponseState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingAnimation()
                            }
                        }

                        is ResponseState.Success -> {
                            CurrentLocationUi(
                                name = (markerAddress as ResponseState.Success<Address>).data.getAddressLine(
                                    0
                                ),
                                area = getLocationName((markerAddress as ResponseState.Success<Address>).data)
                            )

                            OutlinedButton(
                                onClick = { fillAddressSheetVisible = true },
                                modifier = Modifier
                                    .padding(bottom = spacing.medium)
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.medium),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Text(
                                    maxLines = 1,
                                    text = "Enter complete address",
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }

                        else -> {
                            Text(text = "Something went wrong")//TODO: ERROR HANDLING
                        }
                    }
                }
            }
        }
    }
}


