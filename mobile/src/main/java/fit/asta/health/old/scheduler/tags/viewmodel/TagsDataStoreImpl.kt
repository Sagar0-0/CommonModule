package fit.asta.health.old.scheduler.tags.viewmodel

import fit.asta.health.old.scheduler.tags.data.ScheduleTagData

class TagsDataStoreImpl : TagsDataStore {
    private var listTags: List<ScheduleTagData> = listOf()
    private var userTag = ScheduleTagData()

    override fun setMyTag(tag: ScheduleTagData) {
        userTag = tag
    }

    override fun getMyTag(userId: String): ScheduleTagData {
        return userTag
    }

    override fun updateTagList(list: List<ScheduleTagData>) {
        listTags = list
    }

    override fun getTag(position: Int): ScheduleTagData {
        return listTags[position]
    }

    override fun updateData(tagId: String, updatedValue: Boolean) {
        listTags.find { it.isSelected }?.isSelected = false
        listTags.find { it.uid == tagId }?.isSelected = updatedValue
    }
}