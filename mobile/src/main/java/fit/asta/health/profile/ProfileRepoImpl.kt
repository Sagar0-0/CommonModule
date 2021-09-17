package fit.asta.health.profile

import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ProfileRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: ProfileDataMapper
) : ProfileRepo {
    override suspend fun fetchProfile(userId: String): Flow<ProfileData> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getProfile(userId)))
        }
    }

    override fun updateProfile(
        profileData: ProfileData?,
        userId: String
    ): Flow<String> {
        return flow {
            profileData?.let {
                val data = dataMapper.toMap(userId, it)
                remoteApi.postProfile(data)
                "successfully posted the data"
            }
        }
    }
}