package fit.asta.health.feature.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTextButton

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
    onWrongNumberButtonClick: () -> Unit
) {

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
                // TODO :- Verify OTP
            }

            // Resend OTP Button
            AppOutlinedButton(
                modifier = Modifier.weight(1f),
                textToShow = "Resend in 30 sec"
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