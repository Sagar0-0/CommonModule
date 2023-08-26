package fit.asta.health.data.profile.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.profile.remote.model.BasicProfileDTO

interface ProfileRepo {
    suspend fun isProfileAvailable(userId: String): ResponseState<Boolean>
    suspend fun createBasicProfile(basicProfileDTO: BasicProfileDTO): ResponseState<Boolean>
}