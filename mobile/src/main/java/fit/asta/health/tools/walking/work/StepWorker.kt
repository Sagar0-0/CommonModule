package fit.asta.health.tools.walking.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.tools.walking.db.StepsData
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.notification.CreateNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltWorker
class StepWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val walkingToolRepo: WalkingToolRepo,
    private val localRepo: LocalRepo
) : Worker(context, params) {
    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            val data = localRepo.getStepsData(LocalDate.now().dayOfMonth)
            if (data==null) return@launch
            else runServerRequest(walkingToolRepo,data,applicationContext)
        }
        return Result.success()
    }

}
private suspend fun runServerRequest(
    walkingToolRepo: WalkingToolRepo,
    data: StepsData,
    applicationContext: Context
){
    walkingToolRepo.getHomeData(userId = "6309a9379af54f142c65fbfe")
        .collect {
            when (it) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    if ( it.data == null) { return@collect}
                    else {
                        val steps = it.data.stepsTarget
                        if (data.status == "active" && steps >= data.allSteps) {
                            val createNotification = CreateNotification(
                                applicationContext,
                                " Walking ",
                                "Today your steps progress is ${(data.allSteps / steps) * 100} % completed"
                            )
                            createNotification.showNotification()
                        }
                    }
                }
                is NetworkResult.Error -> {}
                else -> {}
            }
        }
}
