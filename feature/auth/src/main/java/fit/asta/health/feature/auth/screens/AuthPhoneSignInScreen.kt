package fit.asta.health.feature.auth.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.components.AuthNumberInputUI
import fit.asta.health.feature.auth.components.AuthOtpVerificationUI
import fit.asta.health.feature.auth.util.OtpVerifier

@Composable
fun AuthPhoneSignInScreen(
    loginState: UiState<Unit>,
    onUiEvent: (AuthUiEvent) -> Unit
) {

    val context = LocalContext.current

    // phone Number inputted by the User
    var phoneNumber by remember { mutableStateOf("") }

    // Country Code Inputted By the user
    var countryCode by remember { mutableStateOf("+91") }

    // This determines if to show the OTP input text fields or the Phone Number Input Fields
    var otpVisible by remember { mutableStateOf(false) }

    // This is the OTP entered by the user
    var otp by remember { mutableStateOf("") }

    // This is the heading text of the App asking the App for necessary inputs
    val textToShow = when (otpVisible) {
        true -> "Enter the Otp Sent To phone Number ${countryCode}$phoneNumber"
        false -> "Enter your mobile number to get the OTP"
    }

    // Used to show the Loading state of the Whole Flow
    var loading by remember { mutableStateOf(false) }

    // This is the Verification ID which would be matched for OTP
    var verificationID by remember { mutableStateOf("") }

    // This is the function for sending the OTP
    val onSendOtp = {
        if (phoneNumber.length != 10 || countryCode.length < 2)
            Toast.makeText(context, "Please enter a valid Number", Toast.LENGTH_SHORT).show()
        else {
            loading = true

            OtpVerifier.startSMSRetrieverClient(context)
            OtpVerifier.startPhoneVerification(
                number = "${countryCode}${phoneNumber}",
                activity = context as Activity,
                onVerificationComplete = {
                    onUiEvent(AuthUiEvent.SignInWithCredentials(it))
                },
                onVerificationFailure = {
                    Toast.makeText(
                        context,
                        "Verification failed.. ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    loading = false
                    otpVisible = false
                },
                onCodeSent = {
                    verificationID = it
                    otpVisible = true
                    loading = false
                }
            )
        }
    }

    // This is the Function for checking the OTP
    val onOtpSubmit = {
        if (otp.length != 6)
            Toast.makeText(context, "Please enter a valid OTP", Toast.LENGTH_SHORT).show()
        else {
            loading = true
            val credential: AuthCredential = PhoneAuthProvider.getCredential(verificationID, otp)
            onUiEvent(AuthUiEvent.SignInWithCredentials(credential))
        }
    }

    // Resetting all the Values once the Login Fails and asks the User to enter and check data again
    when (loginState) {
        is UiState.ErrorRetry -> {
            AppRetryCard(text = loginState.resId.toStringFromResId()) {
                onUiEvent(AuthUiEvent.OnLoginFailed)
            }
        }

        UiState.Loading -> {
            loading = true
        }

        else -> {}
    }

    AppScreen {

        // Contains Both the heading text, Number Input and the Verification Code input Fields
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.spacing.level2),
            contentAlignment = Alignment.Center
        ) {

            Column {

                // heading Text asking user to give his inputs
                TitleTexts.Level2(text = textToShow)

                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

                // This Contains the Phone Number Input UI
                Crossfade(targetState = otpVisible, label = "") { visibility ->
                    if (!visibility)
                        AuthNumberInputUI(
                            phoneNumber = phoneNumber,
                            countryCode = countryCode,
                            onPhoneNumberChange = {
                                phoneNumber = it
                            },
                            onCountryCodeChange = {
                                countryCode = it
                            },
                            onGenerateOtpClick = onSendOtp
                        )
                    else
                        AuthOtpVerificationUI(
                            otp = otp,
                            onOtpTextChange = {
                                otp = it
                            },
                            onWrongNumberButtonClick = {
                                otp = ""
                                otpVisible = false
                            },
                            onVerifyOtpClick = onOtpSubmit,
                            onResendOtpClick = onSendOtp
                        )
                }
            }

            // If loading is true we show the Loading UI
            if (loading)
                AppDotTypingAnimation(modifier = Modifier.align(Alignment.Center))
        }
    }
}