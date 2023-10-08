package fit.asta.health.designsystem.components.functional

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton

@Composable
fun TwoButtonLayout(
    onClickButton1: () -> Unit,
    modifier: Modifier = Modifier,
    contentButton1: @Composable RowScope.() -> Unit,
    onClickButton2: () -> Unit,
    contentButton2: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        AppOutlinedButton(
            onClick = onClickButton1,
            modifier = Modifier.weight(1f),
            content = contentButton1,
            border = BorderStroke(width = 2.dp, color = AppTheme.colors.onSurface)
        )
        AppFilledButton(
            onClick = onClickButton2,
            modifier = Modifier.weight(1f),
            content = contentButton2,
            shape = CircleShape
        )
    }
}