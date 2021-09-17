package fit.asta.health.course.details.adapter

enum class CourseDetailsTabType(val value: Int) {

    OverviewTab(1),
    SessionsTab(2);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}