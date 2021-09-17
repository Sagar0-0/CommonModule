package fit.asta.health.navigation.today.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.navigation.today.TodayPlanRepo
import fit.asta.health.navigation.today.data.TodayPlanItemData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodayPlanViewModel(
    private val todayPlanRepo: TodayPlanRepo
) : ViewModel() {

    private val todayLiveData = MutableLiveData<TodayPlanAction>()
    private val dataStore = TodayPlanDataStoreImpl()

    fun fetchPlan(userId: String) {
        viewModelScope.launch {
            todayPlanRepo.fetchTodayPlan(userId).catch {
                e -> e.printStackTrace()
            }
                .collect {
                    updateTodayPlan(it)
                }
        }
    }

    private fun updateTodayPlan(list: List<TodayPlanItemData>) {
        dataStore.updatePlan(list)
        todayLiveData.value = TodayPlanAction.LoadPlan(list)
    }

    fun observeTodayLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: TodayPlanObserver
    ) {
        todayLiveData.observe(lifecycleOwner, observer)
    }

    fun getPlanDetails(position: Int): TodayPlanItemData {
        return dataStore.getPlan(position)
    }
}