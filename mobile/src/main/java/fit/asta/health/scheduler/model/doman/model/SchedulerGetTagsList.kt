package fit.asta.health.scheduler.model.doman.model

import fit.asta.health.scheduler.model.net.tag.Data

data class SchedulerGetTagsList(
    val customTagList: List<Data>,
    val list: List<Data>,
)
