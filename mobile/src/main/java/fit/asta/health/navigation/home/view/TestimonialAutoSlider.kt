package fit.asta.health.navigation.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.R
import fit.asta.health.navigation.home.view.component.MyToolsAndViewAll
import fit.asta.health.navigation.home.view.component.TestimonialAutoSliderAnimation
import fit.asta.health.testimonials.TestimonialsActivity
import fit.asta.health.testimonials.model.domain.Testimonial
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Testimonials(
    testimonialsList: List<Testimonial>,
) {

    val context = LocalContext.current
    FontFamily(Font(R.font.inter_regular, FontWeight.Normal))
    FontFamily(Font(R.font.inter_medium))

    MyToolsAndViewAll(myTools = "Testimonials", allTools = "View All", onClick = {
        TestimonialsActivity.launch(context = context)
    })

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Box(
            Modifier
                .height(26.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 1.dp, bottom = 1.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.testimonials_tagline),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        */

        Text(
            text = "Why our customers love ASTA?",
            style = MaterialTheme.typography.h6,
            color = Color.Gray
        )

        Divider(
            color = Color(0xFF0088FF),
            thickness = 4.dp,
            modifier = Modifier
                .width(71.dp)
                .clip(RoundedCornerShape(2.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TestimonialAutoSliderAnimation(testimonialsList = testimonialsList)
    }
}