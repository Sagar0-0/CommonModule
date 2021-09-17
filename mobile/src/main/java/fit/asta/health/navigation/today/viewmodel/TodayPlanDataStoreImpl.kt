package fit.asta.health.navigation.today.viewmodel

import fit.asta.health.navigation.today.data.TodayPlanItemData

class TodayPlanDataStoreImpl: TodayPlanDataStore {
    private var planItemData: List<TodayPlanItemData> = listOf()

    override fun updatePlan(list: List<TodayPlanItemData>) {
        planItemData = list
    }

    override fun getPlan(position: Int): TodayPlanItemData {
        return planItemData[position]
    }
}