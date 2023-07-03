package fit.asta.health.network.api

import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.network.data.UploadInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface Api {

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