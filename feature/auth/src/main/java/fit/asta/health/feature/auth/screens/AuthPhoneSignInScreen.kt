package fit.asta.health.feature.auth.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.auth.components.AuthNumberInputUI
import fit.asta.health.feature.auth.components.AuthOtpVerificationUI

@Composable
fun AuthPhoneSignInScreen() {

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
                        }
                    ) {
                        // TODO :- Send OTP Function comes here
                        otpVisible = true
                    }
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
                        }
                    )
                }
            }
        }
    }
}