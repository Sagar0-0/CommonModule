package fit.asta.health.subscription.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.subscription.remote.model.Offer
import fit.asta.health.subscription.remote.model.OffersBannerContentType


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
                OfferBanner(
                    Offer()
                ) {

                }
            }
        }
    }
}
@Composable
fun OfferBanner(offer: Offer, onClick: (offer: Offer) -> Unit) {
    // Container box for the banner content
    AppCard(
        modifier = Modifier
            .clip(AppTheme.shape.level1)
            .fillMaxSize(),
        onClick = {
            onClick(offer)
        }
    ) {
        // Displaying content based on content type
        when (offer.type) {
            OffersBannerContentType.Image.type -> {
                // Image content
                AppNetworkImage(
                    model = getImgUrl(offer.url),
                    contentDescription = "Offers Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            OffersBannerContentType.GIF.type -> {
                // GIF content
                AppGifImage(
                    modifier = Modifier.fillMaxSize(),
                    url = getImgUrl(offer.url),
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
                        .setUri(getVideoUrl(url = offer.url).toUri()).build()
                )
            }
        }
    }
}