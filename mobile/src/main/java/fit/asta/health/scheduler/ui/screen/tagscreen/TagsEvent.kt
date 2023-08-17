package fit.asta.health.scheduler.ui.screen.tagscreen

import fit.asta.health.scheduler.data.db.entity.TagEntity

sealed class TagsEvent {
    data class DeleteTag(val tag: TagEntity) : TagsEvent()
    data class UndoTag(val tag: TagEntity) : TagsEvent()
    data class SelectedTag(val tag: TagEntity) : TagsEvent()
    data class UpdateTag(val label: String, val url: String) : TagsEvent()

}
