package fit.asta.health.navigation.home_old.banners

import fit.asta.health.navigation.home_old.banners.data.BannerData


class BannersDataStoreImpl :
    BannersDataStore {
    private var listBanners: List<BannerData> = listOf()

    override fun updateList(list: List<BannerData>) {
        listBanners = list
    }

    override fun getBanner(position: Int): BannerData {
        return listBanners[position]
    }
}