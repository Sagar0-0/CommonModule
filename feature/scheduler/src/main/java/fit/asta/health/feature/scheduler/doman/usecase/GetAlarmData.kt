package fit.asta.health.feature.scheduler.doman.usecase

import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import javax.inject.Inject

class GetAlarmData @Inject constructor(
    private val backendRepo: AlarmBackendRepo
) {
    suspend operator fun invoke(scheduleId:String) {
       val data= backendRepo.getScheduleDataFromBackend(scheduleId)
    }
}