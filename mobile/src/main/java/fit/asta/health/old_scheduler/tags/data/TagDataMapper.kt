package fit.asta.health.old_scheduler.tags.data

import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagNetData
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagsResponse

class TagDataMapper {

    fun toMap(s: ScheduleTagsResponse): List<ScheduleTagData> {
        return s.data.map {
            ScheduleTagData().apply {
                uid = it.uid
                tagName = it.tag
                type = it.type
                url = it.url
            }
        }
    }

    fun toTag(netTag: ScheduleTagNetData): ScheduleTagData {
        return ScheduleTagData().apply {
            uid = netTag.uid
            tagName = netTag.tag
            type = netTag.type
            url = netTag.url
        }
    }

    fun toNetTag(stag: ScheduleTagData): ScheduleTagNetData {
        return ScheduleTagNetData().apply {
            uid = stag.uid
            tag = stag.tagName
            type = stag.type
            url = stag.url
        }
    }
}