package fit.asta.health.data.water.db.dbmodel

data class Status(
    val code: Int,
    val err: String,
    val msg: String,
    val retry: Boolean
)