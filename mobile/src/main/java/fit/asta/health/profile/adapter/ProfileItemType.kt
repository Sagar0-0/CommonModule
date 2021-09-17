package fit.asta.health.profile.adapter

enum class ProfileItemType(val value: Int) {

    PlainCard(1),
    BodyTypeCard(2),
    SleepScheduleCard(3),
    ChipsCard(4);
    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}