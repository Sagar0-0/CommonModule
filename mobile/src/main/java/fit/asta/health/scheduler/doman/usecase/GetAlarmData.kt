package fit.asta.health.scheduler.doman.usecase

import fit.asta.health.scheduler.data.repo.AlarmBackendRepo
import javax.inject.Inject

class GetAlarmData @Inject constructor(
    private val backendRepo: AlarmBackendRepo
) {
    suspend operator fun invoke(scheduleId:String) {
       val data= backendRepo.getScheduleDataFromBackend(scheduleId)
    }
}