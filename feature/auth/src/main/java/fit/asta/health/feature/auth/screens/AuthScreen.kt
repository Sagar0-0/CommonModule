package fit.asta.health.feature.auth.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import fit.asta.health.auth.model.domain.User
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.carouselTransition
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.auth.util.GoogleSignIn
import fit.asta.health.feature.auth.util.PhoneSignIn
import fit.asta.health.player.media.Media
import fit.asta.health.player.media.ResizeMode
import fit.asta.health.player.media.rememberMediaState
import fit.asta.health.player.presentation.ControllerType
import fit.asta.health.player.presentation.component.VideoState
import fit.asta.health.player.presentation.component.rememberManagedExoPlayer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@Composable
internal fun AuthScreen(
    loginState: UiState<User>,
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
                    onUiEvent(AuthUiEvent.ResetLoginState)
                }
            }

            UiState.Loading -> {
                AppCircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f))
            }

            is UiState.ErrorMessage -> {
                TitleTexts.Level2(text = loginState.resId.toStringFromResId())
            }

            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    onUiEvent(AuthUiEvent.CheckProfileAndNavigate(loginState.data))
                }
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
                AppCircularProgressIndicator(modifier = Modifier.weight(1f))
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
                onUiEvent(AuthUiEvent.ResetLoginState)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.OnboardingDataPager(items: List<OnboardingData>) {
    val pagerState = rememberPagerState(pageCount = { items.size })
    HorizontalPager(
        modifier = Modifier.weight(1f),
        state = pagerState,
        contentPadding = PaddingValues(AppTheme.spacing.level2),
        pageSpacing = AppTheme.spacing.level3,
    ) { page ->
        AppCard(
            modifier = Modifier
                .carouselTransition(page, pagerState)
                .fillMaxHeight()
                .padding(AppTheme.spacing.level2)
                .clip(AppTheme.shape.level3)
        ) {
            when (items[page].type) {
                1, 2 -> {
                    AppGifImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = AppTheme.spacing.level3),
                        url = getImgUrl(url = items[page].url),
                        contentScale = ContentScale.FillWidth
                    )
                }

                3 -> {
                    VideoView(
                        onPlay = page == pagerState.currentPage,
                        onPause = page != pagerState.currentPage,
                        mediaItem = MediaItem.Builder()
                            .setUri(getVideoUrl(url = items[page].url).toUri()).build()
                    )
                }
            }


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleTexts.Level2(
                    modifier = Modifier.padding(horizontal = AppTheme.spacing.level3),
                    text = items[page].title,
                    textAlign = TextAlign.Center
                )
                TitleTexts.Level2(
                    modifier = Modifier.padding(horizontal = AppTheme.spacing.level3),
                    text = items[page].desc,
                    textAlign = TextAlign.Center
                )
            }
        }
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

@Composable
fun VideoView(
    mediaItem: MediaItem,
    uiState: VideoState = VideoState(
        controllerType = ControllerType.None,
        resizeMode = ResizeMode.FixedWidth,
        useArtwork = true
    ),
    onPlay: Boolean,
    onPause: Boolean
) {
    val player by rememberManagedExoPlayer()

    val state = rememberMediaState(player = player)
    LaunchedEffect(player) {
        Log.d("TAG", "VideoView: ")
        player?.run {
            setMediaItem(mediaItem)
            playWhenReady = false
            repeatMode = Player.REPEAT_MODE_ONE
            prepare()
        }
    }
    LaunchedEffect(onPlay) {
        if (onPlay) {
            player?.play()
        }
    }
    LaunchedEffect(onPause) {
        if (onPause) {
            player?.pause()
        }
    }

    Box(
        modifier = Modifier.background(Color.Black), contentAlignment = Alignment.Center
    ) {
        Media(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(AppTheme.aspectRatio.wideScreen)
                .background(Color.Black),
            surfaceType = uiState.surfaceType,
            resizeMode = uiState.resizeMode,
            keepContentOnPlayerReset = uiState.keepContentOnPlayerReset,
            useArtwork = uiState.useArtwork,
            showBuffering = uiState.showBuffering,
            buffering = {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    AppCircularProgressIndicator()
                }
            },
            errorMessage = { error ->
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    BodyTexts.Level1(text = error.message ?: "", color = AppTheme.colors.error)
                }
            },
            controllerHideOnTouch = uiState.controllerHideOnTouchType,
            controllerAutoShow = uiState.controllerAutoShow,
            controller = null
        )
    }
}
