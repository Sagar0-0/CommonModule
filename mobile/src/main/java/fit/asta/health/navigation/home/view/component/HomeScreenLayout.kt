package fit.asta.health.navigation.home.view.component

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
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
                    WeatherCardImage(
                        temperature = it.temperature,
                        location = it.location,
                        date = "Friday,24 October"
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                toolsHome.banners?.let { BannerAutoSlider(bannerList = it) }
            }

            item {
                MyToolsAndViewAll(myTools = "My Tools", allTools = "All Tools", onClick = {

                    //TODO - Integrate All tools
                })
            }

            item {

                val itemSize: Dp = LocalConfiguration.current.screenWidthDp.dp / 2

                FlowRow(
                    mainAxisSize = SizeMode.Expand,
                    mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
                ) {

                    toolsHome.tools?.let {
                        it.forEachIndexed { index, _ ->
                            ToolsCardLayoutDemo(
                                imgUrl = toolsHome.tools[index].url,
                                cardTitle = toolsHome.tools[index].title,
                                modifier = Modifier.size(width = itemSize, height = 250.dp)
                            )
                        }
                    }
                }

            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { toolsHome.testimonials?.let { Testimonials(testimonialsList = it) } }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { RateUsCard(activity) }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { ReferAndEarn() }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}