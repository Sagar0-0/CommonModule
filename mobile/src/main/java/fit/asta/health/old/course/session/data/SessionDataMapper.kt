package fit.asta.health.old.course.session.data

import fit.asta.health.old.course.session.networkdata.SessionResponse

class SessionDataMapper {
    fun toMap(sessionNetData: SessionResponse): SessionData {
        return SessionData().apply {
            uid = sessionNetData.data.uid
            imgUrl = sessionNetData.data.url
            title = sessionNetData.data.ttl
            author = sessionNetData.data.auth
            day = sessionNetData.data.day
            totalDays = sessionNetData.data.totalDays
            progress = sessionNetData.data.prg
            level = sessionNetData.data.lvl
            duration = sessionNetData.data.dur
            intensity = sessionNetData.data.int
            calories = sessionNetData.data.cal
            exerciseList = sessionNetData.data.exercises.map {
                Exercise().apply {
                    uid = it.uid
                    title = it.ttl
                    subTitle = it.sub
                    duration = it.dur
                    url = it.url
                    style = it.sty
                    level = it.lvl
                    intensity = it.int
                    calories = it.cal
                }
            }
        }
    }
}