package fit.asta.health.main.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun AlarmPermDialogue(onClick: () -> Unit) {
    AppCard(modifier = Modifier.padding(AppTheme.spacing.level2)) {
        Column(
            Modifier.padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeadingTexts.Level3(
                text = "Alarm permission required!",
                textAlign = TextAlign.Center
            )
            CaptionTexts.Level5(
                text = "To make the app functionality work properly we require your permission to manage alarms on this device.",
                textAlign = TextAlign.Center
            )
            ButtonWithColor(
                modifier = Modifier.fillMaxWidth(),
                color = AppTheme.colors.primary,
                text = "AUTHORIZE",
                onClick = onClick
            )
        }

    }
}