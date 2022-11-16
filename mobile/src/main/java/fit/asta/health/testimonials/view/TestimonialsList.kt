package fit.asta.health.testimonials.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
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
    }
}