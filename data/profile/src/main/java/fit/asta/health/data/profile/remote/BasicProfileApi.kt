package fit.asta.health.data.profile.remote


import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.BasicProfileResponse
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.ProfileAvailableStatus
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface BasicProfileApi {

    @GET("payment/referral/code/check/?")
    suspend fun checkReferralCode(@Query("refCode") refCode: String): CheckReferralDTO


    @GET("userProfile/get/isUserProfileAvailable/?")
    suspend fun isUserProfileAvailable(@Query("uid") userId: String): ProfileAvailableStatus

    @Multipart
    @POST("userProfile/basic/post")
    suspend fun createBasicProfile(
        @Part("json") basicProfileDTO: BasicProfileDTO,
        @Part files: List<MultipartBody.Part>
    ): BasicProfileResponse

}