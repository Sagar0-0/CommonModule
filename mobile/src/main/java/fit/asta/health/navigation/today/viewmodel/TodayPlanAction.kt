package fit.asta.health.navigation.today.viewmodel

import fit.asta.health.navigation.today.data.TodayPlanItemData

sealed class TodayPlanAction {

    class LoadPlan(val list: List<TodayPlanItemData>) : TodayPlanAction()
    object Empty : TodayPlanAction()
    class Error(val message: String) : TodayPlanAction()
}