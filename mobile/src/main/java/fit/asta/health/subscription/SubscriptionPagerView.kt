package fit.asta.health.subscription

import android.app.Activity
import android.view.View
import fit.asta.health.subscription.data.SubscriptionData
import fit.asta.health.subscription.listner.SubscriptionViewPagerListener

interface SubscriptionPagerView {

    fun setContentView(activity: Activity): View?
    fun changeState(state: State)
    fun setUpViewPager(
        fragmentActivity: SubscriptionActivity,
        listener: SubscriptionViewPagerListener
    )

    fun continueClickListener(listener: View.OnClickListener)

    sealed class State {
        class LoadSubscription(val subscriptionData: SubscriptionData) : State()
        object Empty : State()
        class Error(val message: String) : State()
    }
}