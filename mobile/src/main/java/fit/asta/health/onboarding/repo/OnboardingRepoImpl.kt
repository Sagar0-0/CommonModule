package fit.asta.health.onboarding.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.api.OnboardingApi
import fit.asta.health.onboarding.modal.OnboardingData
import fit.asta.health.onboarding.modal.OnboardingDataMapper
import retrofit2.HttpException
import java.io.IOException

class OnboardingRepoImpl(
    private val remoteApi: OnboardingApi,
    private val mapper: OnboardingDataMapper,
) : OnboardingRepo {
    override suspend fun getData(): ResponseState<List<OnboardingData>> {
        return try {
            val response = remoteApi.getData()
            ResponseState.Success(data = mapper.mapToDomainModel(response))
        } catch (e: HttpException) {
            ResponseState.NoInternet
        } catch (e: IOException) {
            ResponseState.Error(e)
        }
    }
}