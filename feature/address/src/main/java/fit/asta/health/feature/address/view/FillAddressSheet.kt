package fit.asta.health.feature.address.view

import android.location.Address
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getLocationName
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.designsystem.component.AstaValidatedTextField
import fit.asta.health.designsystem.component.AstaValidatedTextFieldType
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.customSize
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FillAddressSheet(
    modifier: Modifier = Modifier,
    address: UiState<Address>,//Used while saving new address
    myAddressItem: MyAddress,//Used while editing only
    putAddressState: UiState<Boolean>,
    onUiEvent: (FillAddressUiEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(true)
    val context = LocalContext.current

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                onUiEvent(FillAddressUiEvent.CloseSheet)
            }
        }
    }

    val defaultField = remember { FocusRequester() }
    val nameField = remember { FocusRequester() }

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
        mutableStateOf(myAddressItem.ph)
    }
    val name = rememberSaveable {
        mutableStateOf(myAddressItem.name.ifEmpty { R.string.home.toStringFromResId(context) })
    }

    LaunchedEffect(name.value) {
        if (name.value.isEmpty()) {
            nameField.requestFocus()
        } else {
            defaultField.requestFocus()
        }
    }

    LaunchedEffect(putAddressState) {
        if (putAddressState is UiState.Success) {
            closeSheet()
            Toast.makeText(
                context,
                R.string.address_saved_successfully.toStringFromResId(context),
                Toast.LENGTH_SHORT
            ).show()
            onUiEvent(FillAddressUiEvent.Back)
            onUiEvent(FillAddressUiEvent.ResetPutState)
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        dragHandle = null,
        onDismissRequest = { closeSheet() },
    ) {
        when (address) {
            UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingAnimation()
                }
            }

            is UiState.Success -> {
                val onSaveAddressClick = {
                    val newMyAddress = MyAddress(
                        area = address.data.adminArea,
                        selected = if (myAddressItem.id.isEmpty()) true else myAddressItem.selected,
                        block = block.value,
                        hn = houseNo.value,
                        id = myAddressItem.id,
                        lat = address.data.latitude,
                        lon = address.data.longitude,
                        loc = address.data.locality,
                        nearby = nearby.value,
                        name = name.value,
                        pin = address.data.postalCode,
                        ph = phone.value,
                        sub = address.data.subLocality,
                        uid = ""
                    )
                    onUiEvent(FillAddressUiEvent.SaveAddress(newMyAddress))
                }

                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = customSize.medium,
                                topEnd = customSize.medium
                            )
                        )
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
                            name = address.data.getAddressLine(0),
                            area = address.data.getLocationName()
                        )
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.CenterEnd),
                            onClick = { closeSheet() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = R.string.close.toStringFromResId()
                            )
                        }
                    }

                    Text(
                        text = R.string.save_address_as.toStringFromResId(),
                        Modifier.padding(bottom = spacing.small)
                    )
                    Row(Modifier.fillMaxWidth()) {
                        Text(modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (name.value == R.string.home.toStringFromResId()) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { name.value = R.string.home.toStringFromResId(context) }
                            .padding(8.dp), text = R.string.home.toStringFromResId())
                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (name.value == R.string.work.toStringFromResId()) MaterialTheme.colorScheme.primary else Color.Transparent)
                                .clickable { name.value = R.string.work.toStringFromResId(context) }
                                .padding(8.dp), text = R.string.work.toStringFromResId())
                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                        Text(modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (name.value != R.string.home.toStringFromResId() && name.value != R.string.work.toStringFromResId()) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { name.value = "" }
                            .padding(8.dp), text = R.string.other.toStringFromResId())
                    }

                    Spacer(modifier = Modifier.height(spacing.medium))

                    AnimatedVisibility(name.value != R.string.home.toStringFromResId() && name.value != R.string.work.toStringFromResId()) {
                        AstaValidatedTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(nameField),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            value = name.value,
                            onValueChange = {
                                name.value = it
                            },
                            label = R.string.location_name
                        )
                        Spacer(modifier = Modifier.height(spacing.medium))
                    }

                    AstaValidatedTextField(
                        modifier = Modifier.fillMaxWidth().focusRequester(defaultField),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        value = houseNo.value,
                        onValueChange = {
                            houseNo.value = it
                        },
                        label = R.string.house_number,
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    AstaValidatedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        value = block.value,
                        onValueChange = {
                            block.value = it
                        },
                        label = R.string.block_street_road,
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    AstaValidatedTextField(
                        type = AstaValidatedTextFieldType.Default(0),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                        value = nearby.value,
                        onValueChange = {
                            nearby.value = it
                        },
                        label = R.string.nearby_landmark
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    AstaValidatedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        type = AstaValidatedTextFieldType.Phone,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            onSaveAddressClick()
                        }
                        ),
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        label = R.string.phone_number
                    )

                    Crossfade(targetState = putAddressState, label = "") {
                        when (it) {
                            is UiState.Loading -> {
                                Column(
                                    Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            else -> {
                                OutlinedButton(
                                    onClick = onSaveAddressClick,
                                    modifier = Modifier
                                        .padding(spacing.medium)
                                        .fillMaxWidth()
                                        .clip(MaterialTheme.shapes.large),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    enabled = houseNo.value.isNotEmpty() && block.value.isNotEmpty() && phone.value.isNotEmpty() && phone.value.length == 10 && name.value.isNotEmpty()
                                ) {
                                    Text(
                                        text = R.string.save_address.toStringFromResId(),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }

            else -> {}
        }
    }
}