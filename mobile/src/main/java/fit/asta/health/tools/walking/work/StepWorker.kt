package fit.asta.health.tools.walking.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fit.asta.health.tools.walking.db.StepsData
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.model.network.request.Distance
import fit.asta.health.tools.walking.model.network.request.Duration
import fit.asta.health.tools.walking.model.network.request.PutDayData
import fit.asta.health.tools.walking.model.network.request.Steps
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
            if (data == null) return@launch
            else runServerRequest(walkingToolRepo, data, applicationContext)
        }
        return Result.success()
    }

}

private suspend fun runServerRequest(
    walkingToolRepo: WalkingToolRepo,
    data: StepsData,
    applicationContext: Context
) {

    val dayData = createPutDayData(
        bpm = 0f,
        id = "",
        uid = "6383745840efcc8cdeb289e1",
        weightLoose = data.weightLoosed.toFloat(),
        date = "${LocalDate.now()}",
        calories = data.calories.toInt(),
        heartRate = 72f,
        steps = Steps(steps = data.allSteps, unit = "steps"),
        duration = Duration(dur = data.realtime.toFloat(), unit = "mins"),
        distance = Distance(dis = data.allSteps.toFloat() * 1408, unit = "km")
    )
//    val result = walkingToolRepo.putDayData(putDayData = dayData)
    val createNotification = CreateNotification(
        applicationContext,
        " Walking ",
        "Day data is uploaded to server"
    )
    createNotification.showNotification()
}

fun createPutDayData(
    bpm: Float,
    calories: Int,
    date: String,
    distance: Distance,
    duration: Duration,
    heartRate: Float,
    id: String,
    steps: Steps,
    uid: String,
    weightLoose: Float
): PutDayData {
    return PutDayData(
        bpm = bpm,
        calories = calories,
        date = date,
        distance = distance,
        duration = duration,
        heartRate = heartRate,
        id = id,
        steps = steps,
        uid = uid,
        weightLoose = weightLoose
    )
}

