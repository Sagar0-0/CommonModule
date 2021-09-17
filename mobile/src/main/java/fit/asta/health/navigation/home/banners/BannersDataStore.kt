package fit.asta.health.navigation.home.banners

import fit.asta.health.navigation.home.banners.data.BannerData


interface BannersDataStore {
    fun updateList(list: List<BannerData>)
    fun getBanner(position: Int): BannerData
}