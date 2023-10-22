package fit.asta.health.feature.auth.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun AuthStringDivider(
    modifier: Modifier = Modifier,
    textToShow: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppDivider(
            modifier = Modifier.weight(1f),
            color = AppTheme.colors.onSurface,
            thickness = .5.dp
        )

        BodyTexts.Level1(
            modifier = Modifier.padding(horizontal = AppTheme.spacing.level1),
            text = textToShow
        )

        AppDivider(
            modifier = Modifier.weight(1f),
            color = AppTheme.colors.onSurface,
            thickness = .5.dp
        )
    }
}