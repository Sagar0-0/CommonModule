package fit.asta.health.navigation.home.banners

import fit.asta.health.navigation.home.banners.data.BannerData
import kotlinx.coroutines.flow.Flow

interface BannersRepo {
    suspend fun fetchBanners(type: String): Flow<List<BannerData>>
}