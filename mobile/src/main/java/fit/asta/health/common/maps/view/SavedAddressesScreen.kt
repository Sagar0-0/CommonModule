package fit.asta.health.common.maps.view

import android.content.Intent
import android.location.Address
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.maps.modal.AddressScreen
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.modal.AddressesResponse.MyAddress
import fit.asta.health.common.maps.vm.MapsViewModel
import fit.asta.health.common.ui.components.generic.AppBottomSheetScaffold
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.iconSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResponseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedAddressesScreen(
    navHostController: NavHostController,
    mapsViewModel: MapsViewModel,
    onBackPressed: () -> Unit
) {
    val gson: Gson = GsonBuilder().create()
    val context = LocalContext.current

    val addressListState by mapsViewModel.addressListState.collectAsStateWithLifecycle()
    val searchResponseState by mapsViewModel.searchResponseState.collectAsStateWithLifecycle()
    val currentLatLng by mapsViewModel.currentLatLng.collectAsStateWithLifecycle()
    val currentAddressState by mapsViewModel.currentAddressState.collectAsStateWithLifecycle()

    var sheetVisible by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    AppBottomSheetScaffold(
        topBar = {
            AppTopBar(title = "Saved Addresses", onBack = onBackPressed)
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        scaffoldState = scaffoldState,
        sheetContent = {},
        sheetDragHandle = null
    ) { padding ->
        if (sheetVisible) SearchBottomSheet(
            modifier = Modifier.padding(padding),
            onSearch = mapsViewModel::search,
            searchResponseState = searchResponseState,
            onResultClick = { myAddressItem ->
                val addJson = gson.toJson(myAddressItem)
                navHostController.navigate(route = "${AddressScreen.Map.route}/$addJson")
            },
            onClose = {
                sheetVisible = false
            }
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedVisibility(!sheetVisible) {
                OutlinedTextField(
                    maxLines = 1,
                    enabled = false,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.small)
                        .clickable {
                            sheetVisible = true
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

            Spacer(modifier = Modifier.height(spacing.medium))

            OutlinedButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.small),
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = spacing.extraSmall)
                        .size(iconSize.mediumSmall),
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = ""
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = "Use my current location",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )
                    when (currentAddressState) {
                        is ResponseState.Loading -> {
                            Text(
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = "Fetching location...",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start
                            )
                        }

                        is ResponseState.Success -> {
                            Text(
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = (currentAddressState as ResponseState.Success<Address>).data.getAddressLine(
                                    0
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start
                            )
                        }

                        is ResponseState.Error -> {
                            Text(
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = "Error fetching location",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start
                            )
                        }

                        else -> {}
                    }
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(spacing.small))

            OutlinedButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.small),
                onClick = {
                    val myAddressItem = MyAddress(
                        selected = false,
                        area = "",
                        block = "",
                        hn = "",
                        id = "",
                        lat = currentLatLng.latitude,
                        loc = "",
                        lon = currentLatLng.longitude,
                        name = "",
                        nearby = "",
                        ph = "",
                        pin = "",
                        sub = "",
                        uid = ""
                    )
                    val addJson = gson.toJson(myAddressItem)
                    navHostController.navigate(route = "${AddressScreen.Map.route}/$addJson")
                }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = spacing.extraSmall)
                        .size(iconSize.mediumSmall),
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = "Add Address",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )


            Text(
                modifier = Modifier.padding(spacing.small),
                text = "SAVED ADDRESSES",
                style = MaterialTheme.typography.titleLarge
            )
            when (addressListState) {
                is ResponseState.Success -> {
                    val addresses =
                        (addressListState as ResponseState.Success<AddressesResponse>).data.data
                    if (addresses.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(spacing.small),
                            text = "No Saved Address",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else {
                        LazyColumn {
                            items(addresses) { item ->
                                if (item.selected) {
                                    mapsViewModel.setSelectedAdId(item.id)
                                }
                                AddressItem(
                                    modifier = if (item.selected) Modifier.background(
                                        MaterialTheme.colorScheme.primary.copy(0.5f)
                                    ) else Modifier,
                                    icon = Icons.Default.Home,
                                    name = item.name,
                                    address = "${item.hn}, ${item.block}, ${item.sub}, ${item.loc}, ${item.area}",
                                    contactNo = item.ph,
                                    onClick = {
                                        if (!item.selected) mapsViewModel.selectCurrentAddress(item)
                                        onBackPressed()
                                    },
                                    onEditClick = {
                                        val addJson = gson.toJson(item).replace("/", "|")
                                        navHostController.navigate(route = "${AddressScreen.Map.route}/$addJson")
                                    },
                                    onDeleteClick = {
                                        if (item.selected) {
                                            for (i in addresses) {
                                                if (i.id != item.id) {
                                                    mapsViewModel.selectCurrentAddress(i)
                                                    break
                                                }
                                            }
                                        }
                                        mapsViewModel.deleteAddress(item.id) {
                                            Toast.makeText(
                                                context,
                                                "Address Deleted Successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    onShareClick = {
                                        val myAddress =
                                            "${item.name}\n${item.hn}, ${item.block}, ${item.sub}, ${item.loc}, ${item.area}\nPhone number: ${item.ph}"
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_TEXT, myAddress)
                                        }

                                        val chooserIntent =
                                            Intent.createChooser(intent, "Share via")
                                        chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(context, chooserIntent, null)
                                    }
                                )
                            }
                        }
                    }
                }

                is ResponseState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }

                is ResponseState.Error -> {
                    AppErrorScreen(desc = "Some internal error occurred! We are fixing it soon!") {
                        mapsViewModel.getAllAddresses()
                    }
                }

                is ResponseState.NoInternet -> {
                    AppErrorScreen()
                }

                else -> {}
            }
        }
    }

}

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    name: String,
    address: String,
    contactNo: String,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(spacing.small)) {
        Icon(
            modifier = Modifier.padding(end = spacing.extraSmall),
            imageVector = icon,
            contentDescription = ""
        )
        Column(Modifier.fillMaxWidth()) {
            Text(text = name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            Text(
                text = "Phone number: $contactNo",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Row(Modifier.fillMaxWidth()) {
                OutlinedIconButton(onClick = { onEditClick() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.width(spacing.extraSmall))

                var deleteButton by remember {
                    mutableStateOf(true)
                }
                OutlinedIconButton(
                    enabled = deleteButton,
                    onClick = {
                        deleteButton = false
                        onDeleteClick()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.width(spacing.extraSmall))

                OutlinedIconButton(onClick = { onShareClick() }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = ""
                    )
                }

            }
        }
    }
}
