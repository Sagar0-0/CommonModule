package fit.asta.health.old.scheduler.data

import fit.asta.health.old.scheduler.networkdata.ScheduleNetData
import fit.asta.health.old.scheduler.networkdata.ScheduleResponse
import fit.asta.health.old.scheduler.networkdata.ScheduleSessionNetData
import fit.asta.health.old.scheduler.networkdata.ScheduleTimeNetData

class ScheduleDataMapper {

    fun toScheduleMap(s: ScheduleResponse): ScheduleData {
        return ScheduleData().apply {
            uid = s.data.uid
            tagId = s.data.tagId
            title = s.data.ttl
            description = s.data.dsc
            startDate = s.data.date
            courseDuration = s.data.dur
            alertType = s.data.type
            priority = s.data.pry
            remindBefore = s.data.rmd
            sessions = s.data.sns.map { session ->
                ScheduleSessionData().apply {
                    day = session.day
                    cycles = session.times.map {
                        ScheduleTimeData().apply {
                            hour = it.hr
                            minute = it.min
                        }
                    }
                }
            }
        }
    }

    fun toRescheduleMap(userId: String, s: ScheduleData): ScheduleNetData {
        return ScheduleNetData().apply {
            uid = s.uid
            tagId = s.tagId
            usrId = userId
            ttl = s.title
            dsc = s.description
            date = s.startDate
            dur = s.courseDuration
            type = s.alertType
            pry = s.priority
            rmd = s.remindBefore
            sns = s.sessions.map { session ->
                ScheduleSessionNetData().apply {
                    day = session.day
                    times = session.cycles.map {
                        ScheduleTimeNetData().apply {
                            hr = it.hour
                            min = it.minute
                        }
                    }
                }
            }
        }
    }
}