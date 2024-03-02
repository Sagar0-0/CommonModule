package fit.asta.health.sunlight.feature.screens.session_result_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.AppLoadingScreen
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.sunlight.feature.screens.home.homeScreen.SunlightHomeState
import fit.asta.health.sunlight.feature.screens.session_result_screen.components.SessionResultRow
import fit.asta.health.sunlight.feature.utils.toAmPmTime
import fit.asta.health.sunlight.feature.utils.toMinutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionResultScreen(
    state: State<SunlightHomeState>,
    sessionState: State<SunlightHomeState>,
    navigateBack: () -> Unit
) {
    val spacing = AppTheme.spacing
    val scrollState = rememberScrollState()
    AppScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.surfaceVariant),
        topBar = {
            AppTopBar(
                title = "Session Result",
                onBack = {},
                containerColor = AppTheme.colors.surface
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        AppTheme.colors.surface,
                        shape = RoundedCornerShape(bottomStart = 200.dp)
                    )
                    .align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        AppTheme.colors.surface,
                        shape = RoundedCornerShape(topEnd = 200.dp)
                    )
                    .align(Alignment.BottomStart)
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding(),
                        start = spacing.level2,
                        end = spacing.level2,
                        bottom = spacing.level2
                    )
                    .verticalScroll(scrollState)
            ) {
                AppElevatedCard {
/*
                    Log.d("skincondition", "SessionResultScreen: ${state.value.skinConditionData.first()}")
*/
                    Column {
                        state.value.sunlightHomeResponse?.sunLightData?.prc?.forEach { prc ->
                            SessionResultRow(
                                title = prc.ttl,
                                caption = prc.values.firstOrNull()?.ttl ?: ""
                            )
                        }
                    }
                }
                AnimatedVisibility(state.value.isLoading) {
                    AppLoadingScreen()
                }

                Spacer(modifier = Modifier.height(spacing.level3))
                AppElevatedCard {
                    Column {
                        sessionState.value.sunlightSessionData?.start?.toLongOrNull()?.toAmPmTime()
                            ?.let { it1 ->
                                SessionResultRow(
                                    title = "Start Time",
                                    caption = it1
                                )
                            }
                        SessionResultRow(
                            title = "End Time",
                            caption = sessionState.value.sunlightSessionData?.end?.toLongOrNull()
                                .toAmPmTime()
                        )
                        SessionResultRow(
                            title = "Total Time",
                            caption = "${sessionState.value.sunlightSessionData?.dur.toMinutes()} min"
                        )
                        SessionResultRow(
                            title = "Vit D consumed",
                            caption = "${sessionState.value.sunlightSessionData?.con ?: "-"} IU"
                        )
                        SessionResultRow(
                            title = "Recommended Vit D",
                            caption = "${state.value.sunlightHomeResponse?.sunLightProgressData?.rcmIu ?: "-"} IU"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing.level3))

                CaptionTexts.Level1(
                    text = "Congrats on completing your session.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

            }
            AppTextButton(
                textToShow = "OKAY",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            ) {
                navigateBack.invoke()
            }
        }
    }
}
