package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppTexts

@Composable
fun ViewAllLayout(
    title: String = "",
    clickString: String = "",
    onClick: (() -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTexts.TitleLarge(text = title)
        onClick?.let { onClick ->
            AppButtons.AppTextButton(onClick = onClick) {
                AppTexts.LabelLarge(text = clickString, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}