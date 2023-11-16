package fit.asta.health.offers.view

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.drawables.R


@OptIn(ExperimentalFoundationApi::class)
@Preview(
    "Light Referral", heightDp = 1100
)
@Preview(
    name = "Dark Referral",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1100
)
@Composable
fun OffersBanner() {

    val banners = listOf(
        R.drawable.weatherimage,
        R.drawable.weatherimage,
        R.drawable.weatherimage,
        R.drawable.weatherimage,
        R.drawable.weatherimage,
        R.drawable.weatherimage,
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AppHorizontalPager(
            pagerState = rememberPagerState { banners.size },
            modifier = Modifier
                .aspectRatio(ratio = AppTheme.aspectRatio.fullScreen)
                .fillMaxWidth()
        ) { page ->
            OfferBannerContent(bannerDataPages = banners[page])
        }
    }
}


@Composable
fun OfferBannerContent(bannerDataPages: Int) {
    Box(
        modifier = Modifier
            .clip(AppTheme.shape.level1)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Image from the Server
        for (i in remember { listOf(bannerDataPages) }) {
            AppLocalImage(
                painter = painterResource(id = bannerDataPages),
                contentDescription = "ToolsHm Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        // Text in the center of the Image
        BodyTexts.Level1(text = "Demo Description")
    }
}