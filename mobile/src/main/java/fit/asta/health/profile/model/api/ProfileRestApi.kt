package fit.asta.health.profile.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import fit.asta.health.profile.model.network.NetUserProfileRes
import fit.asta.health.utils.NetworkUtil
import okhttp3.OkHttpClient


//User Profile Endpoints
class ProfileRestApi(baseUrl: String, client: OkHttpClient) : ProfileApi {

    private val apiService: ProfileApiService = NetworkUtil
        .getRetrofit(baseUrl = baseUrl, client = client)
        .create(ProfileApiService::class.java)

    override suspend fun isUserProfileAvailable(userId: String): NetUserProfileAvailableRes {
        return apiService.isUserProfileAvailable(userId)
    }

    override suspend fun updateUserProfile(userProfile: UserProfile): Status {
        return apiService.updateUserProfile(userProfile)
    }

    override suspend fun getUserProfile(userId: String): NetUserProfileRes {
        return apiService.getUserProfile(userId)
    }

    override suspend fun getHealthProperties(propertyType: String): NetHealthPropertiesRes {
        return apiService.getHealthProperties(propertyType)
    }
}