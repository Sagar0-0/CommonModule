package fit.asta.health.main

sealed class Graph(val route: String) {
    object ROOT : Graph("graph_root")
    object Home : Graph("graph_home")
    object Settings : Graph("graph_settings")
}
