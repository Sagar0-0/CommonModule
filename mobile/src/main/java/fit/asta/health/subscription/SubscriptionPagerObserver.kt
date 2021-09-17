package fit.asta.health.subscription

import androidx.lifecycle.Observer

class SubscriptionPagerObserver(private val subscriptionPagerView: SubscriptionPagerView) :
    Observer<SubscriptionPagerAction> {
    override fun onChanged(action: SubscriptionPagerAction) {
        when (action) {
            is SubscriptionPagerAction.LoadSubscriptionPagerAction -> {
                val state = SubscriptionPagerView.State.LoadSubscription(action.subscriptionData)
                subscriptionPagerView.changeState(state)
            }
            SubscriptionPagerAction.Empty -> {
                val state = SubscriptionPagerView.State.Empty
                subscriptionPagerView.changeState(state)
            }
            is SubscriptionPagerAction.Error -> {
                val state = SubscriptionPagerView.State.Error(action.message)
                subscriptionPagerView.changeState(state)
            }
        }
    }
}