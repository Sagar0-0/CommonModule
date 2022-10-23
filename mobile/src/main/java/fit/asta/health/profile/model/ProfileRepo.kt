package fit.asta.health.profile.model

import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    suspend fun getUserProfile(uid: String): Flow<UserProfile>
    suspend fun updateUserProfile(userProfile: UserProfile): Flow<Status>
}