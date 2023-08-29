package fit.asta.health.data.scheduler.remote.model

import fit.asta.health.data.scheduler.remote.net.tag.Data

data class SchedulerGetTagsList(
    val customTagList: List<Data>? = null,
    val list: List<Data>,
)
