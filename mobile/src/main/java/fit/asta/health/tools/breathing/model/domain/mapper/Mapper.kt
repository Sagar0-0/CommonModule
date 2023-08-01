package fit.asta.health.tools.breathing.model.domain.mapper


import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AMPMHoursMin
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.Time24hr
import fit.asta.health.tools.breathing.model.domain.model.BreathingTool
import fit.asta.health.tools.breathing.model.network.NetGetRes
import fit.asta.health.tools.breathing.model.network.NetGetStart
import fit.asta.health.tools.breathing.model.network.request.NetPut
import fit.asta.health.tools.breathing.model.network.request.Prc
import fit.asta.health.tools.breathing.model.network.request.Value
import fit.asta.health.tools.meditation.model.domain.model.MusicData
import fit.asta.health.tools.meditation.model.domain.model.MusicTool


fun NetGetStart.getMusicTool(): MusicTool {
    return MusicTool(
        music = MusicData(
            artist_name = this.data.musicData.artist.name,
            artist_url = this.data.musicData.artist.url,
            duration = this.data.musicData.music.duration,
            imgUrl = this.data.musicData.music.imgUrl,
            language = this.data.musicData.music.language,
            music_name = this.data.musicData.music.name,
            music_url = this.data.musicData.music.url
        ),
        instructor = this.data.exerciseList.map {
            MusicData(
                artist_name = it.mda.instructorDetail.name,
                artist_url = "hoi",
                duration = it.mda.instructorMda.duration,
                imgUrl = "hidf",
                language = it.mda.instructorMda.language,
                music_name = it.mda.instructorMda.name,
                music_url = it.mda.instructorMda.url
            )
        },
    )
}

fun NetGetRes.getBreathingTool(): BreathingTool {
    return BreathingTool(
        id = this.data.breathingToolData.id,
        uid = this.data.breathingToolData.uid,
        target = this.data.breathingProgressData.target,
        recommend = this.data.breathingProgressData.recommend,
        achieved = this.data.breathingProgressData.achieved,
        exercise = this.data.breathingToolData.prc[0].values.map { it.value },
        Language = this.data.breathingToolData.prc[1].values[0].value,
        Break = this.data.breathingToolData.prc[2].values[0].value,
        Goal = this.data.breathingToolData.prc[3].values.map { it.value },
        Target = this.data.breathingToolData.prc[4].values[0].value,
        Pace = this.data.breathingToolData.prc[5].values[0].value,
        Level = this.data.breathingToolData.prc[6].values[0].value,
        instructor = this.data.breathingToolData.prc[7].values[0].value,
        Music = this.data.breathingToolData.prc[8].values[0].value,
        weather = this.data.breathingToolData.weather
    )
}

fun BreathingTool.toPutData(): NetPut {
    return NetPut(
        code = "breathing",
        id = this.id,
        type = 3,
        uid = this.uid,
        wea = this.weather,
        prc = listOf(
            Prc(
                code = "exercise",
                id = "",
                dsc = "exercise",
                title = "exercise",
                type = 1,
                values = this.exercise.map { value-> Value(id = "", name = value, value = value) }
            ),

            Prc(
                code = "Language",
                id = "",
                dsc = "Language",
                title = "Language",
                type = 0,
                values = listOf(Value(id = "", name = this.Language, value = this.Language))
            ),

            Prc(
                code = "Break",
                id = "",
                dsc = "Break",
                title = "Break",
                type = 1,
                values = listOf(Value(id = "", name = this.Break, value = this.Break))
            ),

            Prc(
                code = "Goal",
                id = "",
                dsc = "Goal",
                title = "Goal",
                type = 1,
                values = this.Goal.map { value-> Value(id = "", name = value, value = value) }
            ),

            Prc(
                code = "target",
                id = "",
                dsc = "target",
                title = "target",
                type = 1,
                values = listOf(Value(id = "", name = this.Target, value = this.Target))
            ),

            Prc(
                code = "Pace",
                id = "",
                dsc = "Pace",
                title = "Pace",
                type = 1,
                values = listOf(Value(id = "", name = this.Pace, value = this.Pace))
            ),

            Prc(
                code = "Level",
                id = "",
                dsc = "Level",
                title = "Level",
                type = 1,
                values = listOf(Value(id = "", name = this.Level, value = this.Level))
            ),

            Prc(
                code = "instructor",
                id = "",
                dsc = "instructor",
                title = "instructor",
                type = 1,
                values = listOf(Value(id = "", name = this.instructor, value = this.instructor))
            ),

            Prc(
                code = "Music",
                id = "",
                dsc = "Music",
                title = "Music",
                type = 1,
                values = listOf(Value(id = "", name = this.Music, value = this.Music))
            )
        )
    )
}


fun Time24hr.convert24hrTo12hr(): AMPMHoursMin {
    var hour = this.hour
    val dayTime = if (hour < 12) AMPMHoursMin.DayTime.AM else AMPMHoursMin.DayTime.PM
    hour %= 12
    if (hour == 0) {
        hour = 12
    }
    return AMPMHoursMin(hour, this.min, dayTime)
}

fun AMPMHoursMin.convert12hrTo24hr(): Time24hr {
    var hour = this.hours
    if (this.dayTime == AMPMHoursMin.DayTime.PM && hour != 12) {
        hour += 12
    } else if (this.dayTime == AMPMHoursMin.DayTime.AM && hour == 12) {
        hour = 0
    }
    return Time24hr(hour, this.minutes)
}

