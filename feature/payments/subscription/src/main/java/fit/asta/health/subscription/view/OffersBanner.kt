package fit.asta.health.subscription.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager


/**
 * Composable function to display a pager of offer banners.
 */
@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OffersBannerPreview() {

    val pagerState = rememberPagerState { 3 }

    // Wrapping the banner in an AppTheme
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 54.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // Horizontal pager to display offer banners
            AppHorizontalPager(
                pagerState = pagerState,
                modifier = Modifier
                    .fillMaxHeight(0.6f),
                enableAutoAnimation = false,
                userScrollEnabled = true
            ) { _ ->
                OffersBanner(
                    listOf(OffersUiData())
                ) { _, _ ->

                }
            }
        }
    }
}

enum class OffersBannerContentType(val type: Int) {
    Image(1),
    GIF(2),
    Video(3),
    AUDIO(4),
    DOCUMENT(5),
}

data class OffersUiData(
    val categoryId: String = "",
    val productId: String = "",
    val type: Int = OffersBannerContentType.GIF.type,
    val url: String = ""
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OffersBanner(
    offersList: List<OffersUiData>,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    onClick: (categoryId: String, productId: String) -> Unit
) {
    val pagerState = rememberPagerState { offersList.size }
    Box {
        AppHorizontalPager(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(ratio = AppTheme.aspectRatio.fullScreen),
            pagerState = pagerState,
            contentPadding = PaddingValues(AppTheme.spacing.noSpacing),
            pageSpacing = AppTheme.spacing.noSpacing,
            enableAutoAnimation = true,
        ) { page ->
            // Container box for the banner content
            AppCard(
                modifier = contentModifier
                    .fillMaxWidth(),
                onClick = {
                    onClick(offersList[page].categoryId, offersList[page].productId)
                },
                shape = AppTheme.shape.rectangle
            ) {
                // Displaying content based on content type
                when (offersList[page].type) {
                    OffersBannerContentType.Image.type -> {
                        // Image content
                        AppNetworkImage(
                            model = getImageUrl(offersList[page].url),
                            contentDescription = "Offers Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    OffersBannerContentType.GIF.type -> {
                        // GIF content
                        AppGifImage(
                            modifier = Modifier.fillMaxSize(),
                            url = getImageUrl(offersList[page].url),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    OffersBannerContentType.Video.type -> {
                        VideoViewUI(
                            modifier = Modifier.fillMaxSize(),
                            onPlay = true,
                            onPause = false,
                            mediaItem = MediaItem
                                .Builder()
                                .setUri(getVideoUrl(offersList[page].url).toUri()).build()
                        )
                    }

                    else -> {}
                }
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
}