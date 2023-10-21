package fit.asta.health.feature.auth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.animations.ShimmerAnimation
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.OnboardingDataPager
import fit.asta.health.feature.auth.util.PhoneSignIn
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@Composable
internal fun AuthScreen(
    loginState: UiState<Unit>,
    onboardingState: UiState<List<OnboardingData>>,
    onUiEvent: (AuthUiEvent) -> Unit
) {
    val context = LocalContext.current
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
                ShimmerAnimation(cardHeight = AppTheme.cardHeight.level3, cardWidth = AppTheme.cardHeight.level2)
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
                ShimmerAnimation(cardHeight = AppTheme.cardHeight.level3, cardWidth = AppTheme.cardHeight.level2)
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


        val annotatedLinkString: AnnotatedString = buildAnnotatedString {

            val str = stringResource(id = StringR.string.tnc_text)
            val startTIndex = str.indexOf("Terms")
            val endTIndex = startTIndex + 16
            val startPIndex = str.indexOf("Privacy")
            val endPIndex = startPIndex + 14
            append(str)
            addStyle(
                style = SpanStyle(
                    color = AppTheme.colors.onBackground
                ), start = 0, end = startTIndex - 1
            )
            addStyle(
                style = SpanStyle(
                    color = AppTheme.colors.onBackground
                ), start = endTIndex + 1, end = startPIndex - 1
            )
            addStyle(
                style = SpanStyle(
                    color = AppTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                ), start = startTIndex, end = endTIndex
            )
            addStyle(
                style = SpanStyle(
                    color = AppTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                ), start = startPIndex, end = endPIndex
            )

            addStringAnnotation(
                tag = "terms",
                annotation = getImgUrl(context.getString(StringR.string.url_terms_of_use)),
                start = startTIndex,
                end = endTIndex
            )

            addStringAnnotation(
                tag = "privacy",
                annotation = getImgUrl(context.getString(StringR.string.url_privacy_policy)),
                start = startPIndex,
                end = endPIndex
            )

        }

        ClickableText(
            modifier = Modifier
                .padding(vertical = AppTheme.spacing.level2)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = annotatedLinkString,
            onClick = {
                annotatedLinkString
                    .getStringAnnotations("terms", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        val url = URLEncoder.encode(
                            stringAnnotation.item,
                            StandardCharsets.UTF_8.toString()
                        )
                        onUiEvent(AuthUiEvent.NavigateToWebView(url))
                    }
                annotatedLinkString
                    .getStringAnnotations("privacy", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        val url = URLEncoder.encode(
                            stringAnnotation.item,
                            StandardCharsets.UTF_8.toString()
                        )
                        onUiEvent(AuthUiEvent.NavigateToWebView(url))
                    }
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
