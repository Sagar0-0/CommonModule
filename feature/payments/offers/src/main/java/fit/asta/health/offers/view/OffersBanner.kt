package fit.asta.health.offers.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.image.AppGifImage
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.resources.drawables.R

/**
 * Sealed class representing different types of content for offers banners.
 *
 * @property type An integer identifier for the content type.
 */
sealed class OffersBannerContentType(val type: Int) {
    data object Image : OffersBannerContentType(0)
    data object GIF : OffersBannerContentType(1)
    data object Video : OffersBannerContentType(2)
}

/**
 * Composable function to display a pager of offer banners.
 */
@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OffersBanner() {
    // Sample data for offer banners
    val banners = listOf(
        OffersBannerData(
            imgID = R.drawable.weatherimage,
            type = OffersBannerContentType.Image.type
        ),
        OffersBannerData(
            imgID = R.drawable.weatherimage,
            type = OffersBannerContentType.Image.type
        ),
        OffersBannerData(
            imgID = R.drawable.weatherimage,
            type = OffersBannerContentType.Image.type
        ),
    )

    val pagerState = rememberPagerState { banners.size }

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
            ) { page ->
                OfferBannerContent(
                    bannerDataPages = banners[page].imgID,
                    contentType = banners[page].type,
                    pagerState = pagerState
                )
            }
        }
    }
}

/**
 * Composable function to display the content of an offer banner.
 *
 * @param bannerDataPages The resource ID of the banner image.
 * @param contentType The type of content for the banner (image, GIF, video).
 * @param pagerState The state of the pager to control navigation.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OfferBannerContent(bannerDataPages: Int, contentType: Int, pagerState: PagerState) {
    // Container box for the banner content
    Box(
        modifier = Modifier
            .clip(AppTheme.shape.level1)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Displaying content based on content type
        when (contentType) {
            OffersBannerContentType.Image.type -> {
                // Image content
                AppLocalImage(
                    painter = painterResource(id = bannerDataPages),
                    contentDescription = "ToolsHm Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            OffersBannerContentType.GIF.type -> {
                // GIF content
                AppGifImage(
                    modifier = Modifier.fillMaxSize(),
                    url = getImgUrl(url = ""),
                    contentScale = ContentScale.FillBounds
                )
            }

            OffersBannerContentType.Video.type -> {
                // Uncomment the following lines once VideoViewUI is implemented
                /*VideoViewUI(
                    modifier = Modifier.fillMaxSize(),
                    onPlay = page == pagerState.currentPage,
                    onPause = page != pagerState.currentPage,
                    mediaItem = MediaItem
                        .Builder()
                        .setUri(getVideoUrl(url = items[page].url).toUri()).build()
                )*/
            }
        }

        // Text and indicator below the content
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Button to explore the section
            AppOutlinedButton(
                textToShow = "Explore this Section",
                onClick = {},
                modifier = Modifier.padding(AppTheme.spacing.level2)
            )

            // Pager indicator
            AppExpandingDotIndicator(
                modifier = Modifier.padding(bottom = AppTheme.spacing.level2),
                pagerState = pagerState
            )
        }
    }
}

/**
 * Data class representing the data for an offer banner.
 *
 * @property imgID The resource ID of the banner image.
 * @property type The type of content for the banner (image, GIF, video).
 */
data class OffersBannerData(
    val imgID: Int,
    val type: Int,
)