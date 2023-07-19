package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.common.ui.theme.spacing


@Composable
fun CardBreathingRatio(
    name: String, color: Color,
    modifier: Modifier = Modifier,
    duration: String,
    ratio: String,
    onRatio: () -> Unit,
    onDuration: () -> Unit,
    onInfo: () -> Unit,
    onReset: () -> Unit
) {
    Card(colors = CardDefaults.outlinedCardColors(containerColor = color)) {
        Column(
            modifier = modifier.padding(16.dp),
        ) {
            Text(text = name)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = onReset) {
                        Icon(
                            painter = painterResource(id =R.drawable.baseline_restart_alt_24),
                            contentDescription = null
                        )
                    }
                    Text(text = "Reset")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    Text(
                        text = ratio, modifier = Modifier.clickable { onRatio() },
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Ratio", textAlign = TextAlign.End)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    Text(
                        text = duration,
                        modifier = Modifier.clickable { onDuration() },
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Duration", textAlign = TextAlign.End)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = onInfo) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_info_24),
                            contentDescription = null
                        )
                    }
                    Text(text = "Info")
                }

            }
        }
    }
}

@Preview
@Composable
fun ComposablePreviewDemo() {
    CardBreathingRatio(
        name = "Nadi Shodana",
        duration = "2:00", color = Color.Green,
        ratio = "1:1",
        onDuration = {},
        onInfo = {},
        onRatio = {},
        onReset = {}
    )
}
