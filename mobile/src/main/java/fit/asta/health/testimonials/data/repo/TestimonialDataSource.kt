package fit.asta.health.testimonials.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fit.asta.health.network.NetworkHelper
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.testimonials.data.model.Testimonial


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

        return if (networkHelper.isConnected()) {
            when (val result = repo.getTestimonials(index = index, limit = params.loadSize)) {
                is ApiResponse.Error -> LoadResult.Error(result.exception)
                is ApiResponse.HttpError -> LoadResult.Error(result.ex)
                is ApiResponse.Success -> {
                    LoadResult.Page(
                        data = result.data,
                        prevKey = if (index == STARTING_PAGE_INDEX) null else index - 1,
                        nextKey = if (result.data.isEmpty()) null else params.key?.plus(1)
                    )
                }
            }
        } else {
            LoadResult.Error(Throwable("NoInternet"))
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        private const val STARTING_PAGE_INDEX = 0
    }
}