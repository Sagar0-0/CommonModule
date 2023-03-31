package fit.asta.health.network.api

import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.network.data.UploadInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {

    //Home page
    @GET("home/get?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("start") startDate: String,
        @Query("end") endDate: String,
        @Query("time") time: String
    ): NetHealthToolsRes

    @PUT("tool/selected/put")
    suspend fun updateSelectedTools(@Body toolIds: NetSelectedTools): Status

    //File upload Endpoints ------------------------------------------------------------------------
    @Multipart
    @PUT("file/upload/put/")
    suspend fun uploadFile(
        @Part("json") info: UploadInfo,
        @Part file: MultipartBody.Part
    ): SingleFileUploadRes

    @Multipart
    @PUT("file/upload/put/")
    suspend fun uploadFile(
        @Part("json") info: UploadInfo,
        @Part file: MultipartBody.Part,
        @Tag progressCallback: ProgressCallback?
    ): SingleFileUploadRes

    @Multipart
    @PUT("file/upload/healthHisList/put/")
    suspend fun uploadFiles(
        @Body body: RequestBody?,
        @Part file: MultipartBody
    ): MultiFileUploadRes

    @Multipart
    @PUT("file/upload/healthHisList/put/")
    suspend fun uploadFiles(
        @Body body: RequestBody?,
        @Part file: MultipartBody,
        @Tag progressCallback: ProgressCallback?
    ): MultiFileUploadRes

    @DELETE("file/upload/delete/")
    suspend fun deleteFile(@Query("uid") Id: String, @Query("feature") feature: String): Status

    @DELETE("file/upload/healthHisList/delete/")
    suspend fun deleteFiles(@Query("uid") Id: String, @Query("feature") feature: String): Status
}
