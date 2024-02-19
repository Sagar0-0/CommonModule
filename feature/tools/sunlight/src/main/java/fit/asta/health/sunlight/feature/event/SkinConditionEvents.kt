package fit.asta.health.sunlight.feature.event

import fit.asta.health.common.utils.Prc
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData

sealed interface SkinConditionEvents  {
    data object OnSkinExposure : SkinConditionEvents
    data object OnSkinColor : SkinConditionEvents
    data object OnSkinType : SkinConditionEvents
    data object OnSPF : SkinConditionEvents
    data object OnSupplements : SkinConditionEvents
    data class OnDataUpdate(val id:Int,val data: Prc):SkinConditionEvents

}