package fit.asta.health.course.details.data

import fit.asta.health.course.details.networkdata.CourseDetailsNetData

class CourseDetailsDataMapper {

    fun toMap(courseDetails: CourseDetailsNetData): CourseDetailsData {
        return CourseDetailsData().apply {
            uid = courseDetails.uid
            header = CourseHeaderData().apply {
                pubDate = courseDetails.header.date
                title = courseDetails.header.ttl
                subTitle = courseDetails.header.sub
                urlType = courseDetails.header.urlType
                url = courseDetails.header.url
                level = courseDetails.header.lvl
                duration = courseDetails.header.dur
            }
            overview = OverViewData().apply {
                intro = IntroData().apply {
                    url = courseDetails.overview.intro.url
                    title = courseDetails.overview.intro.ttl
                    desc = courseDetails.overview.intro.dsc
                }
                keyPoints = KeyPointsData().apply {
                    title = courseDetails.overview.keys.ttl
                    points = courseDetails.overview.keys.pts
                }
                audience = AudienceData().apply {
                    ageGroup = AgeGroupData().apply {
                        from = courseDetails.overview.audience.age.frm
                        to = courseDetails.overview.audience.age.to
                    }
                    gender = courseDetails.overview.audience.gen
                    level = courseDetails.overview.audience.lvl
                    preRequisites = courseDetails.overview.audience.preReq
                    notFor = courseDetails.overview.audience.notFor
                }
                experts = courseDetails.overview.experts.map {
                    ExpertData().apply {
                        uid = it.uid
                        name = it.name
                        expYears = it.exp
                        profession = it.prof
                        qualification = it.qlf
                        about = it.abt
                        url = it.url
                    }
                }
            }
            sessions = courseDetails.sessions.map {
                SessionData().apply {
                    uid = it.uid
                    title = it.ttl
                    subTitle = it.sub
                    level = it.lvl
                    duration = it.dur
                    calories = it.cal
                    intensity = it.int
                    desc = it.dsc
                    precautions = it.pct
                }
            }
        }
    }
}