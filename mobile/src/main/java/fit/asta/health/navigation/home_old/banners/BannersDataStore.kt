package fit.asta.health.navigation.home_old.banners

import fit.asta.health.navigation.home_old.banners.data.BannerData


interface BannersDataStore {
    fun updateList(list: List<BannerData>)
    fun getBanner(position: Int): BannerData
}