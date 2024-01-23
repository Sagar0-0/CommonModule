package fit.asta.health.feature.auth.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.feature.auth.AUTH_OTP_VERIFICATION_ROUTE
import fit.asta.health.feature.auth.components.AuthOnboardingControl
import fit.asta.health.feature.auth.components.AuthStringDivider
import fit.asta.health.feature.auth.components.AuthTermAndPrivacyUI
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.resources.strings.R

/**
 * This function is the Screen for the Login or Sign up screen in the App. This shows the various types
 * of Login for the App.
 *
 * @param onboardingState This is the on Boarding API call state
 * @param onNavigate This is the function which helps to Navigate between different screens
 * @param onUiEvent This function is used to send UI Events to the ViewModel
 */
@Composable
fun AuthScreenControl(
    loginState: UiState<Unit>,
    onboardingState: UiState<List<OnboardingData>>,
    onNavigate: (String) -> Unit,
    onUiEvent: (AuthUiEvent) -> Unit
) {

    AppScaffold { padding ->
        // Parent Composable
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(AppTheme.spacing.level2)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // On Boarding UI
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = AppTheme.spacing.level2)
                ) {

                    // This handles and shows UI of On Boarding Data Pager
                    AuthOnboardingControl(
                        onboardingState = onboardingState,
                        onUiEvent = onUiEvent
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Login or sign up divider
                    AuthStringDivider(textToShow = "Login or Sign up")

                    // Sign in with Phone Button
                    AppOutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppTheme.buttonSize.level6),
                        onClick = {
                            onNavigate(AUTH_OTP_VERIFICATION_ROUTE)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            modifier = Modifier.padding(end = AppTheme.spacing.level1),
                        )
                        // Button Text
                        CaptionTexts.Level1(
                            text = "Sign in with Phone",
                            color = AppTheme.colors.onSurface
                        )
                    }

                    // Google Sign In Button
                    GoogleSignIn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppTheme.buttonSize.level6),
                        textId = R.string.sign_in_with_google
                    ) {
                        onUiEvent(AuthUiEvent.SignInWithCredentials(it))
                    }

                    // Terms and Policy composable function
                    AuthTermAndPrivacyUI(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { url ->
                            onUiEvent(AuthUiEvent.NavigateToWebView(url = url))
                        }
                    )
                }
            }
            // This function shows and handles the Login State UI Flow
            HandleLoginState(loginState = loginState, onUiEvent = onUiEvent)
        }
    }
}

@Composable
private fun BoxScope.HandleLoginState(
    loginState: UiState<Unit>,
    onUiEvent: (AuthUiEvent) -> Unit
) {
    val context = LocalContext.current

    when (loginState) {

        is UiState.ErrorRetry -> {

            // TODO :- Properly Handle the Error
            AppErrorScreen(
                modifier = Modifier.fillMaxSize(),
                text = loginState.resId.toStringFromResId()
            ) { onUiEvent(AuthUiEvent.OnLoginFailed) }
        }

        UiState.Loading -> {
            AppDotTypingAnimation(modifier = Modifier.align(Alignment.Center))
        }

        is UiState.ErrorMessage -> {

            // TODO :- Properly Handle the Error
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
}