package fit.asta.health.common.location.maps.ui

import android.location.Address
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fit.asta.health.common.location.maps.MapsViewModel
import fit.asta.health.common.location.maps.modal.AddressesResponse
import fit.asta.health.common.ui.components.ValidatedTextField
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.tools.walking.view.component.RoundIconButton

@Composable
fun FillAddressSheet(
    navHostController: NavHostController,
    mapsViewModel: MapsViewModel,
    address: Address,
    addressItem: AddressesResponse.Address,
    onCloseIconClick: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val houseNo = rememberSaveable {
        mutableStateOf(addressItem.hn)
    }
    val block = rememberSaveable {
        mutableStateOf(addressItem.block)
    }
    val nearby = rememberSaveable {
        mutableStateOf(addressItem.nearby)
    }
    val phone = rememberSaveable {
        mutableStateOf(addressItem.ph)
    }
    val name = rememberSaveable {
        mutableStateOf(addressItem.name.ifEmpty { "Home" })
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundIconButton(
            imageVector = Icons.Default.Close,
            modifier = Modifier
                .padding(spacing.extraMedium),
            onClick = onCloseIconClick
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = customSize.medium, topEnd = customSize.medium))
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = spacing.medium,
                    start = spacing.medium,
                    end = spacing.medium
                )
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            CurrentLocationUi(
                name = (if (addressItem.area.isNotEmpty()) addressItem.area + ", " else "") + (address.locality
                    ?: address.subAdminArea
                    ?: address.adminArea ?: ""),
                area = (address.adminArea ?: "") + ", " + (address.countryName ?: "")
            )

            Text(text = "Save address as*", Modifier.padding(bottom = 8.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (name.value == "Home") MaterialTheme.colorScheme.primary else Color.Transparent)
                    .clickable { name.value = "Home" }
                    .padding(8.dp), text = "Home")
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                Text(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (name.value == "Work") MaterialTheme.colorScheme.primary else Color.Transparent)
                    .clickable { name.value = "Work" }
                    .padding(8.dp), text = "Work")
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                Text(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (name.value != "Home" && name.value != "Work") MaterialTheme.colorScheme.primary else Color.Transparent)
                    .clickable { name.value = "" }
                    .padding(8.dp), text = "Other")
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            if (name.value != "Home" && name.value != "Work") {
                ValidatedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    singleLine = true,
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    label = "Location Name",
                    showError = name.value.isEmpty(),
                    errorMessage = UiString.Dynamic("This Field can't be empty")
                )
                Spacer(modifier = Modifier.height(spacing.medium))
            }

            ValidatedTextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                singleLine = true,
                value = houseNo.value,
                onValueChange = {
                    houseNo.value = it
                },
                label = "House Number*",
                showError = houseNo.value.isEmpty(),
                errorMessage = UiString.Dynamic("This Field can't be empty")
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            ValidatedTextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                singleLine = true,
                value = block.value,
                onValueChange = {
                    block.value = it
                },
                label = "Block / Street / Road*",
                showError = block.value.isEmpty(),
                errorMessage = UiString.Dynamic("This Field can't be empty")
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            ValidatedTextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                singleLine = true,
                value = nearby.value,
                onValueChange = {
                    nearby.value = it
                },
                label = "Nearby landmark",
                errorMessage = UiString.Empty
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            ValidatedTextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                singleLine = true,
                value = phone.value,
                onValueChange = {
                    phone.value = it
                },
                label = "Phone number",
                showError = phone.value.isEmpty(),
                errorMessage = UiString.Dynamic("This Field can't be empty")
            )

            OutlinedButton(
                onClick = {
                    val newAddress = AddressesResponse.Address(
                        area = address.adminArea,
                        selected = if (addressItem.id.isEmpty()) true else addressItem.selected,
                        block = block.value,
                        hn = houseNo.value,
                        id = addressItem.id,
                        lat = address.latitude,
                        lon = address.longitude,
                        loc = address.locality,
                        nearby = nearby.value,
                        name = name.value,
                        pin = address.postalCode,
                        ph = phone.value,
                        sub = address.subLocality,
                        uid = mapsViewModel.uId
                    )
                    mapsViewModel.putAddress(newAddress)
                    Toast.makeText(context, "Address Saved", Toast.LENGTH_SHORT).show()
                    onCloseIconClick()
                    mapsViewModel.getAllAddresses()
                    navHostController.navigateUp()
                },
                modifier = Modifier
                    .padding(spacing.medium)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                enabled = houseNo.value.isNotEmpty() && block.value.isNotEmpty() && phone.value.isNotEmpty() && name.value.isNotEmpty()
            ) {
                Text(text = "Save address", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}