package fit.asta.health.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import fit.asta.health.R
import fit.asta.health.home.model.sliderDataList
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@ExperimentalPagerApi
@Composable
fun AutoSliding() {
    val pagerState = rememberPagerState(
        pageCount = sliderDataList.size
    )

    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(2500)
        var newPosition = pagerState.currentPage + 1
        if (newPosition > (sliderDataList.size - 1)) newPosition = 0
        pagerState.animateScrollToPage(newPosition)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { page ->
            Box(
                modifier = Modifier
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
                    .fillMaxWidth()
                    .height(236.dp)
            ) {
                val sliderDataPages = sliderDataList[page]
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
                            color = Color.White
                        )
                    }
                }
            }
        }
        //Horizontal dot indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

