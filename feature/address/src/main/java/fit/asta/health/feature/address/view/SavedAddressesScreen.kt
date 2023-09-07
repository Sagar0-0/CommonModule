package fit.asta.health.feature.address.view

import android.content.Intent
import android.location.Address
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import fit.asta.health.designsystem.components.generic.AppBottomSheetScaffold
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.LocalSpacing
import fit.asta.health.designsystem.theme.iconSize
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SavedAddressesScreen(
    savedAddressListState: UiState<List<MyAddress>>,
    searchSheetVisible: Boolean,
    fillAddressSheetVisible: Boolean,
    deleteAddressState: UiState<Boolean>,
    selectAddressState: UiState<Unit>,
    currentAddressState: UiState<Address>,
    onUiEvent: (SavedAddressUiEvent) -> Unit
) {
    val context = LocalContext.current

    val scaffoldState = rememberBottomSheetScaffoldState()

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

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedVisibility(!searchSheetVisible) {
                OutlinedTextField(
                    maxLines = 1,
                    enabled = false,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalSpacing.current.small)
                        .clickable {
                            onUiEvent(SavedAddressUiEvent.ShowSearchSheet)
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

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            AnimatedVisibility(!fillAddressSheetVisible) {
                OutlinedButton(
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LocalSpacing.current.small),
                    onClick = {
                        if (currentAddressState is UiState.Success) {
                            onUiEvent(SavedAddressUiEvent.ShowFillAddressSheet)
                        } else if (currentAddressState is UiState.Error) {
                            onUiEvent(SavedAddressUiEvent.UpdateCurrentLocation)
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = LocalSpacing.current.extraSmall)
                            .size(iconSize.mediumSmall),
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = ""
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = R.string.use_my_current_location.toStringFromResId(),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                        when (currentAddressState) {
                            UiState.Loading -> {
                                Text(
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = R.string.fetching_location.toStringFromResId(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Start
                                )
                            }

                            is UiState.Success -> {
                                Text(
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = currentAddressState.data.getAddressLine(
                                        0
                                    ),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Start
                                )
                            }

                            is UiState.Error -> {
                                Text(
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = currentAddressState.resId.toStringFromResId(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Start
                                )
                            }

                            else -> {}
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight, contentDescription = ""
                    )
                }
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.small))

            OutlinedButton(colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary
            ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LocalSpacing.current.small),
                onClick = {
                    onUiEvent(SavedAddressUiEvent.NavigateToMaps())
                }) {
                Icon(
                    modifier = Modifier
                        .padding(end = LocalSpacing.current.extraSmall)
                        .size(iconSize.mediumSmall),
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = R.string.add_address.toStringFromResId(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight, contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )

            Text(
                modifier = Modifier.padding(LocalSpacing.current.small),
                text = R.string.saved_address.toStringFromResId(),
                style = MaterialTheme.typography.titleLarge
            )
            when (savedAddressListState) {
                is UiState.Success -> {
                    val addresses = savedAddressListState.data
                    if (addresses.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(LocalSpacing.current.small),
                            text = R.string.no_saved_address.toStringFromResId(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
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
                                                onUiEvent(SavedAddressUiEvent.NavigateToMaps(item))
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

                is UiState.Error -> {
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
        if (deleteAddressState is UiState.Error) loading = false
    }
    Row(
        Modifier
            .background(
                color =
                if (item.selected) MaterialTheme.colorScheme.primary.copy(0.5f)
                else if (selectAddressState is UiState.Loading) MaterialTheme.colorScheme.background
                else Color.Transparent
            )
            .clickable {
                if (selectAddressState is UiState.Idle && !item.selected) onClick(
                    AddressEvent.Select
                )
            }
            .fillMaxWidth()
            .padding(LocalSpacing.current.small)
    ) {
        Icon(
            modifier = Modifier.padding(end = LocalSpacing.current.extraSmall),
            imageVector = Icons.Default.Home,
            contentDescription = ""
        )
        Column(Modifier.fillMaxWidth()) {
            Text(text = item.name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${item.hn}, ${item.block}, ${item.sub}, ${item.loc}, ${item.area}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            Text(
                text = R.string.phone_number.toStringFromResId() + ": ${item.ph}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Row(Modifier.fillMaxWidth()) {
                OutlinedIconButton(onClick = { onClick(AddressEvent.Edit) }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.width(LocalSpacing.current.extraSmall))

                Crossfade(
                    targetState = loading, label = ""
                ) {
                    if (it) {
                        OutlinedIconButton(onClick = {}) {
                            CircularProgressIndicator(modifier = Modifier.padding(2.dp))
                        }
                    } else {
                        OutlinedIconButton(onClick = {
                            onClick(AddressEvent.Delete)
                            loading = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete, contentDescription = ""
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(LocalSpacing.current.extraSmall))

                OutlinedIconButton(onClick = { onClick(AddressEvent.Share) }) {
                    Icon(
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
