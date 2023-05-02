package fit.asta.health.scheduler.model.doman

import fit.asta.health.scheduler.model.doman.model.SchedulerGetData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetListData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetTagsList
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.model.net.tag.AstaGetTagsListResponse

fun AstaSchedulerGetResponse.mapToSchedulerGetData(): SchedulerGetData {
    return SchedulerGetData(data = this.data)
}
fun AstaSchedulerGetListResponse.mapToSchedulerGetListData():SchedulerGetListData{
    return SchedulerGetListData(list =this.data)
}
fun AstaGetTagsListResponse.mapToSchedulerGetTagsList():SchedulerGetTagsList{
    return SchedulerGetTagsList(customTagList = this.customTagData, list = this.data)
}