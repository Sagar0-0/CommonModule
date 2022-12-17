package fit.asta.health.navigation.home.view.component

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home.view.Testimonials
import fit.asta.health.testimonials.TestimonialsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
@OptIn(ExperimentalPagerApi::class)
fun ReadyScreen(activity: Activity, toolsHome: ToolsHome) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .background(color = MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())
        ) {

            NameAndMoodHomeScreenHeader()

            Spacer(modifier = Modifier.height(24.dp))

            toolsHome.weather?.let {
                WeatherCardImage(
                    temperature = it.temperature,
                    location = it.location,
                    date = "Friday,24 October"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            toolsHome.banners?.let { BannerAutoSlider(bannerList = it) }

            MyToolsAndViewAll(myTools = "My Tools", allTools = "All Tools", onClick = {

                TestimonialsActivity.launch(context = context)
            })

            toolsHome.tools?.let { VerticalImageCards(toolsList = it) }

            toolsHome.testimonials?.let { Testimonials(testimonialsList = it) }

            Spacer(modifier = Modifier.height(24.dp))
            RateUsCard(activity)
            Spacer(modifier = Modifier.height(24.dp))
            ReferAndEarn()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}