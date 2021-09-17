package fit.asta.health.subscription.ui

import fit.asta.health.subscription.data.SubscriptionPlanData

sealed class SubscriptionAction {

    class LoadSubscriptionPlanAction(val subPlanData: SubscriptionPlanData) : SubscriptionAction()
    object Empty : SubscriptionAction()
    class Error(val message: String) : SubscriptionAction()
}