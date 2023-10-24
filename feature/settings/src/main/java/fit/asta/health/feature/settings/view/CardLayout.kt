package fit.asta.health.feature.settings.view

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
internal fun CardLayout(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    AppElevatedCard(modifier = Modifier.padding(horizontal = AppTheme.spacing.level2)) {
        TitleTexts.Level2(
            text = title,
            modifier = Modifier.padding(
                start = AppTheme.spacing.level2,
                top = AppTheme.spacing.level2
            )
        )
        content()
    }
}