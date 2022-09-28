package fit.asta.health.navigation.home_old.banners

import fit.asta.health.navigation.home_old.banners.data.BannerData
import fit.asta.health.navigation.home_old.banners.data.BannersDataMapper
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BannersRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: BannersDataMapper
) : BannersRepo {

    override suspend fun fetchBanners(type: String): Flow<List<BannerData>> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getBanners(type)))
        }
    }
}