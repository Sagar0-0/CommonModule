package fit.asta.health.profile.model

import fit.asta.health.profile.model.domain.UserProfile

interface ProfileRepo {

    suspend fun getProfileData(uid:String): UserProfile
    //suspend fun getProfileData(uid:String): Flow<UserProfile>
}