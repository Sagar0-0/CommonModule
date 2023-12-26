package fit.asta.health.meditation.domain.model

import fit.asta.health.common.utils.Prc

data class MeditationTool(
    val uid: String = "",
    val target: Int = 0,
    val achieved: Int = 0,
    val remaining: Int = 0,
    val recommended: Int = 0,
    val metaMin: String = "",
    val metaMax: String = "",
    val bottomSheetPrc: List<Prc> = emptyList()
)
