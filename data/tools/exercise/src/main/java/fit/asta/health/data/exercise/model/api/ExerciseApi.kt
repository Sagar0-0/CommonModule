package fit.asta.health.data.exercise.model.api

import fit.asta.health.data.exercise.model.network.NetGetRes
import fit.asta.health.data.exercise.model.network.NetGetStart
import fit.asta.health.data.exercise.model.network.NetPost
import fit.asta.health.data.exercise.model.network.NetPutRes
import fit.asta.health.network.data.ServerRes

interface ExerciseApi {
    suspend fun getExerciseTool(uid: String, date: String,name:String): NetGetRes

    suspend fun getStart(uid: String,name: String): NetGetStart

    suspend fun putExerciseData(netPutRes: NetPutRes,name:String): ServerRes

    suspend fun postExerciseData(netPost: NetPost,name:String): ServerRes
}