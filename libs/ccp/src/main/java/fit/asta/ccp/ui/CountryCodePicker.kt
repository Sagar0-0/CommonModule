package fit.asta.ccp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fit.asta.ccp.R
import fit.asta.ccp.data.utils.checkPhoneNumber
import fit.asta.ccp.data.utils.getDefaultLangCode
import fit.asta.ccp.data.utils.getDefaultPhoneCode
import fit.asta.ccp.data.utils.getLibCountries
import fit.asta.ccp.data.utils.getNumberHint
import fit.asta.ccp.transformation.PhoneNumberTransformation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

private var fullNumberState: String by mutableStateOf("")
private var checkNumberState: Boolean by mutableStateOf(false)
private var phoneNumberState: String by mutableStateOf("")
private var countryCodeState: String by mutableStateOf("")

@Composable
fun CountryCodePicker(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    shape: Shape = AppTheme.shape.level3,
    color: Color = AppTheme.colors.background,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    focusedBorderColor: Color = AppTheme.colors.primary,
    unfocusedBorderColor: Color = AppTheme.colors.onSecondary,
    cursorColor: Color = AppTheme.colors.primary,
    bottomStyle: Boolean = false,
) {
    val context = LocalContext.current
    var textFieldValue by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalTextInputService.current
    var phoneCode by rememberSaveable {
        mutableStateOf(
            getDefaultPhoneCode(
                context
            )
        )
    }
    var defaultLang by rememberSaveable {
        mutableStateOf(
            getDefaultLangCode(context)
        )
    }

    fullNumberState = phoneCode + textFieldValue
    phoneNumberState = textFieldValue
    countryCodeState = defaultLang


    AppSurface(color = color) {
        Column(modifier = Modifier.padding(all = AppTheme.spacing.level2)) {
            if (bottomStyle) {
                CCPDialog(
                    pickedCountry = {
                        phoneCode = it.countryPhoneCode
                        defaultLang = it.countryCode
                    },
                    defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                    showCountryCode = showCountryCode,
                    showFlag = showCountryFlag,
                    showCountryName = true
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                AppOutlinedTextField(modifier = modifier.fillMaxWidth(),
                    shape = shape,
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        if (text != it) {
                            onValueChange(it)
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = cursorColor,
                        focusedBorderColor = if (getErrorStatus()) Color.Red else focusedBorderColor,
                        unfocusedBorderColor = if (getErrorStatus()) Color.Red else unfocusedBorderColor,
                    ),
                    visualTransformation = PhoneNumberTransformation(getLibCountries.single { it.countryCode == defaultLang }.countryCode.uppercase()),
                    placeholder = {
                        BodyTexts.Level3(
                            text = stringResource(
                                id = getNumberHint(
                                    getLibCountries.single { it.countryCode == defaultLang }.countryCode.lowercase()
                                )
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword,
                        autoCorrect = true,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hideSoftwareKeyboard()
                    }),
                    leadingIcon = {
                        if (!bottomStyle) Row {
                            Column {
                                CCPDialog(
                                    pickedCountry = {
                                        phoneCode = it.countryPhoneCode
                                        defaultLang = it.countryCode
                                    },
                                    defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                                    showCountryCode = showCountryCode,
                                    showFlag = showCountryFlag
                                )
                            }
                        }
                    },
                    trailingIcon = {
                        AppIconButton(imageVector = Icons.Filled.Clear,
                            iconTint = if (getErrorStatus()) Color.Red else AppTheme.colors.onSurface,
                            onClick = {
                                textFieldValue = ""
                                onValueChange("")
                            })
                    })
            }
            if (getErrorStatus()) CaptionTexts.Level2(
                text = stringResource(id = R.string.invalid_number),
                color = AppTheme.colors.error,
                modifier = Modifier.padding(top = 0.8.dp)
            )
        }
    }
}

fun getFullPhoneNumber(): String {
    return fullNumberState
}

fun getOnlyPhoneNumber(): String {
    return phoneNumberState
}

fun getErrorStatus(): Boolean {
    return !checkNumberState
}

fun isPhoneNumber(): Boolean {
    val check = checkPhoneNumber(
        phone = phoneNumberState, fullPhoneNumber = fullNumberState, countryCode = countryCodeState
    )
    checkNumberState = check
    return check
}