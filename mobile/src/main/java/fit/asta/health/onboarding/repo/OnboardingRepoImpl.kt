package fit.asta.health.onboarding.repo

import android.content.Context
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.onboarding.api.OnboardingApi
import fit.asta.health.onboarding.modal.OnboardingData
import fit.asta.health.onboarding.modal.OnboardingDataMapper
import retrofit2.HttpException
import java.io.IOException

class OnboardingRepoImpl(
    private val context: Context,
    private val remoteApi: OnboardingApi,
    private val mapper: OnboardingDataMapper,
) : OnboardingRepo {
    override suspend fun getData(): ApiResponse<List<OnboardingData>> {
        return try {
            val response = remoteApi.getData()
            ApiResponse.Success(data = mapper.mapToDomainModel(response))
        } catch (e: HttpException) {
            ApiResponse.HttpError(code = e.code(), msg = e.message(), ex = e)
        } catch (e: IOException) {
            ApiResponse.Error(exception = e)
        }
    }

}