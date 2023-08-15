package fit.asta.health.common.address.ui.view

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.R
import fit.asta.health.common.address.data.modal.MyAddress
import fit.asta.health.common.address.data.modal.SearchResponse
import fit.asta.health.common.ui.components.generic.AppBottomSheetScaffold
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.iconButtonSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getLocationName
import fit.asta.health.common.utils.toStringFromResId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MapScreen(
    myAddressItem: MyAddress,
    searchResultState: UiState<SearchResponse>,
    markerAddressState: UiState<Address>,
    currentAddressState: UiState<Address>,
    putAddressState: UiState<Boolean>,
    onUiEvent: (MapScreenUiEvent) -> Unit
) {
    var searchSheetVisible by rememberSaveable { mutableStateOf(false) }
    var fillAddressSheetVisible by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    val bottomCardHeight = 140.dp

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(myAddressItem.lat, myAddressItem.lon), 18f)
    }
    LaunchedEffect(cameraPositionState.position.target) {
        onUiEvent(
            MapScreenUiEvent.GetMarkerAddress(
                LatLng(
                    cameraPositionState.position.target.latitude,
                    cameraPositionState.position.target.longitude
                )
            )
        )
    }

    AppBottomSheetScaffold(
        topBar = {
            AppTopBar(title = R.string.choose_location.toStringFromResId(), onBack = {
                onUiEvent(MapScreenUiEvent.Back)
            })
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        scaffoldState = scaffoldState,
        sheetDragHandle = null,
        sheetContent = {}
    ) { padding ->

        if (searchSheetVisible) SearchBottomSheet(
            modifier = Modifier.padding(padding),
            onSearch = {
                onUiEvent(MapScreenUiEvent.Search(it))
            },
            searchResponseState = searchResultState,
            onResultClick = { addressItem ->
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(LatLng(addressItem.lat, addressItem.lon), 18f)
            },
            onClose = {
                onUiEvent(MapScreenUiEvent.ClearSearch)
                searchSheetVisible = false
            }
        )
        if (fillAddressSheetVisible) FillAddressSheet(
            address = markerAddressState,
            myAddressItem = myAddressItem,
            putAddressState = putAddressState,
            onUiEvent = {
                when (it) {
                    is FillAddressUiEvent.CloseSheet -> {
                        fillAddressSheetVisible = false
                    }

                    is FillAddressUiEvent.Back -> {
                        onUiEvent(MapScreenUiEvent.Back)
                    }

                    is FillAddressUiEvent.SaveAddress -> {
                        onUiEvent(MapScreenUiEvent.PutAddress(it.myAddress))
                    }
                }
            }
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
                            text = R.string.search_for_area_street.toStringFromResId(),
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
                        .size(iconButtonSize.extraLarge2),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        onUiEvent(MapScreenUiEvent.UseCurrentLocation)
                        if (currentAddressState is UiState.Success) {
                            val latLng = LatLng(
                                currentAddressState.data.latitude,
                                currentAddressState.data.longitude
                            )
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(latLng, 18f)
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(iconButtonSize.large),
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = R.string.use_my_current_location.toStringFromResId()
                    )
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
                    when (markerAddressState) {
                        UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(bottomCardHeight),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingAnimation()
                            }
                        }

                        is UiState.Success -> {
                            CurrentLocationUi(
                                name = markerAddressState.data.getAddressLine(
                                    0
                                ),
                                area = getLocationName(markerAddressState.data)
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
                                    text = R.string.enter_complete_address.toStringFromResId(),
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }

                        else -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(bottomCardHeight),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = R.string.something_went_wrong.toStringFromResId())
                            }
                        }
                    }
                }
            }
        }
    }
}


