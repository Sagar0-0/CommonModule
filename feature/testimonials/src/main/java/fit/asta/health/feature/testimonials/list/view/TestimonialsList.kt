package fit.asta.health.feature.testimonials.list.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppErrorMsgCard
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppProgressArc
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.testimonials.list.vm.TestimonialListViewModel

@Composable
fun TestimonialsList(
    paddingValues: PaddingValues,
    viewModel: TestimonialListViewModel
) {
    val testimonials = viewModel.testimonialPager.collectAsLazyPagingItems()

    LazyColumn(Modifier.padding(paddingValues)) {
        items(testimonials.itemCount) { index ->
            val testimonial = testimonials[index]
            testimonial?.let { item ->

                AppCard(modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.appSpacing.medium),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                    content = {
                        when (TestimonialType.from(item.type)) {
                            is TestimonialType.TEXT -> TstViewTxtLayout(item)
                            is TestimonialType.IMAGE -> TstViewImgLayout(item)
                            is TestimonialType.VIDEO -> TstViewVideoLayout(item)
                        }
                    })
            }
        }

        when {
            // Refresh
            testimonials.loadState.refresh is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }
            }
            // Append
            testimonials.loadState.append is LoadState.Loading -> {
                item {
                    LoadingItem()
                }
            }
            // Refresh error
            testimonials.loadState.refresh is LoadState.Error -> {
                item {
                    AppErrorScreen(onTryAgain = {
                        testimonials.refresh()
                    })
                }
            }
            // Append error
            testimonials.loadState.append is LoadState.Error -> {
                item {
                    AppErrorMsgCard(
                        message = "Some error occurred", imageVector = Icons.Filled.Help
                    )
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
        AppProgressArc(
            modifier = Modifier
                .size(AppTheme.appBoxSize.largeSmall)
                .padding(AppTheme.appSpacing.medium),
            strokeWidth = AppTheme.appSpacing.extraSmall
        )
    }
}
