package fit.asta.health.feature.walking.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.molecular.texts.TitleTexts

/**
 * Displays a title and content, for use in conveying session details.
 */
fun LazyListScope.sessionDetailsItem(
    @StringRes labelId: Int,
    content: @Composable () -> Unit
) {
    item {
        TitleTexts.Level2(
            text = stringResource(id = labelId)
        )
        content()
    }
}
