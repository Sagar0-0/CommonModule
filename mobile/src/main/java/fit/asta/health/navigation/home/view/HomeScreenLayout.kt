package fit.asta.health.navigation.home.view

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import fit.asta.health.navigation.home.view.component.*
import fit.asta.health.player.jetpack_video.VideoActivity
import fit.asta.health.tools.meditation.MeditationActivity
import fit.asta.health.tools.walking.view.WalkingActivity
import fit.asta.health.tools.water.WaterToolActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenLayout(activity: Activity, toolsHome: ToolsHomeRes.ToolsHome) {

    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            item { NameAndMoodHomeScreenHeader() }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                toolsHome.banners.let { BannerAutoSlider(bannerList = it) }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                MyToolsAndViewAll(myTools = "My Tools", allTools = "All Tools", onClick = {
                    //TODO - Integrate All tools
                })
            }

            item {

                toolsHome.tools.let {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                            .padding(horizontal = 16.dp),
                        userScrollEnabled = true
                    ) {
                        it.forEachIndexed { _, tool ->
                            item {
                                ToolsCardLayoutDemo(
                                    type = tool.name, cardTitle = tool.title, imgUrl = tool.url
                                ) {
                                    when (it.lowercase(Locale.getDefault())) {
                                        "water" -> {
                                            WaterToolActivity.launch(context = context)
                                        }

                                        "steps" -> {
                                            WalkingActivity.launch(context = context)
                                        }

                                        "workout" -> {
                                            VideoActivity.launch(context = context)
                                        }

                                        "yoga" -> {
                                            VideoActivity.launch(context = context)
                                        }

                                        "meditation"->{
                                            MeditationActivity.launch(context=context)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item { toolsHome.testimonials.let { Testimonials(testimonialsList = it) } }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { RateUsCard(activity) }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { ReferAndEarn() }

            item { Spacer(modifier = Modifier.height(130.dp)) }
        }
    }
}

@Composable
fun dateFormattedScreen(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)
    val currentDate = LocalDate.now()

    return dateFormatter.format(currentDate)
}