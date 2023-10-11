package fit.asta.health.tools.breathing.model.domain.model

import fit.asta.health.common.utils.Prc

data class BreathingTool(
    val id: String,
    val uid: String,
    val weather: Boolean,
    val target: Int,
    val achieved: Int,
    val recommend: Int,
    val bottomSheetPrc: List<Prc> = emptyList()
)
