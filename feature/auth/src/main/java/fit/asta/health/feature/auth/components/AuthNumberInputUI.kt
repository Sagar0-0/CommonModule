package fit.asta.health.feature.auth.components

import android.app.Activity
import android.app.PendingIntent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator


/**
 * This Function draws the Phone Number Input Text Fields UI which contains the Country Code input,
 * Phone Number input and the Generate OTP Button
 *
 * @param phoneNumber This is the Phone Number inputted By the User
 * @param countryCode This is the Country Code inputted by the user
 * @param onPhoneNumberChange This function is invoked when the Phone Number is changed
 * @param onCountryCodeChange This function is invoked when the Country Code is changed
 * @param onGenerateOtpClick This function is invoked when the generate code button is clicked
 */
@Composable
fun AuthNumberInputUI(
    phoneNumber: String,
    countryCode: String,
    onPhoneNumberChange: (String) -> Unit,
    onCountryCodeChange: (String) -> Unit,
    onGenerateOtpClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    // This Function gets the Phone Number the user may be using when the user taps the Text Fields
    val getPhoneNumberHint: @Composable () -> Unit = {
        PhoneNumberHintIntentResultLauncher(
            onPhoneNumberChange = onPhoneNumberChange,
            onCountryCodeChange = onCountryCodeChange,
            onGenerateOtpClick = onGenerateOtpClick
        )
    }

    // This variable states if we should try to find the Phone Number Hint or not
    var shouldGetPhoneNumberHint by remember { mutableStateOf(false) }

    // Checking if we need to start an intent for getting the Phone Number Hints
    if (shouldGetPhoneNumberHint) {
        getPhoneNumberHint()
        shouldGetPhoneNumberHint = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Contains the Country Code and the Phone Number Text Fields
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.hasFocus)
                        shouldGetPhoneNumberHint = true
                },
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // TODO :- Change this code with the country code thing
            AppOutlinedTextField(
                modifier = Modifier.weight(.27f),
                value = countryCode,
                label = "Code",
                onValueChange = onCountryCodeChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                appTextFieldType = AppTextFieldValidator(
                    appTextFieldType = AppTextFieldType.Custom(
                        minSize = 1,
                        maxSize = 4,
                        getErrorMessageLogic = { _, _ -> "" },

                        )
                ),
                singleLine = true
            )

            // Phone Number Input Text Field
            AppOutlinedTextField(
                modifier = Modifier.weight(.73f),
                value = phoneNumber,
                label = "Enter Phone Number",
                onValueChange = onPhoneNumberChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onGenerateOtpClick()
                    }
                ),
                appTextFieldType = AppTextFieldValidator(appTextFieldType = AppTextFieldType.Phone),
                singleLine = true
            )
        }

        // Generate OTP Button
        AppFilledButton(
            modifier = Modifier.fillMaxWidth(),
            textToShow = "Generate OTP",
            onClick = onGenerateOtpClick
        )
    }
}


/**
 * This Function starts an Intent to fetch the User's Phone Number Hints and auto fill the Phone
 * Number Text Field with the Fetched Phone Number
 *
 * @param onPhoneNumberChange This function is invoked when the Phone Number is changed
 * @param onCountryCodeChange This function is invoked when the Country Code is changed
 * @param onGenerateOtpClick This function is invoked when the generate code button is clicked
 */
@Composable
private fun PhoneNumberHintIntentResultLauncher(
    onPhoneNumberChange: (String) -> Unit,
    onCountryCodeChange: (String) -> Unit,
    onGenerateOtpClick: () -> Unit
) {

    val context = LocalContext.current

    // This is the request Launcher Variable which launches an Intent and fetches the Phone Number hint
    val phoneNumberHintIntentResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            try {

                // Fetching the Phone Number Hints
                val phoneNumberHint = Identity.getSignInClient(context as Activity)
                    .getPhoneNumberFromIntent(result.data)

                // Changing the Country code and Phone Number with the Fetched values
                onCountryCodeChange(phoneNumberHint.dropLast(10))
                onPhoneNumberChange(phoneNumberHint.takeLast(10))

                // Automatically Hitting the Generate OTP Button since the OTP is fetched directly
                onGenerateOtpClick()
            } catch (e: Exception) {
                Log.e("Phone Number Hint", e.toString())
            }
        }

    // Finally Requesting the Intent Launcher to launch this Intent
    val request: GetPhoneNumberHintIntentRequest =
        GetPhoneNumberHintIntentRequest.builder().build()

    try {

        // Starting the Intent
        Identity.getSignInClient(context as Activity)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { result: PendingIntent ->
                try {
                    phoneNumberHintIntentResultLauncher.launch(
                        IntentSenderRequest
                            .Builder(result)
                            .build()
                    )
                } catch (e: Exception) {
                    Log.e("Phone Number Hint", e.toString())
                }
            }
            .addOnFailureListener {
                Log.e("Phone Number Hint", it.toString())
            }
    } catch (e: Exception) {
        Log.e("Phone Number Hint", e.toString())
    }
}