package fit.asta.health.subscription

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.appShimmerAnimation
import fit.asta.health.designsystem.molecular.icon.AppIcon

@Composable
@Preview
fun SubscriptionLoadingCard(isLoading: Boolean = true, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(AppTheme.boxSize.level7)
            .fillMaxWidth()
            .clip(AppTheme.shape.level1)
            .appShimmerAnimation(isLoading)
            .clickable {
                if (isLoading) {
                    onClick.invoke()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            AppIcon(imageVector = Icons.Default.Refresh)
        }
    }
}