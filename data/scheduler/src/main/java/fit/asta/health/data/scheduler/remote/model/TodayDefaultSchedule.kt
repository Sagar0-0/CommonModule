package fit.asta.health.data.scheduler.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.scheduler.db.entity.AlarmEntity


data class TodayDefaultSchedule(
    @SerializedName("schedule")
    val schedule: List<AlarmEntity> = listOf()
)