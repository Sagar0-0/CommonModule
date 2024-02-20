package fit.asta.health.feature.water.view.screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fit.asta.health.designsystem.AppTheme

sealed class BottomSheetScreen() {
    class Screen1(var sliderValue: Float, val bevName: String) : BottomSheetScreen()
    class Screen2(var sliderValue: Float, val bevName: String) : BottomSheetScreen()
    class Screen3() : BottomSheetScreen()
}

@Composable
fun AppBottomSheetWithCloseDialog(
    onClosePressed: () -> Unit,
    modifier: Modifier = Modifier,
    closeButtonColor: Color = Color.Gray,
    content: @Composable() () -> Unit
) {
    Box(modifier.fillMaxWidth()) {
        content()

        IconButton(
            onClick = onClosePressed,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(AppTheme.spacing.level2)
                .size(AppTheme.spacing.level3)

        ) {
            Icon(Icons.Filled.Close, tint = closeButtonColor, contentDescription = null)
        }

    }
}