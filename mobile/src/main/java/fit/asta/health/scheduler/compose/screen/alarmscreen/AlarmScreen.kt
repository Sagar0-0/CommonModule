package fit.asta.health.scheduler.compose.screen.alarmscreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImageUrl
import kotlinx.coroutines.CoroutineScope

@Composable
fun AlarmScreen(uiState: AlarmUiState, event: (AlarmEvent) -> Unit) {

    val snackBarHostState = remember { SnackbarHostState() }
    val context: Context = LocalContext.current
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        snackBarHostState = snackBarHostState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImageUrl(url = uiState.image))
                    .crossfade(true)
                    .build(),
                alpha = 0.5f,
                placeholder = painterResource(R.drawable.placeholder_tag),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Column(
                    modifier = Modifier.padding(it).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(spacing.extraLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = uiState.alarmTime, style = MaterialTheme.typography.h2)
                    Row(horizontalArrangement = Arrangement.spacedBy(space = spacing.large)) {
                        Button(onClick = { event(AlarmEvent.onSwipedLeft(context)) }) {
                            Text(text = "Snooze", Modifier.background(Color.Green))
                        }
                        Button(onClick = { event(AlarmEvent.onSwipedRight(context)) }) {
                            Text(text = "stop", Modifier.background(Color.Green))
                        }
                    }
                }
            }
        }
    }
}