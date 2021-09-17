package fit.asta.health.schedule.tags.viewmodel

import fit.asta.health.schedule.tags.data.ScheduleTagData

interface TagsDataStore {
    fun setMyTag(tag: ScheduleTagData)
    fun getMyTag(userId: String): ScheduleTagData
    fun updateTagList(list: List<ScheduleTagData>)
    fun getTag(position: Int): ScheduleTagData
    fun updateData(tagId: String, updatedValue: Boolean)
}