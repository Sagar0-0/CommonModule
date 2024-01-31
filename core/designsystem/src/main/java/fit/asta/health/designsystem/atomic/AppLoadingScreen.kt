package fit.asta.health.designsystem.atomic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation

@Composable
fun AppLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background.copy(0.6f))
            .pointerInput(Unit) {},
        contentAlignment = Alignment.Center
    ) {
        AppDotTypingAnimation()
    }
}