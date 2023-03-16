package fit.asta.health.network.api

import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.network.data.UploadInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface RemoteApis {

    //Home page
    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): NetHealthToolsRes

    suspend fun updateSelectedTools(toolIds: NetSelectedTools): Status

    //File upload Endpoints
    suspend fun uploadFile(info: UploadInfo, file: MultipartBody.Part): SingleFileUploadRes

    suspend fun uploadFile(
        info: UploadInfo,
        file: MultipartBody.Part,
        progressCallback: ProgressCallback?
    ): SingleFileUploadRes

    suspend fun uploadFiles(body: RequestBody?, files: MultipartBody): MultiFileUploadRes
    suspend fun uploadFiles(
        body: RequestBody?,
        files: MultipartBody,
        progressCallback: ProgressCallback?
    ): MultiFileUploadRes

    suspend fun deleteFile(Id: String, feature: String): Status
    suspend fun deleteFiles(Id: String, feature: String): Status
}