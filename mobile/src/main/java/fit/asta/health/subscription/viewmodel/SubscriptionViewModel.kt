package fit.asta.health.subscription.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.subscription.SubscriptionPagerAction
import fit.asta.health.subscription.SubscriptionPagerObserver
import fit.asta.health.subscription.SubscriptionRepo
import fit.asta.health.subscription.data.SubscriptionData
import fit.asta.health.subscription.data.SubscriptionItemType
import fit.asta.health.subscription.data.SubscriptionPlanData
import fit.asta.health.subscription.ui.SubscriptionAction
import fit.asta.health.subscription.ui.SubscriptionObserver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SubscriptionViewModel(private val subscriptionRepo: SubscriptionRepo) : ViewModel() {

    private val subHeaderLiveData = MutableLiveData<SubscriptionPagerAction>()
    private val subPlansLiveData: MutableMap<String, MutableLiveData<SubscriptionAction>> =
        mutableMapOf()
    private var subscriptionList: List<SubscriptionPlanData> = mutableListOf()

    fun fetchSubscriptionPlans() {
        viewModelScope.launch {
            subscriptionRepo.fetchSubscriptionPlans().collect {
                updateSubscriptionData(it)
            }
        }
    }

    private fun updateSubscriptionData(subData: SubscriptionData) {

        subHeaderLiveData.value = SubscriptionPagerAction.LoadSubscriptionPagerAction(subData)
        subscriptionList = subData.plans
    }

    fun observeSubscriptionHeaderLiveData(
        lifecycleOwner: LifecycleOwner,
        pagePagerObserver: SubscriptionPagerObserver
    ) {
        subHeaderLiveData.observe(lifecycleOwner, pagePagerObserver)
    }

    fun observeSubscriptionLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SubscriptionObserver,
        tabName: String
    ) {
        subPlansLiveData[tabName] = MutableLiveData()
        subPlansLiveData[tabName]?.observe(lifecycleOwner, observer)
    }

    fun updateTab(position: Int) {
        subPlansLiveData[subscriptionList[position].title]?.value =
            SubscriptionAction.LoadSubscriptionPlanAction(subscriptionList[position])
    }

    fun updateSelection(uid: String) {
        subscriptionList.forEach { sub ->
            sub.subscriptions.forEach { plan ->
                plan.selected = if (plan.uid == uid) {
                    SubscriptionItemType.SelectedSubscription
                } else {
                    SubscriptionItemType.UnselectedSubscription
                }
            }
        }
    }
}