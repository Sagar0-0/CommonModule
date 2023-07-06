package fit.asta.health.tools.exercise.model.api

import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.exercise.model.network.NetGetRes
import fit.asta.health.tools.exercise.model.network.NetGetStart
import fit.asta.health.tools.exercise.model.network.NetPost
import fit.asta.health.tools.exercise.model.network.NetPutRes

interface ExerciseApi {
    suspend fun getExerciseTool(uid: String, date: String,name:String): NetGetRes

    suspend fun getStart(uid: String): NetGetStart

    suspend fun putExerciseData(netPutRes: NetPutRes,name:String): ServerRes

    suspend fun postExerciseData(netPost: NetPost,name:String): ServerRes
}