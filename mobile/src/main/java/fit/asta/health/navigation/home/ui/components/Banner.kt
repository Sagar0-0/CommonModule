package fit.asta.health.navigation.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import fit.asta.health.R
import fit.asta.health.navigation.home.model.dummy.HomeScreenImageSliderData


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    page: Int,
    sliderDataPages: HomeScreenImageSliderData,
    pagerState: PagerState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(
                id = when (page) {
                    1 -> R.drawable.first_image
                    2 -> R.drawable.second_image
                    else -> R.drawable.third_image
                }
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 54.dp, vertical = 82.dp)
        ) {
            val interLightFontFamily = FontFamily(
                Font(R.font.inter_light, FontWeight.Light)
            )
            Text(
                text = sliderDataPages.description,
                fontSize = 20.sp,
                fontFamily = interLightFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        //Horizontal dot indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}