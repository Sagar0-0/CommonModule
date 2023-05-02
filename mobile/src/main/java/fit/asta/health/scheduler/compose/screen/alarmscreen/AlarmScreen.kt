package fit.asta.health.scheduler.compose.screen.alarmscreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope

@Composable
fun AlarmScreen( uiState:AlarmUiState,event:(AlarmEvent)->Unit) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context: Context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = Color.LightGray, scaffoldState = scaffoldState, content = {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = uiState.alarmTime, style = MaterialTheme.typography.h2)
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
    )
}