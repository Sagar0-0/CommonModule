package fit.asta.health.data.profile.remote

import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.Response
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.data.profile.remote.model.UserProfileAvailableResponse
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {

    @GET("payment/referral/code/check/?")
    suspend fun checkReferralCode(@Query("refCode") refCode: String): Response<CheckReferralDTO>

    @GET("userProfile/get/isUserProfileAvailable/?")
    suspend fun isUserProfileAvailable(@Query("uid") userId: String): Response<UserProfileAvailableResponse>

    @Multipart
    @POST("userProfile/basic/post")
    suspend fun createBasicProfile(
        @Part("json") basicProfileDTO: BasicProfileDTO,
        @Part files: List<MultipartBody.Part>
    ): Response<PutResponse>

    @PUT("userProfile/put/")
    @Multipart
    suspend fun updateUserProfile(
        @Part("json") userProfileResponse: UserProfileResponse,
        @Part files: List<MultipartBody.Part>,
    ): Response<PutResponse>

    @GET("userProfile/get/?")
    suspend fun getUserProfile(@Query("uid") userId: String): Response<UserProfileResponse>

    @GET("health/property/get/all/?")
    suspend fun getHealthProperties(@Query("property") propertyType: String): Response<ArrayList<HealthProperties>>
}