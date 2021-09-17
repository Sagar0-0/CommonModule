package fit.asta.health.profile.adapter

import fit.asta.health.profile.adapter.viewholders.ProfileTabType

class SleepScheduleItem(
    var label: String = "Sleep Schedule",
    var image: Int = 0,
    var bedTime: String = "",
    var wakeUpTime: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.SleepScheduleCard
) : ProfileItem