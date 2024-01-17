package fit.asta.health.subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.appShimmerAnimation
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
@Preview
fun SubscriptionLoadingCard(isLoading: Boolean = true, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Display a title for the subscription list
        HeadingTexts.Level1(text = "Explore Premium Plans")
        (1..3).forEach { _ ->
            Box(
                modifier = Modifier
                    .height(AppTheme.boxSize.level8)
                    .fillMaxWidth()
                    .clip(AppTheme.shape.level1)
                    .appShimmerAnimation(isLoading)
                    .clickable {
                        if (!isLoading) {
                            onClick.invoke()
                        }
                    }
                    .background(if (!isLoading) AppTheme.colors.surfaceVariant else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                if (!isLoading) {
                    AppIcon(imageVector = Icons.Default.Refresh)
                }
            }
        }
    }
}