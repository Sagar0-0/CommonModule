package fit.asta.health.feature.address.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getShortAddressName
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.designsystem.components.generic.AppBottomSheetScaffold
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.iconButtonSize
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.resources.strings.R
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MapScreen(
    myAddressItem: MyAddress,
    searchSheetVisible: Boolean,
    markerAddressState: UiState<Address>,
    currentAddressState: UiState<Address>,
    onUiEvent: (MapScreenUiEvent) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    val bottomCardHeight = remember { 140.dp }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                myAddressItem.lat,
                myAddressItem.lon
            ), 18f
        )
    }
    LaunchedEffect(Unit) {
        if (
            myAddressItem.lat == 0.0 &&
            myAddressItem.lon == 0.0 &&
            currentAddressState is UiState.Success
        ) {
            val latLng = LatLng(
                currentAddressState.data.latitude,
                currentAddressState.data.longitude
            )
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(latLng, 18f)
        }
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
                            onUiEvent(MapScreenUiEvent.ShowSearchSheet)
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
                                area = markerAddressState.data.getShortAddressName()
                            )

                            OutlinedButton(
                                onClick = {
                                    val data = markerAddressState.data
                                    val fillAddressSheetAddressItem = MyAddress(
                                        selected = myAddressItem.selected,
                                        area = data.adminArea,
                                        block = myAddressItem.block,
                                        hn = myAddressItem.hn,
                                        id = myAddressItem.id,
                                        lat = data.latitude,
                                        loc = data.locality,
                                        lon = data.longitude,
                                        name = myAddressItem.name,
                                        nearby = myAddressItem.nearby,
                                        ph = myAddressItem.ph,
                                        pin = data.postalCode,
                                        sub = data.subLocality ?: data.locality,
                                        addressLine = data.getAddressLine(0),
                                        shortAddress = data.getShortAddressName()
                                    )
                                    onUiEvent(
                                        MapScreenUiEvent.ShowFillAddressSheet(
                                            fillAddressSheetAddressItem
                                        )
                                    )
                                },
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


