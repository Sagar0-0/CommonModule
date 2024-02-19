package fit.asta.health.sunlight.repo

import com.hoho.assignmentsun.model.SunDetailsResponseDTO
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.sunlight.remote.model.SkinConditionBody
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import kotlinx.coroutines.flow.Flow

interface SunlightRepo {
     fun getSunData(
        date: String,
        lat: Double,
        lang: Double
    ): Flow<ResponseState<SunDetailsResponseDTO>>

    /*suspend fun getSunData(
        @Query("date") date: String,
        @Query("latitude") lat: Double,
        @Query("longitude") lang: Double
    ): Response<SunDetailsResponseDTO>*/

     fun getScreenContentList(name:String): Flow<ResponseState<ArrayList<SkinConditionResponseData>>>
     fun putSkinConditionData(name: SkinConditionBody): Flow<ResponseState<PutResponse>>

}