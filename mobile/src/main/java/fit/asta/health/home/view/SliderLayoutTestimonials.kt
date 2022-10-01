package fit.asta.health.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import fit.asta.health.R
import fit.asta.health.home.model.slideClientDataList
import fit.asta.health.home.model.sliderDataList
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@Preview(showBackground = true)
@ExperimentalPagerApi
@Composable
fun AutoSlidingTestimonials() {

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    FontFamily(Font(R.font.inter_medium))

    val interExtraBoldFontFamily = FontFamily(
        Font(R.font.inter_extrabold, FontWeight.ExtraBold)
    )

    val pagerState = rememberPagerState(
        pageCount = slideClientDataList.size
    )

    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(2500)
        var newPosition = pagerState.currentPage + 1
        if (newPosition > (sliderDataList.size - 1)) newPosition = 0
        pagerState.animateScrollToPage(newPosition)
    }

    TestimonialsBanner(pagerState, interExtraBoldFontFamily, interFontFamily)
}

@ExperimentalPagerApi
@Composable
private fun TestimonialsBanner(
    pagerState: PagerState,
    interExtraBoldFontFamily: FontFamily,
    interFontFamily: FontFamily,
) {
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
            ) {
                val sliderDataPages = slideClientDataList[page]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))

                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(all = 16.dp)
                    ) {
                        FontFamily(
                            Font(R.font.inter_light, FontWeight.Light)
                        )
                        Column {
                            Box {
                                Text(
                                    text = "❝",
                                    fontFamily = interExtraBoldFontFamily,
                                    fontSize = 20.sp,
                                    color = Color(0xFF0277BD)
                                )
                            }
                            Text(
                                text = sliderDataPages.clientDescription,
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Thin
                            )
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Text(
                                    text = "❞",
                                    fontFamily = interExtraBoldFontFamily,
                                    fontSize = 20.sp,
                                    color = Color(0xFF0277BD)
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Row(Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Box(Modifier.size(width = 80.dp, height = 80.dp)) {
                                        Image(painter = painterResource(id = R.drawable.testimonial_person1),
                                            contentDescription = null,
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier.fillMaxSize())
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.SpaceBetween) {
                                        Text(text = sliderDataPages.clientName,
                                            fontFamily = interFontFamily,
                                            fontSize = 16.sp,
                                            color = Color.Black)
                                        Text(text = sliderDataPages.clientJob,
                                            fontFamily = interFontFamily,
                                            fontSize = 12.sp,
                                            color = Color.Black)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Horizontal dot indicator
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }

    }
}
