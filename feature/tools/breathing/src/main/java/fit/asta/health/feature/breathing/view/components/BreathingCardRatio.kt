package fit.asta.health.feature.breathing.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    AppCard {
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
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = null
                        )
                    }
                    BodyTexts.Level2(text = "Reset")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
                ) {
                    TitleTexts.Level2(
                        text = ratio, modifier = Modifier.clickable { onRatio() }
                    )
                    BodyTexts.Level2(text = "Ratio", textAlign = TextAlign.End)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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
                            imageVector = Icons.Default.Info,
                            contentDescription = null
                        )
                    }
                    BodyTexts.Level2(text = "Info")
                }

            }
        }
    }
}
