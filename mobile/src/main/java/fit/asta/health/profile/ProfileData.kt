package fit.asta.health.profile

import fit.asta.health.profile.adapter.ProfileItem


data class ProfileData(val physique: ArrayList<ProfileItem>,
                       val lifestyle: ArrayList<ProfileItem>,
                       val health: ArrayList<ProfileItem>)