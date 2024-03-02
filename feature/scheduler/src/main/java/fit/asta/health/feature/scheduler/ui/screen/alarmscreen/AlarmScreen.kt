package fit.asta.health.feature.scheduler.ui.screen.alarmscreen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.appSnackBarHostState
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.resources.strings.R as StringR

@Composable
fun AlarmScreen(uiState: AlarmUiState, event: (AlarmEvent) -> Unit) {

    val snackBarHostState = appSnackBarHostState()
    val context: Context = LocalContext.current
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        snackBarHostState = snackBarHostState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AppNetworkImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImageUrl(url = uiState.image))
                    .crossfade(true)
                    .build(),
                alpha = 0.8f,
                contentDescription = stringResource(StringR.string.description),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTexts.Level2(text = uiState.alarmTime, color = Color.White)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level6),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(space = AppTheme.spacing.level4)) {
                        AppFilledButton(textToShow = stringResource(id = StringR.string.snooze)) {
                            event(
                                AlarmEvent.onSwipedLeft(
                                    context
                                )
                            )
                        }
                        AppFilledButton(textToShow = stringResource(StringR.string.stop)) {
                            event(
                                AlarmEvent.onSwipedRight(
                                    context
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}