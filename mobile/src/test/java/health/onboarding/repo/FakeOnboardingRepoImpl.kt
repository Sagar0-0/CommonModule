package fit.asta.health.onboarding.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.modal.OnboardingData

class FakeOnboardingRepoImpl : OnboardingRepo {
    private var error = false
    fun setError(value: Boolean) {
        error = value
    }

    private val list = listOf(
        OnboardingData(
            title = "First",
            desc = "Desc of first",
            imgUrl = "",
            type = 1
        ),
        OnboardingData(
            title = "First",
            desc = "Desc of first",
            imgUrl = "",
            type = 1
        ),
        OnboardingData(
            title = "First",
            desc = "Desc of first",
            imgUrl = "",
            type = 1
        ),
        OnboardingData(
            title = "First",
            desc = "Desc of first",
            imgUrl = "",
            type = 1
        )
    )

    override suspend fun getData(): ResponseState<List<OnboardingData>> {
        return if (error) {
            ResponseState.Error(Exception())
        } else {
            ResponseState.Success(list)
        }
    }
}