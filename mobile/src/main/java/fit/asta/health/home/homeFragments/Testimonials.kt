package fit.asta.health.home.homeFragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.R
import fit.asta.health.home.view.AutoSlidingTestimonials

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Testimonials() {
    FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    FontFamily(Font(R.font.inter_medium))

    Column(Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier
            .height(26.dp)
            .align(Alignment.CenterHorizontally)
            .padding(top = 1.dp, bottom = 1.dp)) {
            Image(painter = painterResource(id = R.drawable.testimonials_tagline),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        Divider(color = Color(0xFF0088FF), thickness = 4.dp, modifier = Modifier
            .width(71.dp)
            .clip(
                RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.height(16.dp))
        AutoSlidingTestimonials()
    }
}