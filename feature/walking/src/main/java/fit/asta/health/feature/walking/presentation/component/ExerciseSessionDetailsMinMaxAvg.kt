package fit.asta.health.feature.walking.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.molecular.texts.BodyTexts

/**
 * Shows the statistical min, max and average values, as can be returned from Health Platform.
 */
@Composable
fun ExerciseSessionDetailsMinMaxAvg(
    minimum: String?,
    maximum: String?,
    average: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        BodyTexts.Level2(
            modifier = Modifier
                .weight(1f),
            text = minimum ?: "N/A",
            textAlign = TextAlign.Center
        )
        BodyTexts.Level2(
            modifier = Modifier
                .weight(1f),
            text = maximum ?: "N/A",
            textAlign = TextAlign.Center
        )
        BodyTexts.Level2(
            modifier = Modifier
                .weight(1f),
            text = average ?: "N/A",
            textAlign = TextAlign.Center
        )
    }
}

