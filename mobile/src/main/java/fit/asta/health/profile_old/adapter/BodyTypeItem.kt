package fit.asta.health.profile_old.adapter

import fit.asta.health.profile_old.adapter.viewholders.ProfileTabType

class BodyTypeItem(
    var id: String = "",
    var label: String = "",
    var image: String = "",
    var bodyTypeValue: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.BodyTypeCard
) : ProfileItem