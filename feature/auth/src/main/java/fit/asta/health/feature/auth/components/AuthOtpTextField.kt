package fit.asta.health.feature.auth.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.BodyTexts

/**
 * This Composable function is used to show the Verification Code OTP UI which would be used for
 * receiving verification OTPs from the User
 *
 * @param modifier This is the modifier that can be passed by the Parent Function for better
 * modifications
 * @param otp This is the OTP which would be given as input by the user
 * @param otpCount This is the Maximum number of digits in the OTP
 * @param onOtpTextChange This function will be invoked when the input is changed by the user
 */
@Composable
fun AuthOtpTextField(
    modifier: Modifier = Modifier,
    otp: String,
    otpCount: Int = 6,
    onOtpTextChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    // Creating the Basic Text Field Parent Function
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otp, selection = TextRange(otp.length)),
        onValueChange = {

            // Checking if the Input is over the otpCount ... if it is then we are not invoking
            if (it.text.length <= otpCount)
                onOtpTextChange(it.text)
        },

        // Setting Keyboard Options to only take Number Inputs
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        decorationBox = {

            // Making the Composable which would show the inside of the Text Field
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                // Repeating and making the Boxes for pageCount Times
                repeat(otpCount) { index ->

                    // This composable function draws the Texts and Boxes in the UI
                    TextBoxesUI(otp = otp, currentIndex = index)
                }
            }
        }
    )
}


/**
 * This composable function draws the Boxes and the Texts UI
 *
 * @param otp This is the Otp Entered By the user
 * @param currentIndex This is the current Index of the Box i.e it defines which number of box is
 * being created from 0 to pageCount - 1
 */
@Composable
private fun TextBoxesUI(
    otp: String,
    currentIndex: Int
) {

    // The Current Character that would be shown in the Box
    val char = if (currentIndex < otp.length) otp[currentIndex].toString() else "0"

    // The Color of the Character and the Border is decided here
    val color = when {
        currentIndex >= otp.length -> AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level3)
        else -> AppTheme.colors.onSurface
    }

    // Box / Text UI
    BodyTexts.Level1(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = color,
                shape = AppTheme.shape.level1
            )
            .padding(AppTheme.spacing.level2),
        text = char,
        color = color,
        textAlign = TextAlign.Center
    )
}