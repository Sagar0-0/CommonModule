package fit.asta.health.tools.walking.core.domain.model


data class WalkingTool(
    val distanceRecommend: Double=0.0,
    val durationRecommend: Int=0,
    val stepsRecommend: Int=0,

    val code: Int=0,
    val id: String="0",
    val name: String="",

    val titleMode: String="",
    val valuesMode: String="",
    val titleMusic: String="",
    val valuesMusic: String="",
    val titleGoal: String="",
    val valuesGoal: List<String> = listOf("running"),
    val titleType: String="",
    val valuesType: String="",
    val sType: Int=0,
    val distanceTarget: Double=0.0,
    val durationTarget: Int=0,
    val stepsTarget: Int=0,
    val uid: String="",

    )

