package fit.asta.health.feature.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.feature.auth.util.OtpVerifier
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
 * @param onVerifyOtpClick This function is invoked when the Verify Button is Clicked
 * @param onResendOtpClick this function is invoked when the Resend Button is Clicked
 */
@Composable
fun AuthOtpVerificationUI(
    modifier: Modifier = Modifier,
    otp: String,
    onOtpTextChange: (String) -> Unit,
    onWrongNumberButtonClick: () -> Unit,
    onVerifyOtpClick: () -> Unit,
    onResendOtpClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    // This variable contains how much time we have till the user can ask to resend OTP
    var ticks by remember { mutableIntStateOf(30) }

    // Keeps tab of if we need to restart the Ticking
    var restartTick by remember { mutableStateOf(false) }

    LaunchedEffect(restartTick) {
        if (restartTick) {
            ticks = 30
            restartTick = false
        } else
            while (ticks > 0) {
                delay(1.seconds)
                ticks--
            }
    }

    // Starting the Broadcast Receiver
    OtpVerifier.SmsReceiver(
        onOtpTextChange = onOtpTextChange,
        onVerifyOtpClick = {
            focusManager.clearFocus()
            onVerifyOtpClick()
        }
    )


    // This draws the Custom Text Field for the OTP inputs
    AuthOtpTextField(
        otp = otp,
        onOtpTextChange = onOtpTextChange,
        onDoneClick = onVerifyOtpClick
    )

    // Contains the Verify and the Resend Button
    Row(
        modifier = modifier
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
                "Resend in ${ticks}s"
            else
                "Resend OTP",
            enabled = (ticks == 0)
        ) {
            restartTick = true
            onResendOtpClick()
        }
    }

    // Wrong Phone Number entered Button
    AppTextButton(
        textToShow = "Wrong Phone Number?",
        onClick = onWrongNumberButtonClick
    )
}