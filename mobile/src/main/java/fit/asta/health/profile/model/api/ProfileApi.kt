package fit.asta.health.profile.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import fit.asta.health.profile.model.network.NetUserProfileRes


//User Profile Endpoints
interface ProfileApi {
    suspend fun isUserProfileAvailable(userId: String): NetUserProfileAvailableRes
    suspend fun updateUserProfile(userProfile: UserProfile): Status
    suspend fun getUserProfile(userId: String): NetUserProfileRes
    suspend fun getHealthProperties(propertyType: String): NetHealthPropertiesRes
}