package fit.asta.health.designsystem.molecular

import androidx.compose.runtime.Composable
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun AppRetryCard(
    text: String,
    onRetry: () -> Unit,
) {
    AppCard(onClick = onRetry) {
        TitleTexts.Level2(text = text)
    }
}