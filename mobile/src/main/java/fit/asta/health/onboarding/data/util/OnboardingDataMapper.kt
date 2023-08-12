package fit.asta.health.onboarding.data.util

import fit.asta.health.onboarding.data.modal.OnboardingData
import fit.asta.health.onboarding.data.remote.modal.OnboardingDTO

class OnboardingDataMapper {

    fun mapToDomainModel(onboardingDTO: OnboardingDTO): List<OnboardingData> {
        return onboardingDTO.data.map {
            mapToDomainModel(it)
        }
    }

    private fun mapToDomainModel(onboardingDTO: OnboardingDTO.Data) =
        OnboardingData(
            onboardingDTO.ttl,
            onboardingDTO.dsc,
            onboardingDTO.url,
            onboardingDTO.type
        )
}