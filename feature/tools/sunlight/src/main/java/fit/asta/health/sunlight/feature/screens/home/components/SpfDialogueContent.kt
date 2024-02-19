package fit.asta.health.sunlight.feature.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.resources.drawables.R as DrawR


@Composable
fun SpfDialogueContent(onContinue: () -> Unit, onUpdate: () -> Unit) {
    AppCard {
            Column(Modifier.padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                HeadingTexts.Level3(text = "You are prone to get damage by UV rays please apply sunscreen with higher SPF levels",
                    textAlign = TextAlign.Center)
                CaptionTexts.Level5(text = "Make good choice live healthy",
                    textAlign = TextAlign.Center)
                Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)) {
                    ButtonWithColor(
                        modifier = Modifier.weight(0.5f),
                        color = AppTheme.colors.secondary,
                        text = "CONTINUE",
                        onClick = onContinue
                    )
                    ButtonWithColor(
                        modifier = Modifier.weight(0.5f),
                        color = AppTheme.colors.primary,
                        text = "UPDATE",
                        onClick = onUpdate
                    )
                }
            }
    }
}