package fit.asta.health.testimonials.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.theme.boxSize
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.imageHeight
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.testimonials.view.list.TestimonialImageCard
import fit.asta.health.testimonials.view.list.TestimonialTextCard
import fit.asta.health.testimonials.view.list.TestimonialsVideoCard
import fit.asta.health.testimonials.viewmodel.list.TestimonialListViewModel


@Composable
fun TestimonialsList(
    paddingValues: PaddingValues,
    viewModel: TestimonialListViewModel,
    player: Player,
) {
    val testimonials = viewModel.testimonialPager.collectAsLazyPagingItems()
    testimonials.refresh()

    LazyColumn(
        Modifier.padding(paddingValues)
        //.background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {

        items(count = testimonials.itemCount,
            key = testimonials.itemKey(),
            contentType = testimonials.itemContentType()) { inx ->
            val item = testimonials[inx]
            item?.let {
                when (TestimonialType.from(it.type)) {
                    TestimonialType.TEXT -> TestimonialTextCard(it)
                    TestimonialType.IMAGE -> TestimonialImageCard(it)
                    TestimonialType.VIDEO -> TestimonialsVideoCard(it, player = player)
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
                        AppErrorScreen(onTryAgain = {
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
        elevation = CardDefaults.cardElevation(cardElevation.extraSmall),
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