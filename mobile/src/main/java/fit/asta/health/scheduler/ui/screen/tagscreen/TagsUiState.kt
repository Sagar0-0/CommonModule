package fit.asta.health.scheduler.ui.screen.tagscreen

import fit.asta.health.scheduler.data.api.net.tag.Data
import fit.asta.health.scheduler.data.db.entity.TagEntity

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
