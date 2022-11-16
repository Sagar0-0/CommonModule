package fit.asta.health.testimonials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.network.NetworkHelper
import fit.asta.health.testimonials.model.TestimonialDataSource
import fit.asta.health.testimonials.model.TestimonialRepo
import javax.inject.Inject


@HiltViewModel
class TestimonialListViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    val testimonialPager = Pager(PagingConfig(pageSize = 10)) {
        TestimonialDataSource(testimonialRepo)
    }.flow.cachedIn(viewModelScope)
}