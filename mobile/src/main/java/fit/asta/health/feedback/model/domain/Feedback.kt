package fit.asta.health.feedback.model.domain

data class Feedback(
    val id: String = "",
    val name: String = "",
    val code: Int = 0,
    val title: String = "",
    val description: String = "",
    val url: String = ""
)
