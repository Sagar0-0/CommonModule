package fit.asta.health.old.subscription

import fit.asta.health.old.subscription.data.SubscriptionData

sealed class SubscriptionPagerAction {

    class LoadSubscriptionPagerAction(val subscriptionData: SubscriptionData) :
        SubscriptionPagerAction()

    object Empty : SubscriptionPagerAction()
    class Error(val message: String) : SubscriptionPagerAction()
}