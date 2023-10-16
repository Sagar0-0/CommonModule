package fit.asta.health.data.scheduler.remote.model

import fit.asta.health.data.scheduler.remote.net.tag.TagData

data class SchedulerGetTagsList(
    val customTagList: List<TagData>? = null,
    val list: List<TagData>,
)
