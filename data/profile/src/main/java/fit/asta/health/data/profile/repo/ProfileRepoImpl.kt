package fit.asta.health.data.profile.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.profile.remote.ProfileApi
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.data.profile.remote.model.UserProfileAvailableResponse
import fit.asta.health.data.profile.remote.model.UserProfileResponse
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

    override suspend fun isUserProfileAvailable(userId: String): ResponseState<UserProfileAvailableResponse> {
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

    override suspend fun updateUserProfile(userProfileResponse: UserProfileResponse): ResponseState<SubmitProfileResponse> {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()
        if (userProfileResponse.contact.url.localUrl != null) {
            parts.add(
                MultipartBody.Part.createFormData(
                    name = "file", body = InputStreamRequestBody(
                        contentResolver, userProfileResponse.contact.url.localUrl!!
                    ), filename = userProfileResponse.uid
                )
            )
        }

        return withContext(coroutineDispatcher) {
            getApiResponseState {
                profileApi.updateUserProfile(userProfileResponse, parts)
            }
        }
    }

    override suspend fun getHealthProperties(propertyType: String): ResponseState<ArrayList<HealthProperties>> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                profileApi.getHealthProperties(propertyType)
            }
        }
    }

    override suspend fun editUserProfile(uid: String): ResponseState<UserProfileResponse> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                profileApi.getUserProfile(uid)
            }
        }
    }
}