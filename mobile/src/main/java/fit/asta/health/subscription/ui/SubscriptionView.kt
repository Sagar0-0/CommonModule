package fit.asta.health.subscription.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import fit.asta.health.subscription.data.SubscriptionPlanData
import fit.asta.health.subscription.listner.SubPlanSelectionListener

interface SubscriptionView {

    fun setContentView(activity: Activity, container: ViewGroup?): View?
    fun changeState(state: State)
    fun setAdapterListener(listener: SubPlanSelectionListener)
    fun setUpViewPager(fragment: Fragment)
    fun registerAutoScroll(lifecycleScope: LifecycleCoroutineScope)
    fun privacyClickListener(listener: View.OnClickListener)
    fun termsClickListener(listener: View.OnClickListener)

    sealed class State {
        class LoadSubscriptionPlan(val subPlanData: SubscriptionPlanData) : State()
        object Empty : State()
        class Error(val message: String) : State()
    }
}