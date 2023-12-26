package fit.asta.health.data.breathing.model.domain.mapper


import fit.asta.health.data.breathing.model.domain.model.BreathingTool
import fit.asta.health.data.breathing.model.network.NetGetRes
import fit.asta.health.data.breathing.model.network.NetGetStart
import fit.asta.health.data.breathing.model.network.request.NetPut

data class MusicTool(
    val music: MusicData,
    val instructor: List<MusicData>
)

data class MusicData(
    val artist_name: String,
    val artist_url: String,
    val duration: String,
    val imgUrl: String,
    val language: List<String>,
    val music_name: String,
    val music_url: String
)

fun NetGetStart.getMusicTool(): MusicTool {
    return MusicTool(
        music = MusicData(
            artist_name = this.musicData.artist.name,
            artist_url = this.musicData.artist.url,
            duration = this.musicData.music.duration,
            imgUrl = this.musicData.music.imgUrl,
            language = listOf(this.musicData.music.language),
            music_name = this.musicData.music.name,
            music_url = this.musicData.music.url
        ),
        instructor = this.exerciseList.map {
            MusicData(
                artist_name = it.mda.instructorDetail.name,
                artist_url = "hoi",
                duration = it.mda.instructorMda.duration,
                imgUrl = "hidf",
                language = listOf(it.mda.instructorMda.language),
                music_name = it.mda.instructorMda.name,
                music_url = it.mda.instructorMda.url
            )
        },
    )
}

fun NetGetRes.getBreathingTool(): BreathingTool {
    return BreathingTool(
        id = this.breathingToolData.id,
        uid = this.breathingToolData.uid,
        target = this.breathingProgressData.target,
        recommend = this.breathingProgressData.recommend,
        achieved = this.breathingProgressData.achieved,
        weather = this.breathingToolData.weather,
        bottomSheetPrc = this.breathingToolData.prc
    )
}

fun BreathingTool.toPutData(): NetPut {
    return NetPut(
        code = "breathing",
        id = this.id,
        type = 3,
        uid = this.uid,
        wea = this.weather,
        prc = this.bottomSheetPrc
    )
}


