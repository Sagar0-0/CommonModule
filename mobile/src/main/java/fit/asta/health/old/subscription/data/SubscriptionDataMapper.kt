package fit.asta.health.old.subscription.data

import fit.asta.health.old.subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.old.subscription.networkdata.SubscriptionStatusResponse

class SubscriptionDataMapper {

    fun toMap(subInfo: SubscriptionDataResponse): SubscriptionData {

        return SubscriptionData().apply {
            title = subInfo.data.ttl
            plans = subInfo.data.types.map {
                SubscriptionPlanData().apply {
                    uid = it.uid
                    type = it.type
                    title = it.ttl
                    desc = subInfo.data.desc
                    url = it.url
                    features = it.features.map {
                        FeatureItem().apply {
                            title = it.ttl
                            description = it.desc
                            url = it.url
                        }
                    }
                    subscriptions = it.plans.map {
                        SubscriptionItem().apply {
                            uid = it.uid
                            tag = it.tag
                            title = it.ttl
                            subTitle = it.sub
                            description = it.desc
                            price = it.price
                            selected = SubscriptionItemType.valueOf(it.default)
                        }
                    }
                }
            }
        }
    }

    fun toMap(subStatus: SubscriptionStatusResponse): SubscriptionStatus {
        return SubscriptionStatus().apply {
            uid = subStatus.data.uid
            userId = subStatus.data.userId
            plan = subStatus.data.plan
            if (subStatus.data.offer != null) {
                val offerData = subStatus.data.offer
                offer = OfferData().apply {
                    code = offerData.code
                    desc = offerData.desc
                    imgUrl = offerData.imgUrl
                    startDate = offerData.startDate
                    ttl = offerData.ttl
                    uid = offerData.uid
                    validTill = offerData.uid
                }
            }
        }
    }
}