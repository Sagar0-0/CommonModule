package fit.asta.health.feature.address.view

import android.content.Intent
import android.location.Address
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.data.address.remote.modal.PutAddressResponse
import fit.asta.health.data.address.remote.modal.SearchResponse
import fit.asta.health.data.address.remote.modal.mapToMyAddress
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppBottomSheetScaffold
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppTextField
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SavedAddressesScreen(
    savedAddressListState: UiState<List<MyAddress>>,
    putAddressState: UiState<PutAddressResponse>,
    searchResultState: UiState<SearchResponse>,
    deleteAddressState: UiState<Boolean>,
    selectAddressState: UiState<Unit>,
    currentAddressState: UiState<Address>,
    onUiEvent: (SavedAddressUiEvent) -> Unit
) {
    val context = LocalContext.current

    val scaffoldState = rememberBottomSheetScaffoldState()
    var searchSheetType by rememberSaveable { mutableStateOf<SearchSheetType?>(null) }
    var fillAddressSheetType by rememberSaveable { mutableStateOf<FillAddressSheetType?>(null) }

    LaunchedEffect(deleteAddressState) {
        if (deleteAddressState is UiState.Success) {
            onUiEvent(SavedAddressUiEvent.GetSavedAddress)
            onUiEvent(SavedAddressUiEvent.ResetDelete)
        }
    }
    LaunchedEffect(selectAddressState) {
        if (selectAddressState is UiState.Success) {
            onUiEvent(SavedAddressUiEvent.GetSavedAddress)
            onUiEvent(SavedAddressUiEvent.ResetSelect)
        }
    }

    val openFillAddressSheet: (FillAddressSheetType) -> Unit = {
        fillAddressSheetType = it
    }
    val openSearchSheet: (SearchSheetType) -> Unit = {
        searchSheetType = it
    }

    AppBottomSheetScaffold(
        topBar = {
            AppTopBar(title = R.string.saved_address.toStringFromResId(), onBack = {
                onUiEvent(SavedAddressUiEvent.Back)
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
                        onUiEvent(SavedAddressUiEvent.ClearSearch)
                    }

                    is SearchSheetUiEvent.OnResultClick -> {
                        onUiEvent(SavedAddressUiEvent.NavigateToMaps(it.myAddress, 1))
                    }

                    is SearchSheetUiEvent.Search -> {
                        onUiEvent(SavedAddressUiEvent.Search(it.query))
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
                        onUiEvent(SavedAddressUiEvent.ResetPutState)
                    }

                    is FillAddressUiEvent.CloseSheet -> {
                        fillAddressSheetType = null
                    }

                    is FillAddressUiEvent.OnPutSuccess -> {
                        onUiEvent(SavedAddressUiEvent.GetSavedAddress)
                    }

                    is FillAddressUiEvent.SaveAddress -> {
                        onUiEvent(SavedAddressUiEvent.PutAddress(it.myAddress))
                    }
                }
            }
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedVisibility(searchSheetType == null) {
                AppTextField(
                    maxLines = 1,
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.small)
                        .clickable {
                            openSearchSheet(SearchSheetType.FromSavedAddress)
                        },
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        TitleTexts.Level2(
                            text = R.string.search_for_area_street.toStringFromResId(),
                        )
                    },
                    leadingIcon = {
                        AppDefaultIcon(imageVector = Icons.Default.Search, contentDescription = "")
                    }
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.medium))

            AnimatedVisibility(fillAddressSheetType == null) {
                AppButtons.AppOutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.small),
                    onClick = {
                        if (currentAddressState is UiState.Success) {
                            openFillAddressSheet(
                                FillAddressSheetType.SaveCurrentAddress(
                                    currentAddressState.data.mapToMyAddress()
                                )
                            )
                        } else if (currentAddressState is UiState.ErrorMessage) {
                            onUiEvent(SavedAddressUiEvent.UpdateCurrentLocation)
                        }
                    }
                ) {
                    AppDefaultIcon(
                        modifier = Modifier
                            .padding(end = AppTheme.spacing.extraSmall)
                            .size(AppTheme.iconSize.level3),
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = ""
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        TitleTexts.Level2(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = R.string.use_my_current_location.toStringFromResId(),
                            textAlign = TextAlign.Start
                        )
                        when (currentAddressState) {
                            UiState.Loading -> {
                                TitleTexts.Level2(
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = R.string.fetching_location.toStringFromResId(),
                                    textAlign = TextAlign.Start
                                )
                            }

                            is UiState.Success -> {
                                TitleTexts.Level2(
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = currentAddressState.data.getAddressLine(
                                        0
                                    ),
                                    textAlign = TextAlign.Start
                                )
                            }

                            is UiState.ErrorMessage -> {
                                TitleTexts.Level2(
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = currentAddressState.resId.toStringFromResId(),
                                    textAlign = TextAlign.Start
                                )
                            }

                            else -> {}
                        }
                    }

                    AppDefaultIcon(
                        imageVector = Icons.Default.KeyboardArrowRight, contentDescription = ""
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.small))

            AppButtons.AppOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.small),
                onClick = {
                    if (currentAddressState is UiState.Success) {
                        onUiEvent(
                            SavedAddressUiEvent.NavigateToMaps(
                                MyAddress(
                                    lat = currentAddressState.data.latitude,
                                    lon = currentAddressState.data.longitude
                                ),
                                2
                            )
                        )
                    } else {
                        Toast.makeText(context, "Fetching location...", Toast.LENGTH_SHORT).show()
                    }
                }) {
                AppDefaultIcon(
                    modifier = Modifier
                        .padding(end = AppTheme.spacing.extraSmall)
                        .size(AppTheme.iconSize.level3),
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
                TitleTexts.Level2(
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = R.string.add_address.toStringFromResId(),
                    textAlign = TextAlign.Start
                )
                AppDefaultIcon(
                    imageVector = Icons.Default.KeyboardArrowRight, contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.medium))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )

            TitleTexts.Level2(
                modifier = Modifier.padding(AppTheme.spacing.small),
                text = R.string.saved_address.toStringFromResId(),
            )
            when (savedAddressListState) {
                is UiState.Success -> {
                    val addresses = savedAddressListState.data
                    if (addresses.isEmpty()) {
                        TitleTexts.Level2(
                            modifier = Modifier.padding(AppTheme.spacing.small),
                            text = R.string.no_saved_address.toStringFromResId(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn {
                            items(addresses) { item ->
                                if (item.selected) {
                                    onUiEvent(SavedAddressUiEvent.SetSelectedAddress(item.id))
                                }
                                AddressItem(
                                    item = item,
                                    deleteAddressState = deleteAddressState,
                                    selectAddressState = selectAddressState,
                                    onClick = {
                                        when (it) {
                                            is AddressEvent.Select -> {
                                                onUiEvent(SavedAddressUiEvent.SelectAddress(item))
                                            }

                                            is AddressEvent.Edit -> {
                                                onUiEvent(
                                                    SavedAddressUiEvent.NavigateToMaps(
                                                        item,
                                                        3
                                                    )
                                                )
                                            }

                                            is AddressEvent.Delete -> {
                                                if (item.selected) {
                                                    for (other in addresses) {
                                                        if (other.id != item.id) {
                                                            onUiEvent(
                                                                SavedAddressUiEvent.SelectAddress(
                                                                    other
                                                                )
                                                            )
                                                            break
                                                        }
                                                    }
                                                }
                                                onUiEvent(SavedAddressUiEvent.DeleteAddress(item.id))
                                            }

                                            is AddressEvent.Share -> {
                                                val myAddress =
                                                    "${item.name}\n${item.hn}, ${item.block}, ${item.sub}, ${item.loc}, ${item.area}\nPhone number: ${item.ph}"
                                                val intent = Intent(Intent.ACTION_SEND).apply {
                                                    type = "text/plain"
                                                    putExtra(Intent.EXTRA_TEXT, myAddress)
                                                }

                                                val chooserIntent =
                                                    Intent.createChooser(
                                                        intent,
                                                        R.string.share_via.toStringFromResId(context)
                                                    )
                                                chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                                startActivity(context, chooserIntent, null)
                                            }
                                        }
                                    })
                            }
                        }
                    }
                }

                UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }

                is UiState.ErrorMessage -> {
                    AppErrorScreen(desc = savedAddressListState.resId.toStringFromResId()) {
                        onUiEvent(SavedAddressUiEvent.GetSavedAddress)
                    }
                }

                else -> {}
            }
        }
    }

}

@Composable
private fun AddressItem(
    item: MyAddress,
    deleteAddressState: UiState<Boolean>,
    selectAddressState: UiState<Unit>,
    onClick: (AddressEvent) -> Unit
) {
    var loading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(deleteAddressState) {
        if (deleteAddressState is UiState.ErrorMessage) loading = false
    }
    Row(
        Modifier
            .background(
                color =
                if (item.selected) AppTheme.colors.primary.copy(0.5f)
                else if (selectAddressState is UiState.Loading) AppTheme.colors.background
                else Color.Transparent
            )
            .clickable {
                if (selectAddressState is UiState.Idle && !item.selected) onClick(
                    AddressEvent.Select
                )
            }
            .fillMaxWidth()
            .padding(AppTheme.spacing.small)
    ) {
        AppDefaultIcon(
            modifier = Modifier.padding(end = AppTheme.spacing.extraSmall),
            imageVector = Icons.Default.Home,
            contentDescription = ""
        )
        Column(Modifier.fillMaxWidth()) {
            TitleTexts.Level2(
                text = item.name
            )
            TitleTexts.Level2(
                text = "${item.hn}, ${item.block}, ${item.sub}, ${item.loc}, ${item.area}",
                modifier = Modifier.padding(vertical = 5.dp)
            )
            TitleTexts.Level2(
                text = R.string.phone_number.toStringFromResId() + ": ${item.ph}",
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Row(Modifier.fillMaxWidth()) {
                AppButtons.AppIconButton(onClick = { onClick(AddressEvent.Edit) }) {
                    AppDefaultIcon(
                        imageVector = Icons.Default.Edit, contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.width(AppTheme.spacing.extraSmall))

                Crossfade(
                    targetState = loading, label = ""
                ) {
                    if (it) {
                        AppButtons.AppIconButton(onClick = {}) {
                            CircularProgressIndicator(modifier = Modifier.padding(2.dp))
                        }
                    } else {
                        AppButtons.AppIconButton(onClick = {
                            onClick(AddressEvent.Delete)
                            loading = true
                        }) {
                            AppDefaultIcon(
                                imageVector = Icons.Default.Delete, contentDescription = ""
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(AppTheme.spacing.extraSmall))

                AppButtons.AppIconButton(onClick = { onClick(AddressEvent.Share) }) {
                    AppDefaultIcon(
                        imageVector = Icons.Default.Share, contentDescription = ""
                    )
                }
            }
        }
    }
}

private sealed interface AddressEvent {
    data object Select : AddressEvent
    data object Edit : AddressEvent
    data object Delete : AddressEvent
    data object Share : AddressEvent
}
