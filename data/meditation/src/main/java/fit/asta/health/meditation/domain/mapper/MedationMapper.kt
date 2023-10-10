package fit.asta.health.meditation.domain.mapper

import fit.asta.health.meditation.domain.model.MeditationTool
import fit.asta.health.meditation.domain.model.MusicData
import fit.asta.health.meditation.domain.model.MusicTool
import fit.asta.health.meditation.remote.network.NetMeditationToolResponse
import fit.asta.health.meditation.remote.network.NetMusicRes
import fit.asta.health.meditation.remote.network.NetSheetData
import fit.asta.health.meditation.remote.network.Value


fun NetMusicRes.getMusicTool(): MusicTool {
    return MusicTool(
        music = MusicData(
            artist_name = this.musicData.artist.name,
            artist_url = this.musicData.artist.url,
            duration = this.musicData.music.dur,
            imgUrl = this.musicData.music.imgUrl,
            language = this.musicData.music.lang,
            music_name = this.musicData.music.name,
            music_url = this.musicData.music.url
        ),
        instructor = this.instructorData?.map {
            MusicData(
                artist_name = it.artist.name,
                artist_url = it.artist.url,
                duration = it.music.dur,
                imgUrl = it.music.imgUrl,
                language = it.music.lang,
                music_name = it.music.name,
                music_url = it.music.url
            )
        } ?: listOf(),
    )
}


fun NetMeditationToolResponse.getMeditationTool(): MeditationTool {
    return MeditationTool(
        uid = this.meditationProgressData.uid,
        target = this.meditationProgressData.tgt,
        achieved = this.meditationProgressData.ach,
        remaining = this.meditationProgressData.rem,
        recommended = this.meditationProgressData.rcm,
        metaMax = this.meditationProgressData.meta.max,
        metaMin = this.meditationProgressData.meta.min,
        bottomSheetPrc = this.meditationToolData.prc
    )
}

fun NetSheetData.toValue(): Value {
    return Value(
        dsc = this.dsc, id = "", ttl = this.name, url = this.url, type = 1
    )
}