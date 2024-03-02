package fit.asta.health.feature.address.view

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.data.address.remote.modal.PutAddressResponse
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.background.appRememberModalBottomSheetState
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun FillAddressSheet(
    sheetVisible: Boolean,
    modifier: Modifier = Modifier,
    type: FillAddressSheetType,
    putAddressState: UiState<PutAddressResponse>,
    onUiEvent: (FillAddressUiEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = appRememberModalBottomSheetState(true)
    val context = LocalContext.current

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                onUiEvent(FillAddressUiEvent.CloseSheet)
            }
        }
    }
    val myAddressItem = remember {
        when (type) {
            is FillAddressSheetType.SaveCurrentAddress -> {
                type.address
            }

            is FillAddressSheetType.EnterNewAddress -> {
                type.address
            }
        }
    }
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
        mutableStateOf(
            myAddressItem.name.ifEmpty {
                R.string.home.toStringFromResId(context)
            }
        )
    }

    var isNameValid by remember {
        mutableStateOf(true)
    }
    var isHnValid by remember {
        mutableStateOf(false)
    }
    var isBlkValid by remember {
        mutableStateOf(false)
    }
    var isPhoneValid by remember {
        mutableStateOf(false)
    }

    val (nameField, houseField, blockField, nearbyField, phoneField) = FocusRequester.createRefs()
    LaunchedEffect(Unit) {
        if (name.value.isEmpty()) {
            nameField.requestFocus()
        } else {
            houseField.requestFocus()
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
            onUiEvent(FillAddressUiEvent.OnPutSuccess)
            onUiEvent(FillAddressUiEvent.ResetPutState)
        }
    }

    AppModalBottomSheet(
        sheetVisible = sheetVisible,
        modifier = modifier,
        sheetState = bottomSheetState,
        dragHandle = null,
        onDismissRequest = { closeSheet() },
    ) {
        val onSaveAddressClick = {
            val newMyAddress = MyAddress(
                area = myAddressItem.area,
                selected = if (myAddressItem.id.isEmpty()) true else myAddressItem.selected,
                block = block.value,
                hn = houseNo.value,
                id = myAddressItem.id,
                lat = myAddressItem.lat,
                lon = myAddressItem.lon,
                loc = myAddressItem.loc,
                nearby = nearby.value,
                name = name.value,
                pin = myAddressItem.pin,
                ph = phone.value,
                sub = myAddressItem.sub
            )
            onUiEvent(FillAddressUiEvent.SaveAddress(newMyAddress))
        }

        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = AppTheme.customSize.level2,
                        topEnd = AppTheme.customSize.level2
                    )
                )
                .background(AppTheme.colors.background)
                .padding(
                    top = AppTheme.spacing.level2,
                    start = AppTheme.spacing.level2,
                    end = AppTheme.spacing.level2
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Box(Modifier.fillMaxWidth()) {
                CurrentLocationUi(
                    name = myAddressItem.addressLine,
                    area = myAddressItem.shortAddress
                )
                AppIconButton(
                    imageVector = Icons.Default.Close,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    iconDesc = R.string.close.toStringFromResId()
                ) { closeSheet() }
            }

            BodyTexts.Level1(
                text = R.string.save_address_as.toStringFromResId(),
                Modifier.padding(bottom = AppTheme.spacing.level1)
            )
            Row(Modifier.fillMaxWidth()) {
                BodyTexts.Level1(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (name.value == R.string.home.toStringFromResId()) AppTheme.colors.primary else Color.Transparent)
                        .clickable { name.value = R.string.home.toStringFromResId(context) }
                        .padding(8.dp), text = R.string.home.toStringFromResId())
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                BodyTexts.Level1(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (name.value == R.string.work.toStringFromResId()) AppTheme.colors.primary else Color.Transparent)
                        .clickable { name.value = R.string.work.toStringFromResId(context) }
                        .padding(8.dp), text = R.string.work.toStringFromResId())
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                BodyTexts.Level1(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (name.value != R.string.home.toStringFromResId() && name.value != R.string.work.toStringFromResId()) AppTheme.colors.primary else Color.Transparent)
                        .clickable { name.value = "" }
                        .padding(8.dp), text = R.string.other.toStringFromResId())
            }

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            AnimatedVisibility(name.value != R.string.home.toStringFromResId() && name.value != R.string.work.toStringFromResId()) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameField)
                        .focusProperties {
                            next = houseField
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    isValidText = {
                        isNameValid = it
                    },
                    label = R.string.location_name.toStringFromResId()
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(houseField)
                    .focusProperties {
                        next = blockField
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                value = houseNo.value,
                onValueChange = {
                    houseNo.value = it
                },
                isValidText = {
                    isHnValid = it
                },
                label = R.string.house_number.toStringFromResId()
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(blockField)
                    .focusProperties {
                        next = nearbyField
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                value = block.value,
                onValueChange = {
                    block.value = it
                },
                isValidText = {
                    isBlkValid = it
                },
                label = R.string.block_street_road.toStringFromResId()
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            AppTextField(
                appTextFieldType = AppTextFieldValidator(AppTextFieldType.Custom(0, 256)),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(nearbyField)
                    .focusProperties {
                        next = phoneField
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                value = nearby.value,
                onValueChange = {
                    nearby.value = it
                },
                label = R.string.nearby_landmark.toStringFromResId()
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(phoneField),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSaveAddressClick()
                    }
                ),
                value = phone.value,
                onValueChange = { phone.value = it },
                isValidText = {
                    isPhoneValid = it
                },
                label = R.string.phone_number.toStringFromResId()
            )

            Crossfade(targetState = putAddressState, label = "") {
                when (it) {
                    is UiState.Loading -> {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AppCircularProgressIndicator()
                        }
                    }

                    else -> {
                        AppOutlinedButton(
                            textToShow = R.string.save_address.toStringFromResId(),
                            modifier = Modifier
                                .padding(AppTheme.spacing.level2)
                                .fillMaxWidth()
                                .clip(AppTheme.shape.level2),
                            enabled = isNameValid && isHnValid && isBlkValid && isPhoneValid,
                            onClick = onSaveAddressClick
                        )
                    }
                }
            }
        }
    }
}