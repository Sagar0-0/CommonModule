package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun ViewAllLayout(
    modifier: Modifier = Modifier,
    title: String,
    clickString: String = "",
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTexts.Level2(text = title)
        onClick?.let { onClick ->
            AppTextButton(
                contentPadding = PaddingValues(
                    start = AppTheme.spacing.level1,
                    top = AppTheme.spacing.level1,
                    bottom = AppTheme.spacing.level1,
                    end = AppTheme.spacing.noSpacing
                ),
                onClick = onClick
            ) {
                BodyTexts.Level1(
                    text = clickString,
                    color = AppTheme.colors.primary
                )
                AppIcon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    tint = AppTheme.colors.primary
                )
            }
        }
    }
}