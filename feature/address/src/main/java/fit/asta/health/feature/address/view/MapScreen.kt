package fit.asta.health.feature.address.view

import android.location.Address
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getShortAddressName
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.data.address.remote.modal.PutAddressResponse
import fit.asta.health.data.address.remote.modal.SearchResponse
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppBottomSheetScaffold
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppTextField
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.strings.R
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MapScreen(
    type: Int,
    myAddressItem: MyAddress,
    markerAddressState: UiState<Address>,
    searchResultState: UiState<SearchResponse>,
    putAddressState: UiState<PutAddressResponse>,
    currentAddressState: UiState<Address>,
    onUiEvent: (MapScreenUiEvent) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current

    val bottomCardHeight = remember { 140.dp }
    var searchSheetType by rememberSaveable { mutableStateOf<SearchSheetType?>(null) }
    var fillAddressSheetType by rememberSaveable { mutableStateOf<FillAddressSheetType?>(null) }

    val openFillAddressSheet: (FillAddressSheetType) -> Unit = {
        fillAddressSheetType = it
    }
    val openSearchSheet: (SearchSheetType) -> Unit = {
        searchSheetType = it
    }

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

        if (searchSheetType != null) SearchBottomSheet(
            type = searchSheetType!!,
            searchResponseState = searchResultState,
            onUiEvent = {
                when (it) {
                    SearchSheetUiEvent.Close -> {
                        searchSheetType = null
                    }

                    SearchSheetUiEvent.ClearSearchResponse -> {
                        onUiEvent(MapScreenUiEvent.ClearSearch)
                    }

                    is SearchSheetUiEvent.OnResultClick -> {
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(
                                LatLng(
                                    it.myAddress.lat,
                                    it.myAddress.lon
                                ), 18f
                            )
                    }

                    is SearchSheetUiEvent.Search -> {
                        onUiEvent(MapScreenUiEvent.Search(it.query))
                    }
                }
            }
        )

        if (fillAddressSheetType != null) FillAddressSheet(
            type = fillAddressSheetType!!,
            putAddressState = putAddressState,
            onUiEvent = {
                when (it) {
                    FillAddressUiEvent.ResetPutState -> {
                        onUiEvent(MapScreenUiEvent.ResetPutState)
                    }

                    is FillAddressUiEvent.CloseSheet -> {
                        fillAddressSheetType = null
                    }

                    is FillAddressUiEvent.OnPutSuccess -> {
                        onUiEvent(MapScreenUiEvent.Back)
                    }

                    is FillAddressUiEvent.SaveAddress -> {
                        Log.d("TAG", "MapScreen: ${it.myAddress}")
                        Log.d("TAG", "MapScreen: $myAddressItem")
                        if (it.myAddress == myAddressItem && type == 3) {
                            Toast.makeText(context, "No changes found", Toast.LENGTH_SHORT).show()
                        } else {
                            onUiEvent(MapScreenUiEvent.PutAddress(it.myAddress))
                        }
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

            AnimatedVisibility(searchSheetType == null) {
                AppTextField(
                    singleLine = true,
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.level2)
                        .clickable {
                            openSearchSheet(SearchSheetType.FromMapScreen)
                        },
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        AppTexts.TitleMedium(
                            text = R.string.search_for_area_street.toStringFromResId(),
                        )
                    },
                    leadingIcon = {
                        AppDefaultIcon(imageVector = Icons.Default.Search, contentDescription = "")
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                AppButtons.AppIconButton(
                    modifier = Modifier
                        .padding(AppTheme.spacing.level3)
                        .align(Alignment.End)
                        .size(AppTheme.buttonSize.level7),
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
                    AppDefaultIcon(
                        modifier = Modifier
                            .size(AppTheme.buttonSize.level4),
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = R.string.use_my_current_location.toStringFromResId()
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = AppTheme.spacing.level3,
                                topEnd = AppTheme.spacing.level3
                            )
                        )
                        .background(AppTheme.colors.surface)
                        .padding(horizontal = AppTheme.spacing.level3),
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
                                AppDotTypingAnimation()
                            }
                        }

                        is UiState.Success -> {
                            CurrentLocationUi(
                                name = markerAddressState.data.getAddressLine(
                                    0
                                ),
                                area = markerAddressState.data.getShortAddressName()
                            )

                            AppButtons.AppOutlinedButton(
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
                                    openFillAddressSheet(
                                        FillAddressSheetType.EnterNewAddress(
                                            fillAddressSheetAddressItem
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .padding(bottom = AppTheme.spacing.level3)
                                    .fillMaxWidth()
                                    .clip(AppTheme.shape.level2)
                            ) {
                                TitleTexts.Level2(
                                    maxLines = 1,
                                    text = if (type == 3) {
                                        R.string.edit_address
                                    } else {
                                        R.string.enter_complete_address
                                    }.toStringFromResId(),
                                    overflow = TextOverflow.Ellipsis
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
                                TitleTexts.Level2(text = R.string.something_went_wrong.toStringFromResId())
                            }
                        }
                    }
                }
            }
        }
    }
}


