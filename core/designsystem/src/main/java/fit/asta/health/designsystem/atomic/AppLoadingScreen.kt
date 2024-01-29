package fit.asta.health.designsystem.atomic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.cards.AppCard

@Composable
fun AppLoadingScreen() {
    AppCard(
        modifier = Modifier
            .fillMaxSize(),
        shape = AppTheme.shape.rectangle,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.background.copy(0.6f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AppDotTypingAnimation()
        }
    }
}