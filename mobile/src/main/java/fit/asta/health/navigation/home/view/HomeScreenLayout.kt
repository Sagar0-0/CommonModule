package fit.asta.health.navigation.home.view

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import fit.asta.health.navigation.home.view.component.*
import fit.asta.health.testimonials.TestimonialsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenLayout(activity: Activity, toolsHome: ToolsHomeRes.ToolsHome) {

    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

        item(span = { GridItemSpan(3) }) {
            BannerAutoSlider(bannerList = toolsHome.banners)
        }


        item(span = { GridItemSpan(3) }) {
            MyToolsAndViewAll(myTools = "My Tools", allTools = "All Tools", onClick = {
                //TODO - Integrate All tools
            })
        }

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

        items(toolsHome.tools) { tools ->
            ToolsCardLayoutDemo(cardTitle = tools.title,
                type = tools.name,
                imgUrl = tools.url,
                onClick = {})
        }

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

        item(span = { GridItemSpan(3) }) {
            MyToolsAndViewAll(myTools = "Testimonials", allTools = "View All", onClick = {
                TestimonialsActivity.launch(context = context)
            })
        }

        item(span = { GridItemSpan(3) }) {
            Testimonials(testimonialsList = toolsHome.testimonials)
        }

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

        item(span = { GridItemSpan(3) }) {
            RateUsCard(activity)
        }

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

        item(span = { GridItemSpan(3) }) {
            ReferAndEarn()
        }

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

    }

}

@Composable
fun dateFormattedScreen(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)
    val currentDate = LocalDate.now()

    return dateFormatter.format(currentDate)
}