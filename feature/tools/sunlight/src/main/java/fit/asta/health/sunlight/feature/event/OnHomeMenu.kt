package fit.asta.health.sunlight.feature.event

import fit.asta.health.common.utils.Prc
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData

sealed interface OnHomeMenu  {
    data object OnSkinConditionEdit : OnHomeMenu
    data object OnHelpAndSuggestion : OnHomeMenu


}