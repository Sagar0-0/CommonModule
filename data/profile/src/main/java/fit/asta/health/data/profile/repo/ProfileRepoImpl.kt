package fit.asta.health.data.profile.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.profile.remote.ProfileApi
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.HealthPropertiesRes
import fit.asta.health.data.profile.remote.model.UserProfile
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.ScreenCode
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.network.data.Status
import fit.asta.health.network.utils.InputStreamRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
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

    override suspend fun isUserProfileAvailable(userId: String): ResponseState<Boolean> {
        return withContext(coroutineDispatcher) {
            getResponseState {
                profileApi.isUserProfileAvailable(userId).flag
            }
        }
    }

    override suspend fun createBasicProfile(basicProfileDTO: BasicProfileDTO): ResponseState<Boolean> {
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
            getResponseState {
                profileApi.createBasicProfile(basicProfileDTO, parts).data.flag
            }
        }
    }

    override suspend fun checkReferralCode(code: String): ResponseState<CheckReferralDTO> {
        return withContext(coroutineDispatcher) {
            getResponseState {
                profileApi.checkReferralCode(code)
            }
        }
    }

    override suspend fun getUserProfile(uid: String): ApiResponse<UserProfile> {
        return try {
            val response = profileApi.getUserProfile(uid)
            ApiResponse.Success(data = response.userProfile)
        } catch (e: HttpException) {
            ApiResponse.HttpError(code = e.code(), msg = e.message(), ex = e)
        } catch (e: IOException) {
            ApiResponse.Error(exception = e)
        }
    }

    override suspend fun updateUserProfile(userProfile: UserProfile): Flow<Status> {

        val parts: ArrayList<MultipartBody.Part> = ArrayList()
        if (userProfile.contact.url.localUrl != null) {
            parts.add(
                MultipartBody.Part.createFormData(
                    name = "file", body = InputStreamRequestBody(
                        contentResolver, userProfile.contact.url.localUrl!!
                    ), filename = userProfile.uid
                )
            )
        }

        return flow {
            emit(profileApi.updateUserProfile(userProfile, parts))
        }
    }

    override suspend fun getHealthProperties(propertyType: String): Flow<HealthPropertiesRes> {
        return flow {
            emit(profileApi.getHealthProperties(propertyType))
        }
    }

    override suspend fun editUserProfile(uid: String): ApiResponse<UserProfile> {
        return try {
            val response = profileApi.getUserProfile(userId = uid)
            ApiResponse.Success(data = response.userProfile)
        } catch (e: HttpException) {
            ApiResponse.HttpError(code = e.code(), msg = e.message(), ex = e)
        } catch (e: IOException) {
            ApiResponse.Error(exception = e)
        }
    }
}