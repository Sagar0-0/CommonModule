package fit.asta.health.subscription

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.appShimmerAnimation
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon

@Composable
fun SubscriptionLoadingCard(isLoading: Boolean, onClick: () -> Unit = {}) {
    AppCard(
        modifier = Modifier
            .height(AppTheme.boxSize.level2)
            .fillMaxWidth()
            .clip(AppTheme.shape.level1)
            .appShimmerAnimation(isLoading),
        onClick = {
            if (isLoading) {
                onClick.invoke()
            }
        }
    ) {
        if (isLoading) {
            AppIcon(imageVector = Icons.Default.Refresh)
        }
    }
}