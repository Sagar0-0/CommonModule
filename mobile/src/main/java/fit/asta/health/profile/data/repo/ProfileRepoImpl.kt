package fit.asta.health.profile.data.repo

import android.content.Context
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.network.data.Status
import fit.asta.health.network.utils.InputStreamRequestBody
import fit.asta.health.profile.data.api.ProfileApiService
import fit.asta.health.profile.data.model.domain.UserProfile
import fit.asta.health.profile.data.model.network.NetHealthPropertiesRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException


class ProfileRepoImpl(
    private val context: Context,
    private val remoteApi: ProfileApiService
) : ProfileRepo {

//    override suspend fun getUserProfile(uid: String): Flow<UserProfile> {
//        return flow {
//            emit(remoteApi.getUserProfile(uid).userProfile)
//        }
//    }

    override suspend fun getUserProfile(uid: String): ApiResponse<UserProfile> {
        return try {
            val response = remoteApi.getUserProfile(uid)
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
                        context.contentResolver, userProfile.contact.url.localUrl!!
                    ), filename = userProfile.uid
                )
            )
        }

        return flow {
            emit(remoteApi.updateUserProfile(userProfile, parts))
        }

    }

    override suspend fun isUserProfileAvailable(userId: String): Flow<Boolean> {
        return flow {
            emit(remoteApi.isUserProfileAvailable(userId).flag)
        }
    }

    override suspend fun getHealthProperties(propertyType: String): Flow<NetHealthPropertiesRes> {
        return flow {
            emit(remoteApi.getHealthProperties(propertyType))
        }
    }

    override suspend fun editUserProfile(uid: String): ApiResponse<UserProfile> {
        return try {
            val response = remoteApi.getUserProfile(userId = uid)
            ApiResponse.Success(data = response.userProfile)
        } catch (e: HttpException) {
            ApiResponse.HttpError(code = e.code(), msg = e.message(), ex = e)
        } catch (e: IOException) {
            ApiResponse.Error(exception = e)
        }
    }

}