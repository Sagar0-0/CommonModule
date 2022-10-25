package fit.asta.health.profile.model

import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    suspend fun isUserProfileAvailable(userId: String): Flow<NetUserProfileAvailableRes>
    suspend fun getUserProfile(uid: String): Flow<UserProfile>
    suspend fun updateUserProfile(userProfile: UserProfile): Flow<Status>
    suspend fun getHealthProperties(propertyType: String): Flow<NetHealthPropertiesRes>
}