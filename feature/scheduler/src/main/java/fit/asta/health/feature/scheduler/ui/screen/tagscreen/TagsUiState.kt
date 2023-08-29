package fit.asta.health.feature.scheduler.ui.screen.tagscreen

import fit.asta.health.data.scheduler.db.entity.TagEntity
import fit.asta.health.data.scheduler.remote.net.tag.Data

data class TagsUiState(
    val tagsList: List<TagEntity> = emptyList(),
    val selectedTag: TagState = TagState(),
    val newTag: Data = Data(
        id = "",
        name = "",
        uid = "",
        url = ""
    ),
)

data class TagState(
    val selected: Boolean = false,
    val meta: Data = Data(
        id = "",
        name = "",
        uid = "",
        url = ""
    ),
    val id: Int = 0
)
