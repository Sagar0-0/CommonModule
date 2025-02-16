package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.home.remote.model.ToolsHome

@Composable
fun ToolsHmScreenTopBanner(
    bannerDataPages: ToolsHome.Banner,
) {
    Box(
        modifier = Modifier
            .clip(AppTheme.shape.level1)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Image from the Server
        for (i in remember { listOf(bannerDataPages) }) {
            val imgUrl = getImageUrl(url = i.url)
            AppNetworkImage(
                model = imgUrl,
                contentDescription = "ToolsHm Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        // Text in the center of the Image
        BodyTexts.Level1(text = bannerDataPages.desc)
    }
}