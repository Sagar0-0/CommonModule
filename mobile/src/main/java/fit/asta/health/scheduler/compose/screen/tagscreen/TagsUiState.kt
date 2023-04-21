package fit.asta.health.scheduler.compose.screen.tagscreen

import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.tag.Data

data class TagsUiState(
    val tagsList: List<TagEntity> = emptyList(),
    val selectedTag: TagState = TagState(),
    val newTag: Data = Data(id = "", name = "", uid = "", url = ""),
)

data class TagState(
    val selected: Boolean = false,
    val meta: Data = Data(id = "", name = "", uid = "", url = ""),
    val id: Int = 0
)
