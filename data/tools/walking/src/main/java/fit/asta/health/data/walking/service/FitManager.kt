package fit.asta.health.data.walking.service

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject


data class DailyFitnessModel(
    val stepCount: Int,
    val caloriesBurned: Int,
    val distance: Float
)

data class WeeklyFitnessModel(
    val dailyFitnessList: List<DailyFitnessModel>
)


class FitManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    fun getDailyFitnessData() = callbackFlow {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.timeInMillis

        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .aggregate(DataType.AGGREGATE_CALORIES_EXPENDED)
            .aggregate(DataType.AGGREGATE_DISTANCE_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .build()

        Fitness.getHistoryClient(context, getGoogleAccount(context))
            .readData(readRequest)
            .addOnSuccessListener { data ->
                val buckets = data.buckets
                val bucket = if (buckets.isNotEmpty()) buckets[0] else null
                var stepCount = 0
                var calories = 0
                var distance = 0.0f
                bucket?.dataSets?.forEach { dataSet ->
                    dataSet.dataPoints.forEach { dataPoint ->
                        when (dataPoint.dataType) {
                            DataType.TYPE_STEP_COUNT_DELTA -> {
                                stepCount = dataPoint.getValue(Field.FIELD_STEPS).asInt()
                                Toast.makeText(context, "step $stepCount", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            DataType.TYPE_CALORIES_EXPENDED -> {
                                calories = dataPoint.getValue(Field.FIELD_CALORIES).asFloat()
                                    .toInt() / 1000
                            }

                            DataType.TYPE_DISTANCE_DELTA -> {
                                distance = dataPoint.getValue(Field.FIELD_DISTANCE).asFloat() / 1000
                            }
                        }
                    }
                }

                trySend(
                    DailyFitnessModel(
                        stepCount,
                        calories,
                        distance
                    )
                )
            }
            .addOnFailureListener { exception ->
                close(exception) // Close the flow on failure
            }
        awaitClose {
            Toast.makeText(context, "step close", Toast.LENGTH_SHORT).show()
            close()
        }
    }

    fun getWeeklyFitnessData(context: Context) = callbackFlow {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val startTime = calendar.timeInMillis

        // Build data read request for the last 7 days
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .aggregate(DataType.AGGREGATE_CALORIES_EXPENDED)
            .aggregate(DataType.AGGREGATE_DISTANCE_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .build()

        Fitness.getHistoryClient(context, getGoogleAccount(context))
            .readData(readRequest)
            .addOnSuccessListener { data ->
                val buckets = data.buckets
                val dailyFitnessList = mutableListOf<DailyFitnessModel>()

                // Process each bucket of fitness data
                buckets.forEach { bucket ->
                    var stepCount = 0
                    var calories = 0
                    var distance = 0.0f

                    bucket.dataSets.forEach { dataSet ->
                        dataSet.dataPoints.forEach { dataPoint ->
                            when (dataPoint.dataType) {
                                DataType.TYPE_STEP_COUNT_DELTA -> {
                                    stepCount = dataPoint.getValue(Field.FIELD_STEPS).asInt()
                                }

                                DataType.TYPE_CALORIES_EXPENDED -> {
                                    calories = dataPoint.getValue(Field.FIELD_CALORIES).asFloat()
                                        .toInt() / 1000
                                }

                                DataType.TYPE_DISTANCE_DELTA -> {
                                    distance =
                                        dataPoint.getValue(Field.FIELD_DISTANCE).asFloat() / 1000
                                }
                            }
                        }
                    }

                    val dailyFitness = DailyFitnessModel(
                        stepCount,
                        calories,
                        distance
                    )
                    dailyFitnessList.add(dailyFitness)
                }
                trySend(WeeklyFitnessModel(dailyFitnessList))
            }
            .addOnFailureListener { exception ->
                close(exception) // Close the flow on failure
            }
        awaitClose { close() }
    }

    fun getGoogleAccount(context: Context): GoogleSignInAccount =
        GoogleSignIn.getAccountForExtension(context, fitnessOptions)

    val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1

    fun requestPermissions() {
        GoogleSignIn.requestPermissions(
            context as Activity, // your activity
            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
            getGoogleAccount(context),
            fitnessOptions
        )
    }

    fun checkFitPermission() =
        GoogleSignIn.hasPermissions(getGoogleAccount(context), fitnessOptions)

}