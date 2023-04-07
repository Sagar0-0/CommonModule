package fit.asta.health.profile.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import fit.asta.health.profile.model.network.NetUserProfileRes
import okhttp3.MultipartBody


//User Profile Endpoints
interface ProfileApi {

    suspend fun isUserProfileAvailable(userId: String): NetUserProfileAvailableRes
    suspend fun updateUserProfile(
        userProfile: UserProfile,
        files: List<MultipartBody.Part>,
    ): Status // create,update,or edit

    suspend fun getUserProfile(userId: String): NetUserProfileRes
    suspend fun getHealthProperties(propertyType: String): NetHealthPropertiesRes

}