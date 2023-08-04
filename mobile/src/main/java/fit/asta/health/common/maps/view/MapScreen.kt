package fit.asta.health.common.maps.view

import android.location.Address
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.common.maps.modal.AddressesResponse.MyAddress
import fit.asta.health.common.maps.vm.MapsViewModel
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    myAddressItem: MyAddress,
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

    val expandSheet: () -> Unit = {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    val collapseSheet: () -> Unit = {
        scope.launch {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }

    val bottomSheetSize = 140.dp
    BottomSheetScaffold(
        sheetDragHandle = null,
        sheetPeekHeight = bottomSheetSize,
        sheetShadowElevation = cardElevation.medium,
        sheetSwipeEnabled = false,
        scaffoldState = scaffoldState,
        topBar = {
            AppTopBar(title = "Choose Location", onBack = onBackPressed)
        },
        sheetContent = {
            LaunchedEffect(cameraPositionState.position.target) {
                mapsViewModel.getMarkerAddressDetails(
                    cameraPositionState.position.target.latitude,
                    cameraPositionState.position.target.longitude,
                    context
                )
            }
            val markerAddress by mapsViewModel.markerAddressDetail.collectAsStateWithLifecycle()
            when (markerAddress) {
                ResponseState.Loading -> {
                    Box(
                        modifier = Modifier.height(bottomSheetSize),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }

                is ResponseState.Success -> {
                    MapBottomSheet(
                        sheetScaffoldState = scaffoldState,
                        address = (markerAddress as ResponseState.Success<Address>).data,
                        myAddressItem = myAddressItem,
                        onEnterAddressClick = expandSheet,
                        onCollapse = collapseSheet,
                        onBackPress = onBackPressed,
                        onSaveAddress = mapsViewModel::putAddress
                    )
                }

                is ResponseState.Error -> {
                    AppErrorScreen(
                        desc = "Something went wrong!!",
                        onTryAgain = {
                            scope.launch {
                                mapsViewModel.getMarkerAddressDetails(
                                    cameraPositionState.position.target.latitude,
                                    cameraPositionState.position.target.longitude,
                                    context
                                )
                            }
                        }
                    )
                }

                else -> {}
            }

        }) { padding ->

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


            OutlinedTextField(
                maxLines = 1,
                enabled = false,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.small)
                    .clickable {
//                        onSearchClick() TODO: SEARCH BOTTOM SHEET
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
                    .padding(bottom = bottomSheetSize + spacing.medium)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    mapsViewModel.updateCurrentLocationData(context)
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


