package fit.asta.health.navigation.today.data

import fit.asta.health.navigation.today.networkdata.TodayPlanNetData

class TodayDataMapper {

    fun toMap(todayListNetData: TodayPlanNetData): List<TodayPlanItemData> {
        return todayListNetData.data.map {
            TodayPlanItemData().apply {
                uid = it.uid
                courseId = it.cid
                sessionId = it.sid
                tag = it.tag
                title = it.ttl
                subTitle = it.dsc
                urlType = it.urlType
                imageUrl = it.url
                time = it.time
                duration = it.dur
                progress = it.prog
                stat = it.stat
                type = TodayPlanItemType.valueOf(it.type)
            }
        }
    }
}