package fit.asta.health.feature.auth.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.auth.components.AuthNumberInputUI
import fit.asta.health.feature.auth.components.AuthOtpVerificationUI
import fit.asta.health.feature.auth.util.OtpVerifier

@Composable
fun AuthPhoneSignInScreen(
    loginState: UiState<Unit>,
    onUiEvent: (AuthUiEvent) -> Unit
) {


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
        true -> "Enter the Otp Sent To phone Number ${countryCode}-$phoneNumber"
        false -> "Enter your mobile number to get the OTP"
    }

    // Used to show the Loading state of the Whole Flow
    var loading by remember { mutableStateOf(false) }

    // This is the Verification ID which would be matched for OTP
    var verificationID by remember { mutableStateOf("") }

    val context = LocalContext.current

    // This is the function for sending the OTP
    val onSendOtp = {
        if (phoneNumber.length != 10 || countryCode.length < 2)
            Toast.makeText(context, "Please enter a valid OTP", Toast.LENGTH_SHORT).show()
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
    LaunchedEffect(loginState) {
        if (loginState is UiState.ErrorMessage) {
            onUiEvent(AuthUiEvent.OnLoginFailed)
            loading = false
            otp = ""
            Toast.makeText(
                context,
                "Please check if this number already linked with another account.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (loading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }

    AppScreen {

        // Contains Both the heading text, Number Input and the Verification Code input Fields
        Column(
            modifier = Modifier
                .padding(
                    horizontal = AppTheme.spacing.level2,
                    vertical = AppTheme.spacing.level5
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // heading Text asking user to give his inputs
            HeadingTexts.Level1(text = textToShow)

            // This Contains the Phone Number Input UI
            Box {
                this@Column.AnimatedVisibility(
                    visible = !otpVisible,
                    enter = fadeIn() + slideInHorizontally(
                        initialOffsetX = { it * 2 }
                    ),
                    exit = fadeOut() + slideOutHorizontally(
                        targetOffsetX = { it * 2 }
                    )
                ) {
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
                }

                // This contains the OTP Verification code Inputs
                this@Column.AnimatedVisibility(
                    visible = otpVisible,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    AuthOtpVerificationUI(
                        otp = otp,
                        onOtpTextChange = {
                            otp = it
                        },
                        onWrongNumberButtonClick = {
                            otp = ""
                            otpVisible = false
                        },
                        onVerifyOtpClick = onOtpSubmit
                    )
                }
            }
        }
    }
}