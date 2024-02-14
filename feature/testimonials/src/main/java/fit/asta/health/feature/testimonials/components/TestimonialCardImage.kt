package fit.asta.health.feature.testimonials.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.TitleTexts

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        AppSurface {
            TestimonialCardImage(tstImageMedia = listOf(Media(), Media(), Media(), Media()))
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestimonialCardImage(tstImageMedia: List<Media?>) {

    if (tstImageMedia.isNotEmpty()) {
        val pagerState = rememberPagerState { tstImageMedia.size }
        Box {
            AppHorizontalPager(
                pagerState = pagerState,
                contentPadding = PaddingValues(horizontal = AppTheme.spacing.level2),
                pageSpacing = AppTheme.spacing.level1,
                enableAutoAnimation = false,
                userScrollEnabled = true
            ) { page ->
                Box(
                    modifier = Modifier
                        .aspectRatio(AppTheme.aspectRatio.square)
                        .clip(AppTheme.shape.level1)
                ) {
                    AppNetworkImage(
                        model = getImageUrl(url = tstImageMedia[page]!!.url),
                        contentDescription = "Before and After Images",
                        modifier = Modifier.aspectRatio(AppTheme.aspectRatio.square)
                    )
                }
            }

            // This function draws the Dot Indicator for the Pager
            AppExpandingDotIndicator(
                modifier = Modifier
                    .padding(bottom = AppTheme.spacing.level2)
                    .align(Alignment.BottomCenter),
                pagerState = pagerState
            )
        }
    } else
        TitleTexts.Level3(text = "NO MEDIA FILE PRESENT", color = AppTheme.colors.error)
}