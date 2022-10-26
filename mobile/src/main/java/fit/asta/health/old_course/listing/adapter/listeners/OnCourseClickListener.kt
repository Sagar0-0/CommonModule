package fit.asta.health.old_course.listing.adapter.listeners

interface OnCourseClickListener {
    fun onCourseClick(position: Int)
    fun onPlayClick(position: Int)
    fun onShareClick(position: Int)
    fun onAlarmClick(position: Int)
    fun onFavoriteClick(position: Int)
}