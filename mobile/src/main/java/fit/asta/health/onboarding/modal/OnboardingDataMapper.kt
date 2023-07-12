package fit.asta.health.onboarding.modal

class OnboardingDataMapper {

    fun mapToDomainModel(onboardingResponse: OnboardingResponse): List<OnboardingData> {
        return onboardingResponse.data.map {
            mapToDomainModel(it)
        }
    }

    private fun mapToDomainModel(onboardingResponse: OnboardingResponse.Data) =
        OnboardingData(
            onboardingResponse.ttl,
            onboardingResponse.dsc,
            onboardingResponse.url,
            onboardingResponse.type
        )
}