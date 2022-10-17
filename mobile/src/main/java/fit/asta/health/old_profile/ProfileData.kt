package fit.asta.health.old_profile

import fit.asta.health.old_profile.adapter.ProfileItem


data class ProfileData(val physique: ArrayList<ProfileItem>,
                       val lifestyle: ArrayList<ProfileItem>,
                       val health: ArrayList<ProfileItem>)