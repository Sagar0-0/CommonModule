package fit.asta.health.testimonials.viewmodel.create

import androidx.compose.runtime.collectAsState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class TstSource(private val repo: TestimonialRepo) :
    PagingSource<Int, NetTestimonialsRes>() {

    override fun getRefreshKey(state: PagingState<Int, NetTestimonialsRes>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition((anchorPosition))
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetTestimonialsRes> {
        return try {
            val page = params.key ?: 1
            val response = repo.getTestimonials(page, 10)
            LoadResult.Page(
                data = response.collectAsState(initial =),
                prevKey = null,
                nextKey = if (response.collect())
            )
        }
    }

}