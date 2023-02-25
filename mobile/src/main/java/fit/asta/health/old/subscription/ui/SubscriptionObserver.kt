package fit.asta.health.old.subscription.ui

import androidx.lifecycle.Observer

class SubscriptionObserver(private val subscriptionView: SubscriptionView) :
    Observer<SubscriptionAction> {
    override fun onChanged(action: SubscriptionAction) {
        when (action) {
            is SubscriptionAction.LoadSubscriptionPlanAction -> {
                val state = SubscriptionView.State.LoadSubscriptionPlan(action.subPlanData)
                subscriptionView.changeState(state)
            }
            SubscriptionAction.Empty -> {
                val state = SubscriptionView.State.Empty
                subscriptionView.changeState(state)
            }
            is SubscriptionAction.Error -> {
                val state = SubscriptionView.State.Error(action.message)
                subscriptionView.changeState(state)
            }
        }
    }
}