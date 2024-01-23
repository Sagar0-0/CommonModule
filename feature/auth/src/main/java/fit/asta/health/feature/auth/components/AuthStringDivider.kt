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
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun AuthStringDivider(
    modifier: Modifier = Modifier,
    textToShow: String = "Login or Sign Up"
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

        HeadingTexts.Level3(
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