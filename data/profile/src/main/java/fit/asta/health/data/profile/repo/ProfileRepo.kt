package fit.asta.health.data.profile.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.HealthPropertiesRes
import fit.asta.health.data.profile.remote.model.UserProfile
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    suspend fun setProfilePresent()
    suspend fun isUserProfileAvailable(userId: String): ResponseState<Boolean>
    suspend fun createBasicProfile(basicProfileDTO: BasicProfileDTO): ResponseState<Boolean>
    suspend fun checkReferralCode(code: String): ResponseState<CheckReferralDTO>

    suspend fun getUserProfile(uid: String): ApiResponse<UserProfile>
    suspend fun updateUserProfile(userProfile: UserProfile): Flow<Status>
    suspend fun getHealthProperties(propertyType: String): Flow<HealthPropertiesRes>
    suspend fun editUserProfile(uid: String): ApiResponse<UserProfile>
}