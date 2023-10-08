package fit.asta.health.designsystem.components.functional

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton

@Composable
fun BottomSheetButton(
    title: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
    onClick: (() -> Unit)? = null,
) {
    onClick?.let {
        AppFilledButton(
            modifier = modifier.fillMaxWidth(),
            textToShow = title,
            shape = AppTheme.shape.level1,
            onClick = onClick,
            colors = colors
        )
    }
}