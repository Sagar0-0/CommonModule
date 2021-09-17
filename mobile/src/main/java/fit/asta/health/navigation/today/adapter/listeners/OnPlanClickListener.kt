package fit.asta.health.navigation.today.adapter.listeners

interface OnPlanClickListener {
    fun onPlanClick(position: Int)
    fun onPlayClick(position: Int)
    fun onRescheduling(position: Int)
}