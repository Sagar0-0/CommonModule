package fit.asta.health.common.maps.view

import android.location.Address
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.ui.components.ValidatedTextField
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.common.utils.getLocationName

@Composable
fun FillAddressSheet(
    address: Address,//Used while saving new address
    myAddressItem: AddressesResponse.MyAddress,//Used while editing only
    onBackPress: () -> Unit,
    onCloseIconClick: () -> Unit,
    onSaveAddress: (address: AddressesResponse.MyAddress, onSuccess: () -> Unit) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val houseNo = rememberSaveable {
        mutableStateOf(myAddressItem.hn)
    }
    val block = rememberSaveable {
        mutableStateOf(myAddressItem.block)
    }
    val nearby = rememberSaveable {
        mutableStateOf(myAddressItem.nearby)
    }
    val phone = rememberSaveable {
        mutableStateOf(if (myAddressItem.ph == "Unknown") "" else myAddressItem.ph)
    }
    val name = rememberSaveable {
        mutableStateOf(myAddressItem.name.ifEmpty { "Home" })
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = customSize.medium, topEnd = customSize.medium))
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = spacing.medium,
                start = spacing.medium,
                end = spacing.medium
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Box(Modifier.fillMaxWidth()) {
            CurrentLocationUi(
                name = address.getAddressLine(0),
                area = getLocationName(address)
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                onClick = onCloseIconClick
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

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
                if (phone.value.length < 10) phone.value = it
            },
            label = "Phone number",
            showError = phone.value.isEmpty() || phone.value.length < 10,
            errorMessage = UiString.Dynamic("This Field can't be empty")
        )

        OutlinedButton(
            onClick = {
                val newMyAddress = AddressesResponse.MyAddress(
                    area = address.adminArea,
                    selected = if (myAddressItem.id.isEmpty()) true else myAddressItem.selected,
                    block = block.value,
                    hn = houseNo.value,
                    id = myAddressItem.id,
                    lat = address.latitude,
                    lon = address.longitude,
                    loc = address.locality,
                    nearby = nearby.value,
                    name = name.value,
                    pin = address.postalCode,
                    ph = phone.value,
                    sub = address.subLocality,
                    uid = ""
                )
                onSaveAddress(newMyAddress) {
                    Toast.makeText(context, "Address Saved", Toast.LENGTH_SHORT).show()
                    onCloseIconClick()
                    onBackPress()
                }
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