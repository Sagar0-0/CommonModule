package fit.asta.health.sunlight.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SessionDetailBody
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData

interface SunlightHomeRepo {
   suspend fun getSunlightHomeData(
        uid: String,
        lat: String,
        lon: String,
        date: Long,
        loc: String,
    ):ResponseState<SunlightHomeData>

    suspend fun getSupplementAndFoodInfo(
    ): ResponseState<HelpAndNutrition>

    suspend fun getSunlightSessionData(
        data: SessionDetailBody
    ): ResponseState<SunlightSessionData>
}