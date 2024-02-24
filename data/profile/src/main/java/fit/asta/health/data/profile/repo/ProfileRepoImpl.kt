package fit.asta.health.data.profile.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.profile.remote.ProfileApi
import fit.asta.health.data.profile.remote.model.Age_Field_Name
import fit.asta.health.data.profile.remote.model.BasicDetail_Screen_Name
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.DOB_Field_Name
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.Gender_Field_Name
import fit.asta.health.data.profile.remote.model.IsPregnant_Field_Name
import fit.asta.health.data.profile.remote.model.Name_Field_Name
import fit.asta.health.data.profile.remote.model.OnPeriod_Field_Name
import fit.asta.health.data.profile.remote.model.PregWeek_Field_Name
import fit.asta.health.data.profile.remote.model.UpdateObjectInt
import fit.asta.health.data.profile.remote.model.UpdateObjectString
import fit.asta.health.data.profile.remote.model.UpdateProfileRequest
import fit.asta.health.data.profile.remote.model.UserProfileAvailableResponse
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.ScreenCode
import fit.asta.health.network.utils.InputStreamRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepoImpl
@Inject constructor(
    private val profileApi: ProfileApi,
    private val prefManager: PrefManager,
    private val contentResolver: ContentResolver,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProfileRepo {
    override suspend fun setProfilePresent() {
        prefManager.setScreenCode(ScreenCode.Home.code)
    }

    override suspend fun isBasicProfileAvailable(userId: String): ResponseState<UserProfileAvailableResponse> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                profileApi.isUserProfileAvailable(userId)
            }
        }
    }

    override suspend fun createBasicProfile(basicProfileDTO: BasicProfileDTO): ResponseState<SubmitProfileResponse> {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()
        if (basicProfileDTO.imageLocalUri != null) {
            parts.add(
                MultipartBody.Part.createFormData(
                    name = "file",
                    filename = null,
                    body = InputStreamRequestBody(contentResolver, basicProfileDTO.imageLocalUri)
                )
            )
        }
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                profileApi.createBasicProfile(basicProfileDTO, parts)
            }
        }
    }

    override suspend fun checkReferralCode(code: String) = withContext(coroutineDispatcher) {
        getApiResponseState {
            profileApi.checkReferralCode(code)
        }
    }

    override suspend fun getUserProfile(uid: String): ResponseState<UserProfileResponse> =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                profileApi.getUserProfile(uid)
            }
        }

//    override suspend fun updateLocalProfile(profileEntity: ProfileEntity) =
//        profileDao.updateProfile(profileEntity)

//    override suspend fun getLocalProfile(): ProfileEntity? = profileDao.getProfile()

//    override suspend fun updateUserProfile(userProfileResponse: UserProfileResponse): ResponseState<SubmitProfileResponse> {
//        val parts: ArrayList<MultipartBody.Part> = ArrayList()
//        if (userProfileResponse.basicDetail.media.localUri != null) {
//            parts.add(
//                MultipartBody.Part.createFormData(
//                    name = "file", body = InputStreamRequestBody(
//                        contentResolver, userProfileResponse.basicDetail.media.localUri!!
//                    ), filename = userProfileResponse.uid
//                )
//            )
//        }
//
//        return withContext(coroutineDispatcher) {
//            getApiResponseState {
//                profileApi.updateUserProfile(userProfileResponse, parts)
//            }
//        }
//    }

    override suspend fun getHealthProperties(propertyType: String): ResponseState<List<UserProperties>> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                profileApi.getHealthProperties(propertyType)
            }
        }
    }

    override suspend fun setName(
        uid: String,
        name: String
    ): ResponseState<SubmitProfileResponse> {
        return getApiResponseState {
            profileApi.updateUserProfile(
                updateProfileRequest = UpdateProfileRequest(
                    uid = uid,
                    list = listOf(
                        UpdateObjectString(
                            screenName = BasicDetail_Screen_Name,
                            fieldName = Name_Field_Name,
                            value = name
                        )
                    )
                )
            )
        }
    }

    override suspend fun setGenderDetails(
        uid: String,
        gender: Gender?,
        isPregnant: BooleanInt?,
        onPeriod: BooleanInt?,
        pregnancyWeek: Int?
    ): ResponseState<SubmitProfileResponse> {
        return getApiResponseState {
            profileApi.updateUserProfile(
                updateProfileRequest = UpdateProfileRequest(
                    uid = uid,
                    list = listOf(
                        UpdateObjectInt(
                            screenName = BasicDetail_Screen_Name,
                            fieldName = Gender_Field_Name,
                            value = gender
                        ),
                        UpdateObjectInt(
                            screenName = BasicDetail_Screen_Name,
                            fieldName = IsPregnant_Field_Name,
                            value = isPregnant
                        ),
                        UpdateObjectInt(
                            screenName = BasicDetail_Screen_Name,
                            fieldName = OnPeriod_Field_Name,
                            value = onPeriod
                        ),
                        UpdateObjectInt(
                            screenName = BasicDetail_Screen_Name,
                            fieldName = PregWeek_Field_Name,
                            value = pregnancyWeek
                        ),
                    )
                )
            )
        }
    }

    override suspend fun setDob(
        uid: String,
        dob: String,
        age: Int
    ): ResponseState<SubmitProfileResponse> {
        return getApiResponseState {
            profileApi.updateUserProfile(
                updateProfileRequest = UpdateProfileRequest(
                    uid = uid,
                    list = listOf(
                        UpdateObjectString(
                            screenName = BasicDetail_Screen_Name,
                            fieldName = DOB_Field_Name,
                            value = dob
                        ),
                        UpdateObjectInt(
                            screenName = BasicDetail_Screen_Name,
                            fieldName = Age_Field_Name,
                            value = age
                        )
                    )
                )
            )
        }
    }
}