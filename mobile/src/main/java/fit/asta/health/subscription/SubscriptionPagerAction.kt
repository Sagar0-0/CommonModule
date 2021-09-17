package fit.asta.health.subscription

import fit.asta.health.subscription.data.SubscriptionData

sealed class SubscriptionPagerAction {

    class LoadSubscriptionPagerAction(val subscriptionData: SubscriptionData) :
        SubscriptionPagerAction()

    object Empty : SubscriptionPagerAction()
    class Error(val message: String) : SubscriptionPagerAction()
}