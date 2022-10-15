package fit.asta.health.profile.model

import fit.asta.health.profile.model.domain.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    suspend fun getProfileData(uid: String): Flow<UserProfile>
}