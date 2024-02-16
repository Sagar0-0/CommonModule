package fit.asta.health.data.profile.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.data.profile.remote.model.UserProfileAvailableResponse
import fit.asta.health.data.profile.remote.model.UserProfileResponse

interface ProfileRepo {
    suspend fun setProfilePresent()
    suspend fun isUserProfileAvailable(userId: String): ResponseState<UserProfileAvailableResponse>
    suspend fun createBasicProfile(basicProfileDTO: BasicProfileDTO): ResponseState<SubmitProfileResponse>
    suspend fun checkReferralCode(code: String): ResponseState<CheckReferralDTO>

    suspend fun getUserProfile(uid: String): ResponseState<UserProfileResponse>
    suspend fun updateUserProfile(userProfileResponse: UserProfileResponse): ResponseState<SubmitProfileResponse>
    suspend fun getHealthProperties(propertyType: String): ResponseState<List<HealthProperties>>
    suspend fun editUserProfile(uid: String): ResponseState<UserProfileResponse>
}