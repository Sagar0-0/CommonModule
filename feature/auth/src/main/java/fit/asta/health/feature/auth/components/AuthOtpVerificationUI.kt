package fit.asta.health.feature.auth.components

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.google.android.gms.auth.api.phone.SmsRetriever
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.feature.auth.screens.OTPReceiver
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

/**
 * This Function draws the OTP Verification Whole UI with the Text Field for OTP, The Verify and
 * resend Button and the Wrong Phone Number Button with it
 *
 * @param otp This is the otp input given by the user
 * @param onOtpTextChange This function is invoked when the OTP is changed by the user
 * @param onWrongNumberButtonClick This function is invoked when the user clicks the Wrong Phone
 * Number Button
 */
@Composable
fun AuthOtpVerificationUI(
    modifier: Modifier = Modifier,
    otp: String,
    onOtpTextChange: (String) -> Unit,
    onWrongNumberButtonClick: () -> Unit,
    onVerifyOtpClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var ticks by rememberSaveable { mutableIntStateOf(30) }
    LaunchedEffect(Unit) {
        while (ticks > 0) {
            delay(1.seconds)
            ticks--
        }
    }


    // Starting the Broadcast Receiver
    SmsReceiver(
        onOtpTextChange = onOtpTextChange,
        onVerifyOtpClick = {
            focusManager.clearFocus()
            onVerifyOtpClick()
        }
    )

    Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {

        // This draws the Custom Text Field for the OTP inputs
        AuthOtpTextField(
            otp = otp,
            onOtpTextChange = onOtpTextChange
        )

        // Contains the Verify and the Resend Button
        Row(
            modifier = modifier
                .padding(horizontal = AppTheme.spacing.level2)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            // Verify Button
            AppFilledButton(
                modifier = Modifier.weight(1f),
                textToShow = "Verify"
            ) {
                focusManager.clearFocus()
                onVerifyOtpClick()
            }

            // Resend OTP Button
            AppOutlinedButton(
                modifier = Modifier.weight(1f),
                textToShow = if (ticks != 0)
                    "Resend in $ticks sec"
                else
                    "Resend OTP",
                enabled = (ticks == 0)
            ) {
                // TODO :- Resend OTP Code
            }
        }

        // Wrong Phone Number entered Button
        AppTextButton(
            modifier = Modifier.fillMaxWidth(),
            textToShow = "Wrong Phone Number ?",
            onClick = onWrongNumberButtonClick
        )
    }
}


/**
 * This function starts the BroadCast Receiver to fetch the User's SMS Messages and auto fill the
 * OTP Text Field with the Fetched SMS
 *
 * @param onOtpTextChange This function is triggered when the OTP text is changed
 * @param onVerifyOtpClick This function is triggered when the OTP is fetched from the Receiver
 */
@Composable
private fun SmsReceiver(
    onOtpTextChange: (String) -> Unit,
    onVerifyOtpClick: () -> Unit
) {

    val context = LocalContext.current

    // This is the request Launcher which launches an Intent to fetch the user's SMS Messages
    val smsReceiverLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {

                // Fetching SMS message content
                val message = result.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

                // Finding the OTP Code from the whole SMS content and updating the OTP
                val smsCode = message?.let { "[0-9]{6}".toRegex().find(it) }
                smsCode?.value?.let {
                    onOtpTextChange(it)
                }

                // Starting the Flow to verify the OTP
                onVerifyOtpClick()
            } else
                Toast.makeText(context, "Failed to retrieve OTP", Toast.LENGTH_SHORT).show()
        }
    )

    // Starting the Broadcast Receiver to fetch the SMS messages of the User
    DisposableEffect(context) {
        val myOTPReceiver = OTPReceiver(
            onSuccess = { intent ->
                smsReceiverLauncher.launch(intent)
            },
            onFailure = {
                Toast.makeText(context, "Failed to retrieve OTP", Toast.LENGTH_SHORT).show()
            }
        )

        // Registering the Receiver
        myOTPReceiver.register(context)

        // Upon Dispose we unregister the Receiver
        onDispose { myOTPReceiver.unregister(context) }
    }
}