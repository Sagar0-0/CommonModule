package fit.asta.health.data.profile.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.UserProfileAvailableResponse
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.data.profile.remote.model.UserProperties

interface ProfileRepo {
    suspend fun setProfilePresent()
    suspend fun isBasicProfileAvailable(userId: String): ResponseState<UserProfileAvailableResponse>
    suspend fun createBasicProfile(basicProfileDTO: BasicProfileDTO): ResponseState<SubmitProfileResponse>
    suspend fun checkReferralCode(code: String): ResponseState<CheckReferralDTO>
    suspend fun getUserProfile(uid: String): ResponseState<UserProfileResponse>
    suspend fun getHealthProperties(propertyType: String): ResponseState<List<UserProperties>>

    suspend fun setName(uid: String, name: String): ResponseState<SubmitProfileResponse>
    suspend fun setGenderDetails(
        uid: String,
        gender: Gender?,
        isPregnant: BooleanInt?,
        onPeriod: BooleanInt?,
        pregnancyWeek: Int?
    ): ResponseState<SubmitProfileResponse>

    suspend fun setDob(
        uid: String,
        dob: String,
        age: Int
    ): ResponseState<SubmitProfileResponse>

    suspend fun saveHeight(
        uid: String,
        height: Float,
        unit: Int
    ): ResponseState<SubmitProfileResponse>

    suspend fun saveWeight(
        uid: String,
        weight: Float,
        unit: Int
    ): ResponseState<SubmitProfileResponse>

    suspend fun savePropertiesList(
        uid: String, screenName: String, fieldName: String, list: List<UserProperties>
    ): ResponseState<SubmitProfileResponse>
}