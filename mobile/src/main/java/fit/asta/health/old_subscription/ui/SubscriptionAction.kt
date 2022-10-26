package fit.asta.health.old_subscription.ui

import fit.asta.health.old_subscription.data.SubscriptionPlanData

sealed class SubscriptionAction {

    class LoadSubscriptionPlanAction(val subPlanData: SubscriptionPlanData) : SubscriptionAction()
    object Empty : SubscriptionAction()
    class Error(val message: String) : SubscriptionAction()
}