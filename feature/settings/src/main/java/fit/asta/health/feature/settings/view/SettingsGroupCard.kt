package fit.asta.health.feature.settings.view

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
internal fun SettingsGroupCard(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = AppTheme.colors.onSurface,
    content: @Composable ColumnScope.() -> Unit
) {
    AppElevatedCard(modifier = modifier) {
        TitleTexts.Level2(
            color = titleColor,
            text = title,
            modifier = Modifier.padding(
                start = AppTheme.spacing.level2,
                top = AppTheme.spacing.level2
            )
        )
        content()
    }
}