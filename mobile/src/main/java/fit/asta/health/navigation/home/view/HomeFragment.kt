package fit.asta.health.navigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.R
import fit.asta.health.navigation.home.view.component.*


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.home_header_layout, container, false)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
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
                    WeatherCardImage()
                    Spacer(modifier = Modifier.height(24.dp))
                    BannerAutoSlider()
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
        return view
    }
}