package fit.asta.health.tools.meditation.model.domain.mapper

import fit.asta.health.tools.meditation.model.domain.model.MeditationTool
import fit.asta.health.tools.meditation.model.domain.model.MusicData
import fit.asta.health.tools.meditation.model.domain.model.MusicTool
import fit.asta.health.tools.meditation.model.domain.model.Tool
import fit.asta.health.tools.meditation.model.network.NetMeditationToolRes
import fit.asta.health.tools.meditation.model.network.NetMusicRes
import fit.asta.health.tools.meditation.model.network.Prc


fun NetMusicRes.getMusicTool(): MusicTool {
    return MusicTool(
        music = MusicData(
            artist_name = this.data.musicData.artist.name,
            artist_url = this.data.musicData.artist.url,
            duration = this.data.musicData.music.dur,
            imgUrl = this.data.musicData.music.imgUrl,
            language = this.data.musicData.music.lang,
            music_name = this.data.musicData.music.name,
            music_url = this.data.musicData.music.url
        ),
        instructor = this.data.instructorData.map {
            MusicData(
                artist_name = it.artist.name,
                artist_url = it.artist.url,
                duration = it.music.dur,
                imgUrl = it.music.imgUrl,
                language = it.music.lang,
                music_name = it.music.name,
                music_url = it.music.url
            )
        },
    )
}


fun NetMeditationToolRes.getMeditationTool(): MeditationTool {
    return MeditationTool(
        uid = this.data.meditationProgressData.uid,
        target = this.data.meditationProgressData.tgt,
        achieved = this.data.meditationProgressData.ach,
        remaining = this.data.meditationProgressData.rem,
        recommended = this.data.meditationProgressData.rcm,
        meta_max = this.data.meditationProgressData.meta.max,
        meta_min = this.data.meditationProgressData.meta.min,
        music = this.data.meditationToolData.prc[0].mapPrc(),
        targetTool = this.data.meditationToolData.prc[1].mapPrc(),
        level = this.data.meditationToolData.prc[2].mapPrc(),
        instructor = this.data.meditationToolData.prc[3].mapPrc(),
        language = this.data.meditationToolData.prc[4].mapPrc()
    )
}

fun Prc.mapPrc(): Tool {
    return Tool(title, values)
}