package fit.asta.health.profile.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import fit.asta.health.profile.model.network.NetUserProfileRes
import retrofit2.http.*


//User Profile Endpoints
interface ProfileApiService {

    @GET("userProfile/get/isUserProfileAvailable/?")
    suspend fun isUserProfileAvailable(@Query("uid") userId: String): NetUserProfileAvailableRes

    @PUT("user/profile/put")
    suspend fun updateUserProfile(@Body userProfile: UserProfile): Status

    @GET("userProfile/get/?")
    suspend fun getUserProfile(@Query("uid") userId: String): NetUserProfileRes

    @GET("health/property/get/all/?")
    suspend fun getHealthProperties(@Query("property") propertyType: String): NetHealthPropertiesRes
}