package fit.asta.health.data.scheduler.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.network.data.Status

data class TodayDefaultSchedule(
    @SerializedName("data")
    val `data`: DefaultSchedule = DefaultSchedule(),
    @SerializedName("status")
    val status: Status = Status()
)

data class DefaultSchedule(
    @SerializedName("schedule")
    val schedule: List<AlarmEntity> = listOf()
)