package fit.asta.health.scheduler.compose.screen.alarmscreen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppDefServerImg
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImgUrl

@Composable
fun AlarmScreen(uiState: AlarmUiState, event: (AlarmEvent) -> Unit) {

    val snackBarHostState = remember { SnackbarHostState() }
    val context: Context = LocalContext.current
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        snackBarHostState = snackBarHostState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AppDefServerImg(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImgUrl(url = uiState.image))
                    .crossfade(true)
                    .build(),
                alpha = 0.8f,
                contentDescription = stringResource(R.string.description),
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
                    AppTexts.DisplayMedium(text = uiState.alarmTime, color = Color.White)
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
                    verticalArrangement = Arrangement.spacedBy(spacing.extraLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(space = spacing.large)) {
                        AppButtons.AppStandardButton(onClick = {
                            event(
                                AlarmEvent.onSwipedLeft(
                                    context
                                )
                            )
                        }) {
                            AppTexts.TitleMedium(text = "Snooze")
                        }
                        AppButtons.AppStandardButton(onClick = {
                            event(
                                AlarmEvent.onSwipedRight(
                                    context
                                )
                            )
                        }) {
                            AppTexts.TitleMedium(text = "stop")
                        }
                    }
                }
            }

        }
    }
}