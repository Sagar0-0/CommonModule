package fit.asta.health.profile_old

import fit.asta.health.profile_old.adapter.ProfileItem


data class ProfileData(val physique: ArrayList<ProfileItem>,
                       val lifestyle: ArrayList<ProfileItem>,
                       val health: ArrayList<ProfileItem>)