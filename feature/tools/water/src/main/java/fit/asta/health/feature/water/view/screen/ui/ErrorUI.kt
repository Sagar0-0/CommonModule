package fit.asta.health.feature.water.view.screen.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.viewmodel.WaterToolViewModel
import fit.asta.health.resources.drawables.R

@Composable
fun ErrorUi(
    viewModel: WaterToolViewModel = hiltViewModel(),
    event: (WTEvent) -> Unit
) {
    val isLoading by remember {
        viewModel._isLoading
    }
    AppSurface {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_error_not_found),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.scale(.8f)
                )
                BodyTexts.Level2(text = "An Unknown Error Occurred", textAlign = TextAlign.Center)
                AppFilledButton(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(AppTheme.spacing.level2),
                    onClick = { event(WTEvent.RetrySection) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isLoading) {
                            Log.d("rishi", "CircularProgressCalled")
                            AppCircularProgressIndicator(
                                modifier = Modifier
                                    .scale(0.6f)
                                    .clipToBounds()
                            )
                        }
                        CaptionTexts.Level2(
                            text = "Tap to Retry",
                            textAlign = TextAlign.Center
                        )
                    }

                }

            }
        }
    }

}