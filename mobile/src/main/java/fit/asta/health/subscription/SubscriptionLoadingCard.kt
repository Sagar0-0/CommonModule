package fit.asta.health.subscription

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
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
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
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
        val pagerState = rememberPagerState { 3 }
        AppHorizontalPager(
            pagerState = pagerState,
            userScrollEnabled = true
        ) { _ ->
            Box(
                modifier = Modifier
                    .height(AppTheme.boxSize.level8)
                    .padding(AppTheme.spacing.level2)
                    .fillMaxWidth()
                    .clip(AppTheme.shape.level1)
                    .appShimmerAnimation(isLoading)
                    .clickable {
                        if (!isLoading) {
                            onClick.invoke()
                        }
                    }
                    .background(if (!isLoading) AppTheme.colors.onSurfaceVariant else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                if (!isLoading) {
                    AppIcon(imageVector = Icons.Default.Refresh)
                }
                // This function draws the Dot Indicator for the Pager
                AppExpandingDotIndicator(
                    modifier = Modifier
                        .padding(bottom = AppTheme.spacing.level0)
                        .align(Alignment.BottomCenter),
                    pagerState = pagerState
                )
            }
        }
    }
}