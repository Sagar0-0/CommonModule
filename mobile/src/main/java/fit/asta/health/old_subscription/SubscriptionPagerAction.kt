package fit.asta.health.old_subscription

import fit.asta.health.old_subscription.data.SubscriptionData

sealed class SubscriptionPagerAction {

    class LoadSubscriptionPagerAction(val subscriptionData: SubscriptionData) :
        SubscriptionPagerAction()

    object Empty : SubscriptionPagerAction()
    class Error(val message: String) : SubscriptionPagerAction()
}