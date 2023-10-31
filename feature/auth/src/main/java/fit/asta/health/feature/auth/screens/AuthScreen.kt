package fit.asta.health.feature.auth.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.feature.auth.AUTH_OTP_VERIFICATION_ROUTE
import fit.asta.health.feature.auth.components.AuthStringDivider
import fit.asta.health.feature.auth.components.AuthTermAndPrivacyUI
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.OnboardingDataPager
import fit.asta.health.resources.strings.R as StringR

@Composable
internal fun AuthScreen(
    loginState: UiState<Unit>,
    onboardingState: UiState<List<OnboardingData>>,
    onNavigate: (String) -> Unit,
    onUiEvent: (AuthUiEvent) -> Unit
) {
    val context = LocalContext.current

    //Parent Composable
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (loginState) {
            is UiState.ErrorRetry -> {
                AppRetryCard(text = loginState.resId.toStringFromResId()) {
                    onUiEvent(AuthUiEvent.OnLoginFailed)
                }
            }

            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppDotTypingAnimation()
                }
            }

            is UiState.ErrorMessage -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        loginState.resId.toStringFromResId(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> {}
        }

        // Boarding Image State
        when (onboardingState) {
            is UiState.Loading -> {
                AppDotTypingAnimation()
            }

            is UiState.Success -> {
                OnboardingDataPager(onboardingState.data)
            }

            is UiState.NoInternet -> {
                AppInternetErrorDialog {
                    onUiEvent(AuthUiEvent.GetOnboardingData)
                }
            }

            is UiState.ErrorRetry -> {
                AppRetryCard(text = onboardingState.resId.toStringFromResId()) {
                    onUiEvent(AuthUiEvent.GetOnboardingData)
                }
            }

            is UiState.ErrorMessage -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        onboardingState.resId.toStringFromResId(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> {}
        }

        // Whole Login and Sign up along with the terms and policy UI
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level3),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Login or sign up divider
            AuthStringDivider(textToShow = "Login or Sign up")

            // Sign in with Phone Button
            AppFilledButton(
                textToShow = "Sign in with Phone",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.buttonSize.level6),
                leadingIcon = Icons.Default.Phone
            ) {
                onNavigate(AUTH_OTP_VERIFICATION_ROUTE)
            }

            // Google Sign In Button
            GoogleSignIn(StringR.string.sign_in_with_google) {
                onUiEvent(AuthUiEvent.SignInWithCredentials(it))
            }

            // Terms and Policy composable function
            AuthTermAndPrivacyUI(
                modifier = Modifier
                    .padding(bottom = AppTheme.spacing.level3)
                    .fillMaxWidth(),
                onTermsClick = {
                    onUiEvent(AuthUiEvent.NavigateToWebView(url = it))
                },
                onPrivacyClick = {
                    onUiEvent(AuthUiEvent.NavigateToWebView(url = it))
                }
            )
        }
    }
}