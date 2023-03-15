package fit.asta.health.testimonials.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import fit.asta.health.testimonials.viewmodel.list.TestimonialListViewModel
import fit.asta.health.ui.theme.boxSize
import fit.asta.health.ui.theme.cardElevation
import fit.asta.health.ui.theme.imageHeight
import fit.asta.health.ui.theme.spacing

@Composable
fun TestimonialsList(
    paddingValues: PaddingValues,
    viewModel: TestimonialListViewModel,
) {
    val testimonials = viewModel.testimonialPager.collectAsLazyPagingItems()
    testimonials.refresh()

    LazyColumn(
        Modifier.padding(paddingValues)
        //.background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {

        items(testimonials) { item ->
            item?.let {
                when (it.type) {
                    TestimonialType.TEXT -> TestimonialTextCard(it)
                    TestimonialType.IMAGE -> TestimonialImageCard(it)
                    TestimonialType.VIDEO -> TestimonialsVideoCard(it)
                }
            }
        }

        testimonials.apply {
            when {
                // refresh
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation()
                        }
                    }
                }
                // reload
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
                // refresh error
                loadState.refresh is LoadState.Error -> {
                    item {
                        NoInternetLayout(onTryAgain = {
                            testimonials.refresh()
                        })
                    }
                }
                // reload error
                loadState.append is LoadState.Error -> {
                    item {
                        ErrorItem(message = "Some error occurred")
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(boxSize.largeSmall)
                .padding(spacing.medium), strokeWidth = 5.dp
        )

    }
}

@Composable
fun ErrorItem(message: String) {
    Card(
        elevation = cardElevation.extraSmall,
        modifier = Modifier
            .padding(spacing.medium)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(spacing.small)
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(imageHeight.small),
                painter = painterResource(id = R.drawable.ic_help),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                color = Color.White,
                text = message,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = spacing.medium)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}