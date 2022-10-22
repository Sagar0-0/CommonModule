package fit.asta.health.onboarding_screen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import fit.asta.health.R


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(
    modifier: Modifier = Modifier,
) {

    val items = ArrayList<OnBoardingData>()

    items.add(OnBoardingData(image = R.drawable.ic_illustration_shopping,
        title = "First Page",
        desc = "Hello Fist Page"))
    items.add(OnBoardingData(image = R.drawable.ic_illustration_delivery,
        title = "First Page",
        desc = "Hello Fist Page"))
    items.add(OnBoardingData(image = R.drawable.ic_illustration_research,
        title = "First Page",
        desc = "Hello Fist Page"))

    val pagerState = rememberPagerState(pageCount = items.size,
        initialPage = 0,
        initialOffscreenLimit = items.size - 1,
        infiniteLoop = false)

    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(state = pagerState) { page ->
                Column(modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = items[page].image),
                        contentDescription = items[page].title,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth())
                    Text(text = items[page].title, modifier = Modifier.padding(top = 50.dp))
                    Text(text = items[page].desc,
                        modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center)
                }
            }
            PagerIndicator(size = items.size, currentPage = pagerState.currentPage)
        }
        Box(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            BottomNavigationSection(currentPage = pagerState.currentPage)
        }
    }
}


@Preview
@Composable
fun PreviewOnBoard() {
    Surface(modifier = Modifier.fillMaxSize()) {
        OnBoardingPager(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White))
    }
}