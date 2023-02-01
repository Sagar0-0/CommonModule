package fit.asta.health.testimonials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.network.NetworkHelper
import fit.asta.health.testimonials.model.TestimonialDataSource
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.domain.Testimonial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


@HiltViewModel
class TestimonialListViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val modificationEvents = MutableStateFlow<List<TestimonialListEvent>>(emptyList())

    val testimonialPager =
        Pager(PagingConfig(pageSize = TestimonialDataSource.PAGE_SIZE)) {
            TestimonialDataSource(testimonialRepo, networkHelper)
        }.flow
            .cachedIn(viewModelScope)
            .combine(modificationEvents) { pagingData, modifications ->
                modifications.fold(pagingData) { acc, event ->
                    applyEvents(acc, event)
                }
            }
    //val testimonialViewState: LiveData<PagingData<NetTestimonial>> = testimonialPager.asLiveData()

    fun onViewEvent(event: TestimonialListEvent) {
        modificationEvents.value += event
    }

    private fun applyEvents(
        paging: PagingData<Testimonial>,
        event: TestimonialListEvent
    ): PagingData<Testimonial> {
        return when (event) {
            is TestimonialListEvent.Remove -> {
                paging.filter { event.id != it.id }
            }
        }
    }
}