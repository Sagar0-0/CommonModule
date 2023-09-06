package fit.asta.health.feature.scheduler.ui.screen.tagscreen

import fit.asta.health.data.scheduler.db.entity.TagEntity


sealed class TagsEvent {
    data class DeleteTag(val tag: TagEntity) : TagsEvent()
    data class SelectedTag(val tag: TagEntity) : TagsEvent()
    data class UpdateTag(val label: String, val url: String) : TagsEvent()
    data object GetTag : TagsEvent()
}
