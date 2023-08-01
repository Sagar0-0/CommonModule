package fit.asta.health.navigation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.common.ui.components.generic.AppBanner
import fit.asta.health.common.ui.components.generic.AppVerticalGrid
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.main.Graph
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import fit.asta.health.navigation.home.view.component.RateUsCard
import fit.asta.health.navigation.home.view.component.ReferAndEarn
import fit.asta.health.navigation.home.view.component.ToolsCardLayout
import fit.asta.health.navigation.home.view.component.ToolsHmScreenTopBanner
import fit.asta.health.navigation.home.view.component.TstBannerCard
import fit.asta.health.navigation.home.view.component.TstSliderExt
import fit.asta.health.navigation.home.view.component.ViewAllLayout
import fit.asta.health.tools.sleep.SleepToolActivity
import fit.asta.health.tools.walking.view.WalkingActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Locale

@ExperimentalCoroutinesApi
@Composable
fun HomeScreenLayout(
    toolsHome: ToolsHomeRes.ToolsHome, userId: String,
    onNav: (Graph) -> Unit,
) {

    val context = LocalContext.current

    val columns = 3

    AppVerticalGrid(
        count = columns,
        content = {

            item(span = { GridItemSpan(columns) }) {
                AppBanner(bannerList = toolsHome.banners, content = { page ->
                    ToolsHmScreenTopBanner(
                        bannerDataPages = toolsHome.banners[page]
                    )
                }, modifier = Modifier.aspectRatio(ratio = aspectRatio.small))
            }

            item(span = { GridItemSpan(columns) }) {
                ViewAllLayout(myTools = "My Tools", "All Tools") {}
            }

            items(toolsHome.tools) { tool ->
                ToolsCardLayout(
                    cardTitle = tool.title,
                    type = tool.name,
                    imgUrl = tool.url,
                    onClick = { type ->
                        when (type.lowercase(Locale.getDefault())) {
                            "water" -> {
                                onNav(Graph.WaterTool)
                            }

                            "steps" -> {
                                WalkingActivity.launch(context = context)
                            }

                            "workout" -> {
                                onNav(Graph.Workout)
                            }

                            "yoga" -> {
                                onNav(Graph.Yoga)
                            }

                            "hiit" -> {
                                onNav(Graph.Hiit)
                            }

                            "dance" -> {
                                onNav(Graph.Dance)
                            }

                            "meditation" -> {
                                onNav(Graph.MeditationTool)
                            }

                            "sleep" -> {
                                onNav(Graph.SleepTool)
                                SleepToolActivity.launch(
                                    context = context, userId = userId
                                )
                            }

                            "breathing" -> {
                                onNav(Graph.BreathingTool)
                            }

                            "sunlight" -> {
                                onNav(Graph.SunlightTool)
                            }
                        }
                    })
            }

            item {
                Spacer(modifier = Modifier.height(spacing.medium))
            }

            item(span = { GridItemSpan(columns) }) {
                ViewAllLayout(myTools = "Testimonials", allTools = "View All", onClick = {
                    onNav(Graph.Testimonials)
                })
            }

            item(span = { GridItemSpan(columns) }) {
                TstSliderExt()
            }

            item {
                Spacer(modifier = Modifier.height(spacing.small))
            }

            item(span = { GridItemSpan(columns) }) {
                AppBanner(bannerList = toolsHome.testimonials, content = { page ->
                    TstBannerCard(testimonialsData = toolsHome.testimonials[page])
                })
            }

            item {
                Spacer(modifier = Modifier.height(spacing.medium))
            }

            item(span = { GridItemSpan(columns) }) {
                RateUsCard()
            }

            item {
                Spacer(modifier = Modifier.height(spacing.medium))
            }

            item(span = { GridItemSpan(columns) }) {
                ReferAndEarn()
            }

            item {
                Spacer(modifier = Modifier.height(spacing.medium))
            }

        },
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalArrangement = Arrangement.spacedBy(spacing.small),
    )

}

