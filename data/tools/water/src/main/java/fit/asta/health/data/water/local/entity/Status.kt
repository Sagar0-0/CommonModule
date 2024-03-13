package fit.asta.health.data.water.local.entity

data class Status(
    val code: Int,
    val err: String,
    val msg: String,
    val retry: Boolean
)