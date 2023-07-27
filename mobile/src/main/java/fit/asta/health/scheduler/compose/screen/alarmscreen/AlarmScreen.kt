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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.components.AppScaffold
import fit.asta.health.common.ui.theme.spacing

@Composable
fun AlarmScreen( uiState:AlarmUiState,event:(AlarmEvent)->Unit) {

    val snackBarHostState = remember { SnackbarHostState() }
    val context: Context = LocalContext.current
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        snackBarHostState = snackBarHostState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = uiState.alarmTime, style = MaterialTheme.typography.displayMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(space = spacing.medium)) {
                Button(onClick = { event(AlarmEvent.onSwipedLeft(context)) }) {
                    Text(text = "Snooze",Modifier.background(Color.Green))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = {event(AlarmEvent.onSwipedRight(context)) }) {
                    Text(text = "stop",Modifier.background(Color.Green))
                }
            }
        }
    }
}