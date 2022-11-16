package fit.asta.health.testimonials.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import fit.asta.health.R
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.testimonials.view.list.TestimonialImageCard
import fit.asta.health.testimonials.view.list.TestimonialTextCard
import fit.asta.health.testimonials.view.list.TestimonialsVideoCard
import fit.asta.health.testimonials.viewmodel.TestimonialListViewModel


@Composable
fun TestimonialsList(
    paddingValues: PaddingValues,
    viewModel: TestimonialListViewModel
) {
    val testimonials = viewModel.testimonialPager.collectAsLazyPagingItems()

    LazyColumn(
        Modifier
            .padding(paddingValues)
            .background(color = Color(0xffF4F6F8))
    ) {

        items(testimonials) { item ->
            item?.let {
                when (TestimonialType.fromInt(it.type)) {
                    TestimonialType.TEXT -> TestimonialTextCard(it)
                    TestimonialType.IMAGE -> TestimonialImageCard(it)
                    TestimonialType.VIDEO -> TestimonialsVideoCard(it)
                }
            }
        }

        testimonials.apply {

            when (loadState.append) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        LoadingAnimation()
                    }
                }
                is LoadState.Error -> {
                    item {
                        ErrorItem(message = "Some error occurred")
                    }
                }
            }

            when (loadState.refresh) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        LoadingAnimation()
                    }
                }
                is LoadState.Error -> {
                    item {
                        NoInternetLayout()
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorItem(message: String) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp),
                painter = painterResource(id = R.drawable.ic_help),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}