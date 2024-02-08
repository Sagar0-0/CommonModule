package fit.asta.health.main

sealed class Graph(val route: String) {
    object ROOT : Graph("graph_root")
    object Scheduler : Graph("graph_today_scheduler")
    object Testimonials : Graph("graph_testimonials_tool")
}
