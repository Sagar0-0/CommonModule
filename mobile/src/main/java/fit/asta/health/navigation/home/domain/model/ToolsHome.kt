package fit.asta.health.navigation.home.domain.model

/**
 * See Recipe example:
 */
data class ToolsHome(
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int = 0,
    val sourceUrl: String,
    val ingredients: List<String> = listOf(),
    val dateAdded: String,
    val dateUpdated: String,
)