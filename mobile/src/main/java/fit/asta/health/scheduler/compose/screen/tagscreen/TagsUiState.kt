package fit.asta.health.scheduler.compose.screen.tagscreen

import fit.asta.health.scheduler.model.net.tag.Data

data class TagsUiState(
    val selected: Boolean=false,
    val meta: Data=Data(id = "", name = "", uid = "", url = ""),
    val id: Int = 0
)
