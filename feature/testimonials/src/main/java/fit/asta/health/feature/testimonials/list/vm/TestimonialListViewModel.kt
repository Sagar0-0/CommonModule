package fit.asta.health.feature.testimonials.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.feature.testimonials.list.vm.TestimonialListEvent.Remove
import fit.asta.health.network.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


@HiltViewModel
class TestimonialListViewModel
@Inject constructor(
    private val testimonialRepo: fit.asta.health.data.testimonials.repo.TestimonialRepo,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val modificationEvents = MutableStateFlow<List<TestimonialListEvent>>(emptyList())

    val testimonialPager =
        Pager(PagingConfig(pageSize = fit.asta.health.data.testimonials.repo.TestimonialDataSource.PAGE_SIZE)) {
            fit.asta.health.data.testimonials.repo.TestimonialDataSource(
                testimonialRepo,
                networkHelper
            )
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
            is Remove -> {
                paging.filter { event.id != it.id }
            }
        }
    }
}