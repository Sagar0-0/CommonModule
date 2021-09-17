package fit.asta.health.profile.adapter

import fit.asta.health.profile.adapter.viewholders.ProfileTabType

class BodyTypeItem(
    var id: String = "",
    var label: String = "",
    var image: String = "",
    var bodyTypeValue: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.BodyTypeCard
) : ProfileItem