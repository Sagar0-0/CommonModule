@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package fit.asta.health.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.navigation.home.presentation.components.*
import fit.asta.health.home.view.AutoSliding

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .clip(RoundedCornerShape(16.dp))) {
                Column(Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .background(color = MaterialTheme.colors.background)
                    .verticalScroll(rememberScrollState())) {
                    NameAndMoodHomeScreenHeader()
                    Spacer(modifier = Modifier.height(24.dp))
                    WeatherCardImage()
                    Spacer(modifier = Modifier.height(24.dp))
                    AutoSliding()
                    MyToolsAndViewAll()
                    VerticalImageCards()
                    Testimonials()
                    Spacer(modifier = Modifier.height(24.dp))
                    RateUsCard()
                    Spacer(modifier = Modifier.height(24.dp))
                    ReferAndEarn()
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}












