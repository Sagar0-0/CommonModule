package fit.asta.health.feature.testimonials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.data.testimonials.remote.model.Testimonial
import fit.asta.health.data.testimonials.repo.TestimonialDataSource
import fit.asta.health.data.testimonials.repo.TestimonialRepo
import fit.asta.health.feature.testimonials.events.TestimonialListEvent
import fit.asta.health.feature.testimonials.events.TestimonialListEvent.Remove
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


@HiltViewModel
class TestimonialListViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo
) : ViewModel() {

    private val modificationEvents = MutableStateFlow<List<TestimonialListEvent>>(emptyList())

    val testimonialPager =
        Pager(PagingConfig(pageSize = TestimonialDataSource.PAGE_SIZE)) {
            TestimonialDataSource(
                testimonialRepo
            )
        }.flow
            .cachedIn(viewModelScope)
            .combine(modificationEvents) { pagingData, modifications ->
                modifications.fold(pagingData) { acc, event ->
                    applyEvents(acc, event)
                }
            }

    private fun applyEvents(
        paging: PagingData<Testimonial>,
        event: TestimonialListEvent
    ): PagingData<Testimonial> {
        return when (event) {
            is Remove -> {
                paging.filter { event.id != it.id }
            }
        }
    }
}