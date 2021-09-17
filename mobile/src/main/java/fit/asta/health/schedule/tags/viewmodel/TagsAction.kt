package fit.asta.health.schedule.tags.viewmodel

import fit.asta.health.schedule.tags.data.ScheduleTagData

sealed class TagsAction {

    class LoadTag(val tag: ScheduleTagData) : TagsAction()
    class LoadTagList(val list: List<ScheduleTagData>) : TagsAction()
    object Empty : TagsAction()
    class Error(val message: String) : TagsAction()
}