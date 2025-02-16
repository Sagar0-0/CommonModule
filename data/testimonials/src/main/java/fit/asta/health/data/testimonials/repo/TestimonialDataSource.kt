package fit.asta.health.data.testimonials.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.testimonials.remote.model.Testimonial

class TestimonialDataSource(
    private val repo: TestimonialRepo
) : PagingSource<Int, Testimonial>() {

    override fun getRefreshKey(state: PagingState<Int, Testimonial>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Testimonial> {

        val index = params.key ?: STARTING_PAGE_INDEX
        return when (val result = repo.getAllTestimonials(index = index, limit = params.loadSize)) {
            is ResponseState.Success -> {
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (index == STARTING_PAGE_INDEX) null else index - 1,
                    nextKey = if (result.data.isEmpty()) null else params.key?.plus(1)
                )
            }

            else -> {
                LoadResult.Error(Exception())
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        private const val STARTING_PAGE_INDEX = 0
    }
}