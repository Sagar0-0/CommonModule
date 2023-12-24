package fit.asta.health.data.breathing.model.domain.model

data class Exercise(
    val name: String,
    val duration: String,
    val ratio: Ratio,
    val isSelected: Boolean
)

data class Ratio(
    val inhale: Int, val inHold: Int, val exhale: Int, val exHold: Int
)

fun Ratio.toStr(): String {
    return "$inhale:$inHold:$exhale:$exHold"
}
