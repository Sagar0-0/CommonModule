package fit.asta.health.feature.auth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.animations.ShimmerAnimation
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.components.TermAndPrivacyUI
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.OnboardingDataPager
import fit.asta.health.feature.auth.util.PhoneSignIn
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@Composable
internal fun AuthScreen(
    loginState: UiState<Unit>,
    onboardingState: UiState<List<OnboardingData>>,
    onUiEvent: (AuthUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        when (loginState) {
            is UiState.ErrorRetry -> {
                AppRetryCard(text = loginState.resId.toStringFromResId()) {
                    onUiEvent(AuthUiEvent.OnLoginFailed)
                }
            }

            UiState.Loading -> {
                ShimmerAnimation(
                    cardHeight = AppTheme.cardHeight.level3,
                    cardWidth = AppTheme.cardHeight.level2
                )
            }

            else -> {}
        }

        when (onboardingState) {
            is UiState.Success -> {
                OnboardingDataPager(onboardingState.data)
            }

            is UiState.NoInternet -> {
                Image(
                    painter = painterResource(id = DrawR.drawable.ic_launcher),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }

            is UiState.Loading -> {
                ShimmerAnimation(
                    cardHeight = AppTheme.cardHeight.level3,
                    cardWidth = AppTheme.cardHeight.level2
                )
            }

            else -> {
                AppRetryCard(text = "Something Went Wrong") {
                    onUiEvent(AuthUiEvent.GetOnboardingData)
                }
            }
        }


        var isGoogleVisible by rememberSaveable {
            mutableStateOf(true)
        }
        AuthDivider("Login or Sign up")
        PhoneSignIn(
            failed = loginState is UiState.ErrorMessage,
            resetFailedState = {
                onUiEvent(AuthUiEvent.OnLoginFailed)
            },
            isPhoneEntered = {
                isGoogleVisible = !it
            }
        ) {
            onUiEvent(AuthUiEvent.SignInWithCredentials(it))
        }

        AnimatedVisibility(visible = isGoogleVisible) {
            Column {
                AuthDivider("or")
                GoogleSignIn(StringR.string.sign_in_with_google) {
                    onUiEvent(AuthUiEvent.SignInWithCredentials(it))
                }
            }
        }

        TermAndPrivacyUI(
            modifier = Modifier
                .padding(vertical = AppTheme.spacing.level2)
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

@Composable
fun AuthDivider(s: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppDivider(
            Modifier
                .weight(1f)
                .padding(AppTheme.spacing.level1)
        )

        TitleTexts.Level3(text = s)

        AppDivider(
            Modifier
                .weight(1f)
                .padding(AppTheme.spacing.level1)
        )
    }
}
