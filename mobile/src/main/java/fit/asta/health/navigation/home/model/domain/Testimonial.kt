package fit.asta.health.navigation.home.model.domain

data class Testimonial(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    val rank: Int = 0,
    val media: List<Media>? = null,
    val user: User? = null,
)

data class Media(
    val type: Int = 0,
    val title: String = "",
    val url: String = ""
)

data class User(
    val userId: String = "",
    val name: String = "",
    val org: String = "",
    val role: String = "",
    val url: String = ""
)