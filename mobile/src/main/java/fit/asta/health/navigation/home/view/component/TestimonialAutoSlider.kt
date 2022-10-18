package fit.asta.health.navigation.home.view.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import fit.asta.health.R
import fit.asta.health.navigation.home.model.domain.Testimonial
import kotlinx.coroutines.delay

@ExperimentalPagerApi
@Composable
fun TestimonialAutoSliderAnimation(testimonialsList: List<Testimonial>) {

    val interFontFamily = FontFamily(Font(R.font.inter_regular, FontWeight.Normal))

    FontFamily(Font(R.font.inter_medium))

    val interExtraBoldFontFamily = FontFamily(Font(R.font.inter_extrabold, FontWeight.ExtraBold))

    val pagerState = rememberPagerState(pageCount = testimonialsList.size)

    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(2500)
        var newPosition = pagerState.currentPage + 1
        if (newPosition > (testimonialsList.size - 1)) newPosition = 0
        pagerState.animateScrollToPage(newPosition)
    }

    TestimonialsSliderLayout(testimonialsList = testimonialsList,
        pagerState,
        interExtraBoldFontFamily,
        interFontFamily)
}