package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import fit.asta.health.R
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerLayout(
    bannerDataPages: ToolsHomeRes.ToolsHome.Banner,
    pagerState: PagerState,
) {
    Box(modifier = Modifier.fillMaxSize()

    ) {

        val domainName = stringResource(id = R.string.media_url)

        for (i in remember { listOf(bannerDataPages) }) {
            val imgUrl = "$domainName${i.url}"
            AsyncImage(model = imgUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
        }

        Box(modifier = Modifier
            .align(Alignment.Center)
            .padding(horizontal = 54.dp, vertical = 82.dp)) {
            val interLightFontFamily = FontFamily(Font(R.font.inter_light, FontWeight.Light))
            Text(text = bannerDataPages.desc,
                fontSize = 20.sp,
                fontFamily = interLightFontFamily,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center)
        }
        //Horizontal dot indicator
        HorizontalPagerIndicator(pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp))
    }
}