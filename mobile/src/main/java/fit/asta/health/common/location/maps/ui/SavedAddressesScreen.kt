package fit.asta.health.common.location.maps.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.location.maps.MapsActivity
import fit.asta.health.common.location.maps.MapsViewModel
import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.location.maps.modal.AddressesResponse.*
import fit.asta.health.common.location.maps.modal.MapScreens
import fit.asta.health.common.location.maps.modal.SearchResponse
import fit.asta.health.common.ui.CustomTopBar
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.iconSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
@SuppressLint("MissingPermission")
fun SavedAddressesScreen(
    startSearch: Boolean,
    navHostController: NavHostController,
    mapsViewModel: MapsViewModel,
    onBackPressed: () -> Unit
) {
    val gson: Gson = GsonBuilder().create()
    var locationLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    val context = LocalContext.current
    val fusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val locationResult = fusedLocationProviderClient.lastLocation
    val setCurrentLocation = {
        locationResult.addOnCompleteListener(context as MapsActivity) { task ->
            if (task.isSuccessful && task.result != null) {
                val lastKnownLocation = task.result
                locationLatLng = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
            } else {
                Log.d(ContentValues.TAG, "Current location is null. Using defaults.")
                Log.e(ContentValues.TAG, "Exception: %s", task.exception)
            }
        }
    }

    if (locationLatLng.latitude == 0.0 && locationLatLng.longitude == 0.0) {
        setCurrentLocation()
    }

    val addressListState by mapsViewModel.addressListState.collectAsState()
    val searchResponseState by mapsViewModel.searchResponseState.collectAsState()

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }

    Column(Modifier.fillMaxSize()) {
        CustomTopBar(text = "Saved Addresses") {
            onBackPressed()
        }

        if (startSearch) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }

        OutlinedTextField(
            maxLines = 1,
            singleLine = true,
            readOnly = false,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .padding(top = spacing.small, start = spacing.small, end = spacing.small),
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                if (it.length > 2) mapsViewModel.search(searchQuery)
            },
            placeholder = {
                Text(
                    text = "Search for area,street name..",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                if (searchQuery.length > 2) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Search Button",
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = customSize.extraMedium
                            )
                        ) {
                            searchQuery = ""
                        }
                    )
                }
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

        Spacer(modifier = Modifier.height(spacing.medium))

        val currentLocation = mapsViewModel.currentAddress.collectAsStateWithLifecycle()
        OutlinedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small),
            onClick = {
                searchQuery = ""
                val addressItem = Address(
                    selected = false,
                    area = "",
                    block = "",
                    hn = "",
                    id = "",
                    lat = locationLatLng.latitude,
                    loc = "",
                    lon = locationLatLng.longitude,
                    name = "",
                    nearby = "",
                    ph = "",
                    pin = "",
                    sub = "",
                    uid = ""
                )
                val addJson = gson.toJson(addressItem)
                navHostController.navigate(route = "${MapScreens.Map.route}/$addJson?confirm=true")
            }
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = spacing.extraSmall)
                    .size(iconSize.mediumSmall),
                imageVector = Icons.Default.LocationOn,
                contentDescription = ""
            )
            Text(
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = "Use my current location",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
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
                searchQuery = ""
                val addressItem = Address(
                    selected = false,
                    area = "",
                    block = "",
                    hn = "",
                    id = "",
                    lat = locationLatLng.latitude,
                    loc = "",
                    lon = locationLatLng.longitude,
                    name = "",
                    nearby = "",
                    ph = "",
                    pin = "",
                    sub = "",
                    uid = ""
                )
                val addJson = gson.toJson(addressItem)
                navHostController.navigate(route = "${MapScreens.Map.route}/$addJson")
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


        if (searchQuery.length < 3) {
            Text(
                modifier = Modifier.padding(spacing.small),
                text = "SAVED ADDRESSES",
                style = MaterialTheme.typography.titleLarge
            )
            when (addressListState) {
                is ResultState.Success -> {
                    val addresses =
                        (addressListState as ResultState.Success<AddressesResponse>).data.data
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
                                        searchQuery = ""
                                        val addJson = gson.toJson(item).replace("/", "|")
                                        navHostController.navigate(route = "${MapScreens.Map.route}/$addJson")
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
                                                "Address Deleted",
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

                is ResultState.Loading -> {
                    LinearProgressIndicator(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                is ResultState.Failure -> {
                    Text(text = "Some error occurred", style = MaterialTheme.typography.titleMedium)
                }

                else -> {}
            }
        } else {
            when (searchResponseState) {
                is ResultState.Success -> {
                    val results =
                        (searchResponseState as ResultState.Success<SearchResponse>).data.results
                    if (results.isEmpty()) {
                        Text(
                            text = "No result for \"$searchQuery\"",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else {
                        Text(
                            modifier = Modifier.padding(spacing.small),
                            text = "Search Results",
                            style = MaterialTheme.typography.titleLarge
                        )
                        LazyColumn {
                            items(results) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            searchQuery = ""
                                            val addressItem = Address(
                                                selected = false,
                                                area = it.name,
                                                block = "",
                                                hn = "",
                                                id = "",
                                                lat = it.geometry.location.lat,
                                                loc = "",
                                                lon = it.geometry.location.lng,
                                                name = "",
                                                nearby = "",
                                                ph = "",
                                                pin = "",
                                                sub = "",
                                                uid = ""
                                            )
                                            val addJson = gson
                                                .toJson(addressItem)
                                                .replace("/", "|")
                                            navHostController.navigate(route = "${MapScreens.Map.route}/$addJson")
                                        }
                                        .padding(spacing.medium),
                                    text = it.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                is ResultState.Loading -> {
                    LinearProgressIndicator(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                is ResultState.Failure -> {
                    Text(text = "Some error occurred", style = MaterialTheme.typography.titleMedium)
                    Log.d(
                        "SearchError",
                        (searchResponseState as ResultState.Failure).msg.message ?: ""
                    )
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
