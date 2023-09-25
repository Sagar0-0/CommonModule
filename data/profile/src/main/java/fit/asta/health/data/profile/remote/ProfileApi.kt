package fit.asta.health.data.profile.remote

import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.BasicProfileResponse
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.HealthPropertiesRes
import fit.asta.health.data.profile.remote.model.UserProfile
import fit.asta.health.data.profile.remote.model.UserProfileAvailable
import fit.asta.health.data.profile.remote.model.UserProfileRes
import fit.asta.health.network.data.Status
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {

    @GET("payment/referral/code/check/?")
    suspend fun checkReferralCode(@Query("refCode") refCode: String): CheckReferralDTO

    @GET("userProfile/get/isUserProfileAvailable/?")
    suspend fun isUserProfileAvailable(@Query("uid") userId: String): UserProfileAvailable

    @Multipart
    @POST("userProfile/basic/post")
    suspend fun createBasicProfile(
        @Part("json") basicProfileDTO: BasicProfileDTO,
        @Part files: List<MultipartBody.Part>
    ): BasicProfileResponse

    @PUT("userProfile/put/")
    @Multipart
    suspend fun updateUserProfile(
        @Part("json") userProfile: UserProfile,
        @Part files: List<MultipartBody.Part>,
    ): Status

    @GET("userProfile/get/?")
    suspend fun getUserProfile(@Query("uid") userId: String): UserProfileRes

    @GET("health/property/get/all/?")
    suspend fun getHealthProperties(@Query("property") propertyType: String): HealthPropertiesRes
}