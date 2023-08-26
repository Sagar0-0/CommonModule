package fit.asta.health.data.profile.remote


import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.BasicProfileResponse
import fit.asta.health.data.profile.remote.model.ProfileAvailableStatus
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BasicProfileApi {
    @GET("userProfile/get/isUserProfileAvailable/?")
    suspend fun isUserProfileAvailable(@Query("uid") userId: String): ProfileAvailableStatus

    @POST("userProfile/basic/post/")
    suspend fun createBasicProfile(@Body basicProfileDTO: BasicProfileDTO): BasicProfileResponse

}