package fit.asta.health.sunlight.feature.screens.skin_conditions.util

import fit.asta.health.common.utils.PutResponse
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData


data class SkinConditionState(
    val isLoading: Boolean = false,
    val skinConditionResponse: ArrayList<SkinConditionResponseData>? = null,
    val error: String? = null,
    val putResp: PutResponse? = null,
    val supplementData:String?=null
)
