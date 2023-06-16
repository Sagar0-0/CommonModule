package fit.asta.health.tools.meditation.model.domain.model

import fit.asta.health.tools.meditation.model.network.Value

data class MeditationTool(
    val uid :String,
    val target:Int,
    val achieved:Int,
    val remaining:Int,
    val recommended:Int,
    val meta_min:String,
    val meta_max:String,
    val music:Tool,
    val targetTool:Tool,
    val instructor:Tool,
    val level:Tool,
    val language:Tool,
)
data class Tool(
    val title: String,
    val values: List<Value>
)
