package fit.asta.health.testimonials.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fit.asta.health.network.NetworkHelper
import fit.asta.health.testimonials.model.domain.Testimonial


class TestimonialDataSource(
    private val repo: TestimonialRepo,
    private val networkHelper: NetworkHelper
) : PagingSource<Int, Testimonial>() {

    override fun getRefreshKey(state: PagingState<Int, Testimonial>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Testimonial> {

        val index = params.key ?: STARTING_PAGE_INDEX

        return try {
            if (networkHelper.isConnected()) {
                val testimonials = repo.getTestimonials(index = index, limit = params.loadSize)
                LoadResult.Page(
                    data = testimonials,
                    prevKey = if (index == STARTING_PAGE_INDEX) null else index - 1,
                    nextKey = if (testimonials.isEmpty()) null else params.key?.plus(1)
                )
            } else {
                LoadResult.Error(Throwable("NoInternet"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        private const val STARTING_PAGE_INDEX = 0
    }
}