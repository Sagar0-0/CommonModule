package fit.asta.health.sunlight.feature.screens.session_result_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun SessionResultRow(
    title: String,
    caption: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level1),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyTexts.Level1(
            text = title,
            modifier = Modifier.weight(0.5f),
            overflow = TextOverflow.Ellipsis
        )
        HeadingTexts.Level4(
            text = caption,
            modifier = Modifier.weight(0.5f),
            overflow = TextOverflow.Ellipsis
        )
    }
}