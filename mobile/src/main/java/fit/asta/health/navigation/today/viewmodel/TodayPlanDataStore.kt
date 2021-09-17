package fit.asta.health.navigation.today.viewmodel

import fit.asta.health.navigation.today.data.TodayPlanItemData
import java.util.*


interface TodayPlanDataStore {
    fun updatePlan(list: List<TodayPlanItemData>)
    fun getPlan(position: Int): TodayPlanItemData
}