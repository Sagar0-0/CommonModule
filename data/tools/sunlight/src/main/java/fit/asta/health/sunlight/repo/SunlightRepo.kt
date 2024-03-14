package fit.asta.health.sunlight.repo

import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.sunlight.remote.model.SkinConditionBody
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData

interface SunlightRepo {
     suspend fun getScreenContentList(name:String): ResponseState<ArrayList<SkinConditionResponseData>>
     suspend fun putSkinConditionData(name: SkinConditionBody): ResponseState<PutResponse>

}