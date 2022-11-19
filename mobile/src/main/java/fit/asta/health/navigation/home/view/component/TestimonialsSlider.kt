package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import fit.asta.health.testimonials.model.domain.Testimonial
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Composable
fun TestimonialsSliderLayout(
    testimonialsList: List<Testimonial>,
    pagerState: PagerState,
    interExtraBoldFontFamily: FontFamily,
    interFontFamily: FontFamily,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(state = pagerState,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally) { page ->
            Box(modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                }
                .fillMaxWidth()) {
                val testimonialsDataPages = testimonialsList[page]
                TestimonialTextCard(interExtraBoldFontFamily,
                    testimonialsDataPages,
                    interFontFamily)
            }
            //Horizontal dot indicator
            HorizontalPagerIndicator(pagerState = pagerState,
                modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}