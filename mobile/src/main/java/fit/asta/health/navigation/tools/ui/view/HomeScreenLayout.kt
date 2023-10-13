package fit.asta.health.navigation.tools.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.animations.AppDividerLineWidth
import fit.asta.health.designsystem.molecular.background.AppHorizontalPager
import fit.asta.health.designsystem.molecular.scrollables.AppVerticalGrid
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.testimonials.components.TstBannerCard
import fit.asta.health.main.Graph
import fit.asta.health.navigation.tools.data.model.domain.ToolsHomeRes
import fit.asta.health.navigation.tools.ui.view.component.RateAppCard
import fit.asta.health.navigation.tools.ui.view.component.ReferAndEarn
import fit.asta.health.navigation.tools.ui.view.component.ToolsCardLayout
import fit.asta.health.navigation.tools.ui.view.component.ToolsHmScreenTopBanner
import fit.asta.health.navigation.tools.ui.view.component.ViewAllLayout
import fit.asta.health.tools.sleep.SleepToolActivity
import fit.asta.health.tools.walking.view.WalkingActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Locale

@ExperimentalCoroutinesApi
@Composable
fun HomeScreenLayout(
    toolsHome: ToolsHomeRes.ToolsHome, userId: String,
    onNav: (String) -> Unit,
) {

    val context = LocalContext.current
    val columns = 3

    AppVerticalGrid(
        count = columns,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Top Sliding Images
        item(span = { GridItemSpan(columns) }) {
            Column {
                AppHorizontalPager(
                    bannerList = toolsHome.banners,
                    modifier = Modifier.aspectRatio(ratio = AppTheme.aspectRatio.fullScreen)
                ) { page ->
                    ToolsHmScreenTopBanner(bannerDataPages = toolsHome.banners[page])
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            }
        }

        // My Tools text and View All button
        item(span = { GridItemSpan(columns) }) {
            Column {
                ViewAllLayout(title = "My Tools")
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }

        // All The Tools Composable cards
        items(toolsHome.tools) { tool ->
            Column {
                ToolsCardLayout(
                    cardTitle = tool.title,
                    type = tool.name,
                    imgUrl = tool.url
                ) { type ->
                    when (type.lowercase(Locale.getDefault())) {
                        "water" -> {
                            onNav(Graph.WaterTool.route)
                        }

                        "steps" -> {
                            WalkingActivity.launch(context = context)
                        }

                        "workout" -> {
                            onNav(Graph.ExerciseTool.route + "?activity=workout")
                        }

                        "yoga" -> {
                            onNav(Graph.ExerciseTool.route + "?activity=yoga")
                        }

                        "hiit" -> {
                            onNav(Graph.ExerciseTool.route + "?activity=HIIT")
                        }

                        "dance" -> {
                            onNav(Graph.ExerciseTool.route + "?activity=dance")
                        }

                        "meditation" -> {
                            onNav(Graph.MeditationTool.route)
                        }

                        "sleep" -> {
                            onNav(Graph.SleepTool.route)
                            SleepToolActivity.launch(
                                context = context, userId = userId
                            )
                        }

                        "breathing" -> {
                            onNav(Graph.BreathingTool.route)
                        }

                        "sunlight" -> {
                            onNav(Graph.SunlightTool.route)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }

        // Testimonials Text and View All Button
        item(span = { GridItemSpan(columns) }) {
            ViewAllLayout(
                title = "Testimonials",
                clickString = "View All"
            ) { onNav(Graph.Testimonials.route) }
        }

        // Why our Customers text
        item(span = { GridItemSpan(columns) }) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleTexts.Level1(text = "Why our customers love ASTA?")
                Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                AppDivider(modifier = Modifier.width(AppDividerLineWidth.TstDividerWidth))
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }

        // Testimonials Banners in a Horizontal Pager
        item(span = { GridItemSpan(columns) }) {
            Column {
                AppHorizontalPager(
                    bannerList = toolsHome.testimonials
                ) {
                    TstBannerCard(testimonialsData = toolsHome.testimonials[it])
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }

        // rate my app Card
        item(span = { GridItemSpan(columns) }) {
            Column {
                RateAppCard()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            }
        }

        // Refer and Earn Card
        item(span = { GridItemSpan(columns) }) {
            ReferAndEarn()
        }
    }
}