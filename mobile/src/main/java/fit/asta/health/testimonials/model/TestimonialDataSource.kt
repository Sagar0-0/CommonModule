package fit.asta.health.testimonials.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import fit.asta.health.testimonials.model.network.NetTestimonial


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
        return try {
            val index = params.key ?: 0
            val response = repo.getTestimonials(index = index, limit = 10)
            LoadResult.Page(
                data = response.testimonials,
                prevKey = null,
                nextKey = if (response.testimonials.isEmpty()) params.key?.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}