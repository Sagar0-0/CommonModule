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
import fit.asta.health.tools.breathing.BreathingActivity
import fit.asta.health.tools.exercise.ExerciseActivity
import fit.asta.health.tools.meditation.MeditationActivity
import fit.asta.health.tools.sunlight.SunlightActivity
import fit.asta.health.tools.walking.view.WalkingActivity
import fit.asta.health.testimonials.TestimonialsActivity
import fit.asta.health.tools.water.WaterToolActivity
import fit.asta.health.tools.sleep.SleepToolActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenLayout(activity: Activity, toolsHome: ToolsHomeRes.ToolsHome, userId: String) {

    val context = LocalContext.current

    val columns = 3

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item(span = { GridItemSpan(columns) }) {
            BannerAutoSlider(bannerList = toolsHome.banners)
        }

        item(span = { GridItemSpan(columns) }) {
            MyToolsAndViewAll(myTools = "My Tools", "") {}
        }

        items(toolsHome.tools) { tool ->
            ToolsCardLayout(
                cardTitle = tool.title,
                type = tool.name,
                imgUrl = tool.url,
                onClick = { type ->
                    when (type.lowercase(Locale.getDefault())) {
                        "water" -> {
                            WaterToolActivity.launch(context = context)
                        }

                        "steps" -> {
                            WalkingActivity.launch(context = context)
                        }

                        "workout" -> {
                            ExerciseActivity.launch(context = context, activity = "workout")
                        }

                        "yoga" -> {
                            ExerciseActivity.launch(context = context, activity = "yoga")
                        }

                        "hiit" -> {
                            ExerciseActivity.launch(context = context, activity = "HIIT")
                        }

                        "dance" -> {
                            ExerciseActivity.launch(context = context, activity = "dance")
                        }

                        "meditation" -> {
                            MeditationActivity.launch(context = context)
                        }

                        "sleep" -> {
                            SleepToolActivity.launch(
                                context = context,
                                userId = userId
                            )
                        }

                        "breathing"->{
                            BreathingActivity.launch(context=context)
                        }

                        "sunlight" -> {
                            SunlightActivity.launch(context = context)
                        }

                    }
                })
        }

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

        item(span = { GridItemSpan(columns) }) {
            MyToolsAndViewAll(myTools = "Testimonials", allTools = "View All", onClick = {
                TestimonialsActivity.launch(context = context)
            })
        }

        item(span = { GridItemSpan(columns) }) {
            Testimonials(testimonialsList = toolsHome.testimonials)
        }

        item {
            Spacer(modifier = Modifier.height(spacing.medium))
        }

        item(span = { GridItemSpan(columns) }) {
            RateUsCard(activity)
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
    }

}

@Composable
fun dateFormattedScreen(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)
    val currentDate = LocalDate.now()

    return dateFormatter.format(currentDate)
}