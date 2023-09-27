package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.navigation.tools.data.model.domain.ToolsHomeRes

@Composable
fun ToolsHmScreenTopBanner(
    bannerDataPages: ToolsHomeRes.ToolsHome.Banner,
) {
    Box(
        modifier = Modifier.fillMaxSize()

    ) {

        for (i in remember { listOf(bannerDataPages) }) {
            val imgUrl = getImgUrl(url = i.url)
            AppDefServerImg(
                model = imgUrl,
                contentDescription = "ToolsHm Banner",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 54.dp, vertical = 82.dp)
        ) {
            AppTexts.BodyMedium(text = bannerDataPages.desc, textAlign = TextAlign.Center)
        }
    }
}