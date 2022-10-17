package fit.asta.health.old_profile.adapter.viewholders

enum class ProfileTabType(val value: Int) {

    NONE(1),
    PhysiqueTab(2),
    LifeStyleTab(3),
    HealthTargetsTab(4);
    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}