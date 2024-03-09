package fit.asta.health.sunlight.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SessionDetailBody
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData
import kotlinx.coroutines.flow.Flow

interface SunlightHomeRepo {
    fun getSunlightHomeData(
        uid: String,
        lat: String,
        lon: String,
        date: Long,
        loc: String,
    ): Flow<ResponseState<SunlightHomeData>>

    fun getSupplementAndFoodInfo(
    ): Flow<ResponseState<HelpAndNutrition>>

    fun getSunlightSessionData(
        data: SessionDetailBody
    ): Flow<ResponseState<SunlightSessionData>>
}