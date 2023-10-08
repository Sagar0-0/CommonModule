package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts


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
    AppCard(colors = CardDefaults.outlinedCardColors(containerColor = color)) {
        Column(
            modifier = modifier.padding(16.dp),
        ) {
            TitleTexts.Level2(text = name)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppIconButton(onClick = onReset) {
                        AppIcon(
                            painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                            contentDescription = null
                        )
                    }
                    BodyTexts.Level2(text = "Reset")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
                ) {
                    TitleTexts.Level2(
                        text = ratio, modifier = Modifier.clickable { onRatio() }
                    )
                    BodyTexts.Level2(text = "Ratio", textAlign = TextAlign.End)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
                ) {
                    TitleTexts.Level2(
                        text = duration,
                        modifier = Modifier.clickable { onDuration() }
                    )

                    BodyTexts.Level2(text = "Duration", textAlign = TextAlign.End)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppIconButton(onClick = onInfo) {
                        AppIcon(
                            painter = painterResource(id = R.drawable.baseline_info_24),
                            contentDescription = null
                        )
                    }
                    BodyTexts.Level2(text = "Info")
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
