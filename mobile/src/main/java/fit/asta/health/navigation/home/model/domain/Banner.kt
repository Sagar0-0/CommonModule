package fit.asta.health.navigation.home.model.domain


data class Banner(
    val id: String = "",
    val type: Int = 0,
    val title: String = "",
    val desc: String = "",
    val url: String = "",
    val isVisible: Boolean = true
)
