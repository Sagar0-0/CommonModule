package fit.asta.health.navigation.home.banners.data

import fit.asta.health.navigation.home.banners.networkdata.BannerResponse

class BannersDataMapper {

    fun toMap(bannerResponse: BannerResponse): List<BannerData> {
        return bannerResponse.data.map {
            BannerData().apply {
                uid = it.uid
                typeId = it.tid
                type = it.type
                title = it.ttl
                subTitle = it.sub
                url = it.url
            }
        }
    }
}