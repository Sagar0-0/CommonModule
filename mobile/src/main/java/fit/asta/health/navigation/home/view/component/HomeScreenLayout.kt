package fit.asta.health.navigation.home.view.component

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home.view.BannerAutoSlider
import fit.asta.health.navigation.home.view.Testimonials
import fit.asta.health.tools.water.WaterToolActivity
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

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)) {
                    Text(text = "Upcoming Vitamin D Session",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black)
                }

            }

            item {

                LazyVerticalGrid(columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .padding(horizontal = 16.dp),
                    userScrollEnabled = false) {

                    repeat(times = 3) {
                        item {
                            SunlightSlotsCardLayout()
                        }
                    }

                }
            }

            item {
                MyToolsAndViewAll(myTools = "My Schedules", allTools = "All Schedules", onClick = {

                    //TODO - Integrate All Schedules
                })
            }

            item {
                ScheduleCardLayout()
            }

            item {
                MyToolsAndViewAll(myTools = "My Tools", allTools = "All Tools", onClick = {

                    //TODO - Integrate All tools
                    val myIntent =Intent(activity.applicationContext, WaterToolActivity::class.java)
                    myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    activity.applicationContext.startActivity(myIntent)
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
                            .padding(horizontal = 16.dp),
                        userScrollEnabled = false) {
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
                            .padding(horizontal = 16.dp),
                        userScrollEnabled = false) {
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