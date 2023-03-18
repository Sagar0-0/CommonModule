package fit.asta.health.network.api

import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.network.data.UploadInfo
import fit.asta.health.common.utils.NetworkUtil
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody


class RestApi(baseUrl: String, client: OkHttpClient) :
    RemoteApis {

    private val apiService: ApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(ApiService::class.java)

    //Home page
    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): NetHealthToolsRes {
        return apiService.getHomeData(
            userId,
            latitude,
            longitude,
            location,
            startDate,
            endDate,
            time
        )
    }

    override suspend fun updateSelectedTools(toolIds: NetSelectedTools): Status {
        return apiService.updateSelectedTools(toolIds)
    }

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