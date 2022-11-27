package fit.asta.health.navigation.home.view.component

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home.view.BannerAutoSlider
import fit.asta.health.navigation.home.view.Testimonials
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenLayout(activity: Activity, toolsHome: ToolsHome) {

    Box(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            item { NameAndMoodHomeScreenHeader() }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                toolsHome.weather?.let {
                    WeatherCardImage(temperature = it.temperature,
                        location = it.location,
                        date = "Friday,24 October")
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                toolsHome.banners?.let { BannerAutoSlider(bannerList = it) }
            }

            item {
                MyToolsAndViewAll(myTools = "My Schedules", allTools = "All Schedules", onClick = {

                    //TODO - Integrate All tools
                })
            }

            item {
                ScheduleCardLayout()
            }

            item {
                MyToolsAndViewAll(myTools = "My Tools", allTools = "All Tools", onClick = {

                    //TODO - Integrate All tools
                })
            }

            item {

                toolsHome.tools?.let {
                    LazyVerticalGrid(columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(560.dp)
                            .padding(horizontal = 16.dp)) {
                        it.forEachIndexed { index, _ ->
                            item {
                                ToolsCardLayoutDemo(imgUrl = toolsHome.tools[index].url,
                                    cardTitle = toolsHome.tools[index].title)
                            }
                        }
                    }
                }


            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {

                toolsHome.tools?.let {
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1315.dp)
                            .padding(horizontal = 16.dp)) {
                        it.forEachIndexed { index, _ ->
                            item {
                                ToolsCardLayoutDemo(imgUrl = toolsHome.tools[index].url,
                                    cardTitle = toolsHome.tools[index].title,
                                    imageModifier = Modifier.height(180.dp))
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item { toolsHome.testimonials?.let { Testimonials(testimonialsList = it) } }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { RateUsCard(activity) }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { ReferAndEarn() }

            item { Spacer(modifier = Modifier.height(130.dp)) }
        }
    }
}