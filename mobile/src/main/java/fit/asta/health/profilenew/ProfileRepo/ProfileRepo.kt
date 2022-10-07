package fit.asta.health.profilenew.ProfileRepo

import fit.asta.health.profilenew.data.ProileData

interface ProfileRepo {

    suspend fun getProfileData(uid:String): ProileData
}