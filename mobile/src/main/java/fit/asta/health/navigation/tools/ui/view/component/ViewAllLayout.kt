package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun ViewAllLayout(
    title: String,
    clickString: String = "",
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeadingTexts.Level1(text = title)
        onClick?.let { onClick ->
            AppTextButton(
                textToShow = clickString,
                trailingIcon = Icons.Filled.KeyboardArrowRight
            ) { onClick() }
        }
    }
}