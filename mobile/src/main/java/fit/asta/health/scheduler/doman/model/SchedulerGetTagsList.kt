package fit.asta.health.scheduler.doman.model

import fit.asta.health.scheduler.data.api.net.tag.Data

data class SchedulerGetTagsList(
    val customTagList: List<Data>? =null,
    val list: List<Data>,
)
