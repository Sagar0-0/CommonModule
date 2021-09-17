package fit.asta.health.profile.adapter

import fit.asta.health.profile.adapter.viewholders.ProfileTabType

class PlainCardItem(
    var id: String = "",
    var label: String = "",
    var image: String = "",
    var itemValue: String = "",
    var updatedValue: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.PlainCard
) : ProfileItem