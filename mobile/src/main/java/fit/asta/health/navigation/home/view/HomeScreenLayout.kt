package fit.asta.health.navigation.home.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fit.asta.health.R
import fit.asta.health.common.ui.components.AppBanner
import fit.asta.health.common.ui.components.AppClickableCard
import fit.asta.health.common.ui.components.AppDefaultIcon
import fit.asta.health.common.ui.components.AppTexts
import fit.asta.health.common.ui.components.AppToolCardImage
import fit.asta.health.common.ui.components.AppVerticalGrid
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.main.Graph
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import fit.asta.health.navigation.home.view.component.RateUsCard
import fit.asta.health.navigation.home.view.component.ReferAndEarn
import fit.asta.health.testimonials.model.domain.Testimonial
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
                MyToolsAndViewAll(myTools = "My Tools", "") {}
            }

            items(toolsHome.tools) { tool ->
                ToolsCardLayout(cardTitle = tool.title,
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
                MyToolsAndViewAll(myTools = "Testimonials", allTools = "View All", onClick = {
                    onNav(Graph.Testimonials)
                })
            }

            item(span = { GridItemSpan(columns) }) {
                TestimonialsSliderExt()
            }

            item {
                Spacer(modifier = Modifier.height(spacing.small))
            }

            item(span = { GridItemSpan(columns) }) {
                AppBanner(bannerList = toolsHome.testimonials, content = { page ->
                    TestimonialTextCard(testimonialsDataPage = toolsHome.testimonials[page])
                    Log.d("tst", "${toolsHome.testimonials[page]}")
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    )

}

@Composable
private fun ToolsCardLayout(
    cardTitle: String,
    type: String,
    imgUrl: String,
    onClick: (type: String) -> Unit,
) {
    AppClickableCard(onClick = { onClick(type) }) {
        Column(modifier = Modifier.background(Color.Transparent)) {
            Box {
                AppToolCardImage(model = getImageUrl(url = imgUrl), contentDescription = cardTitle)
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(start = 6.dp, top = 6.dp)
                ) {
                    AppDefaultIcon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Schedule Icon",
                        tint = Color.DarkGray
                    )
                }
            }
            AppTexts.TitleLarge(
                cardTitle = cardTitle,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun MyToolsAndViewAll(
    myTools: String,
    allTools: String,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = myTools,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Box(modifier = Modifier.clickable(enabled = true, onClick = ({}))) {
            Text(text = AnnotatedString(allTools),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable { onClick() })
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun TestimonialsSliderExt() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTexts.TitleLarge(cardTitle = "Why our customers love ASTA?")

        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 4.dp,
            modifier = Modifier
                .width(71.dp)
                .clip(RoundedCornerShape(2.dp))
        )
    }
}

@Composable
private fun TestimonialTextCard(testimonialsDataPage: Testimonial) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            AppTexts.BodyLarge(
                cardTitle = "❝", color = MaterialTheme.colorScheme.primary
            )
            AppTexts.HeadlineSmall(cardTitle = testimonialsDataPage.testimonial)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                AppTexts.BodyLarge(cardTitle = "❞", color = MaterialTheme.colorScheme.primary)
            }
            ArtistCard(testimonialsDataPage)
        }
    }
}

@Composable
private fun ArtistCard(testimonialsDataPages: Testimonial) {

    val domainName = stringResource(id = R.string.media_url)

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "$domainName${testimonialsDataPages.user.url}",
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier

                .size(width = 80.dp, height = 80.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppTexts.TitleMedium(cardTitle = testimonialsDataPages.user.name)
            AppTexts.BodyMedium(cardTitle = "${testimonialsDataPages.user.role},${testimonialsDataPages.user.org}")
        }
    }

}

@Composable
private fun ToolsHmScreenTopBanner(
    bannerDataPages: ToolsHomeRes.ToolsHome.Banner,
) {
    Box(
        modifier = Modifier.fillMaxSize()

    ) {

        val domainName = stringResource(id = R.string.media_url)

        for (i in remember { listOf(bannerDataPages) }) {
            val imgUrl = "$domainName${i.url}"
            AsyncImage(
                model = imgUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(spacing.small)),
                contentScale = ContentScale.FillBounds
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 54.dp, vertical = 82.dp)
        ) {
            val interLightFontFamily = FontFamily(Font(R.font.inter_light, FontWeight.Light))
            Text(
                text = bannerDataPages.desc,
                fontSize = 20.sp,
                fontFamily = interLightFontFamily,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}
