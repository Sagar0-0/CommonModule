package fit.asta.health.testimonials.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fit.asta.health.testimonials.model.network.NetTestimonial

private const val STARTING_PAGE_INDEX = 0

class TestimonialDataSource(
    private val repo: TestimonialRepo
) : PagingSource<Int, NetTestimonial>() {

    override fun getRefreshKey(state: PagingState<Int, NetTestimonial>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetTestimonial> {

        val index = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = repo.getTestimonials(index = index, limit = params.loadSize)
            LoadResult.Page(
                data = response.testimonials,
                prevKey = if (index == STARTING_PAGE_INDEX) null else index - 1,
                nextKey = if (response.testimonials.isEmpty()) null else params.key?.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}