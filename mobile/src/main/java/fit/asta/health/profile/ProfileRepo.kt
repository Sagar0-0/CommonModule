package fit.asta.health.profile

import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    suspend fun fetchProfile(userId: String): Flow<ProfileData>
    fun updateProfile(profileData: ProfileData?, userId: String): Flow<String>
}