package fit.asta.health.network.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.network.data.UploadInfo
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody


class RestApi(baseUrl: String, client: OkHttpClient) :
    Api {

    private val apiService: ApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(ApiService::class.java)

    //File upload Endpoints
    override suspend fun uploadFile(
        info: UploadInfo,
        file: MultipartBody.Part
    ): SingleFileUploadRes {
        return apiService.uploadFile(info, file)
    }

    override suspend fun uploadFile(
        info: UploadInfo,
        file: MultipartBody.Part,
        progressCallback: ProgressCallback?
    ): SingleFileUploadRes {
        return apiService.uploadFile(info, file, progressCallback)
    }

    override suspend fun uploadFiles(body: RequestBody?, files: MultipartBody): MultiFileUploadRes {
        return apiService.uploadFiles(body, files)
    }

    override suspend fun uploadFiles(
        body: RequestBody?,
        files: MultipartBody,
        progressCallback: ProgressCallback?
    ): MultiFileUploadRes {
        return apiService.uploadFiles(body, files, progressCallback)
    }

    override suspend fun deleteFile(Id: String, feature: String): Status {
        return apiService.deleteFile(Id, feature)
    }

    override suspend fun deleteFiles(Id: String, feature: String): Status {
        return apiService.deleteFiles(Id, feature)
    }
}