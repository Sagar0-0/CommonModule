package fit.asta.health.tools.breathing.model.domain.model

data class BreathingTool(
    val id :String,
    val uid :String,
    val weather:Boolean,
    val target: Int,
    val achieved: Int,
    val recommend: Int,
    val exercise:List<String>,
    val Language:String,
    val Break:String,
    val Goal:List<String>,
    val Target:String,
    val Pace:String,
    val Level:String,
    val instructor:String,
    val Music:String,
)
