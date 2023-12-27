package fit.asta.health.data.exercise.model.domain.mapper

import fit.asta.health.data.exercise.model.domain.model.ExerciseTool
import fit.asta.health.data.exercise.model.network.NetGetRes
import fit.asta.health.data.exercise.model.network.NetGetStart

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
fun NetGetRes.getExerciseTool():ExerciseTool{
    return when(this.data.toolData.code){
        "yoga"->{
            ExerciseTool(
                achieved =this.data.progressData.ach ,
                bp = this.data.progressData.bp,
                bpm =this.data.progressData.bpm ,
                calories =this.data.progressData.cal ,
                date = this.data.progressData.date,
                duration =this.data.progressData.dur ,
                id = this.data.progressData.id,
                recommend =this.data.progressData.rcm ,
                target =this.data.progressData.tgt ,
                uid =this.data.progressData.uid ,
                vit = this.data.progressData.vit,
                weather = this.data.toolData.wea,
                code = this.data.toolData.code,
                style = this.data.toolData.prc[1].values[0].value,
                durationPrc = this.data.toolData.prc[2].values[0].value,
                bodyParts = this.data.toolData.prc[3].values[0].value,
                challenge = this.data.toolData.prc[4].values[0].value,
                bodyStretch = this.data.toolData.prc[5].values[0].value,
                quick = this.data.toolData.prc[6].values[0].value,
                level = this.data.toolData.prc[7].values[0].value,
                instructor = this.data.toolData.prc[8].values[0].value,
                music = this.data.toolData.prc[9].values[0].value,
                language = this.data.toolData.prc[10].values[0].value,
            )
        }
        "dance"->{
            ExerciseTool(
                achieved =this.data.progressData.ach ,
                bp = this.data.progressData.bp,
                bpm =this.data.progressData.bpm ,
                calories =this.data.progressData.cal ,
                date = this.data.progressData.date,
                duration =this.data.progressData.dur ,
                id = this.data.progressData.id,
                recommend =this.data.progressData.rcm ,
                target =this.data.progressData.tgt ,
                uid =this.data.progressData.uid ,
                vit = this.data.progressData.vit,
                weather = this.data.toolData.wea,
                code = this.data.toolData.code,
                style = this.data.toolData.prc[1].values[0].value,
                bodyParts = this.data.toolData.prc[2].values[0].value,
                challenge = this.data.toolData.prc[3].values[0].value,
                bodyStretch = this.data.toolData.prc[4].values[0].value,
                quick = this.data.toolData.prc[5].values[0].value,
                level = this.data.toolData.prc[6].values[0].value,
                instructor = this.data.toolData.prc[7].values[0].value,
                music = this.data.toolData.prc[8].values[0].value,
                language = this.data.toolData.prc[9].values[0].value,
            )
        }
        "workout"->{
            ExerciseTool(
                achieved =this.data.progressData.ach ,
                bp = this.data.progressData.bp,
                bpm =this.data.progressData.bpm ,
                calories =this.data.progressData.cal ,
                date = this.data.progressData.date,
                duration =this.data.progressData.dur ,
                id = this.data.progressData.id,
                recommend =this.data.progressData.rcm ,
                target =this.data.progressData.tgt ,
                uid =this.data.progressData.uid ,
                vit = this.data.progressData.vit,
                weather = this.data.toolData.wea,
                code = this.data.toolData.code,
                style = this.data.toolData.prc[1].values[0].value,
                durationPrc = this.data.toolData.prc[2].values[0].value,
                bodyParts = this.data.toolData.prc[3].values[0].value,
                challenge = this.data.toolData.prc[4].values[0].value,
                bodyStretch = this.data.toolData.prc[5].values[0].value,
                quick = this.data.toolData.prc[6].values[0].value,
                level = this.data.toolData.prc[7].values[0].value,
                equipments = this.data.toolData.prc[8].values[0].value,
                instructor = this.data.toolData.prc[9].values[0].value,
                music = this.data.toolData.prc[10].values[0].value,
                language = this.data.toolData.prc[11].values[0].value,
            )
        }
        "HIIT"->{
            ExerciseTool(
                achieved =this.data.progressData.ach ,
                bp = this.data.progressData.bp,
                bpm =this.data.progressData.bpm ,
                calories =this.data.progressData.cal ,
                date = this.data.progressData.date,
                duration =this.data.progressData.dur ,
                id = this.data.progressData.id,
                recommend =this.data.progressData.rcm ,
                target =this.data.progressData.tgt ,
                uid =this.data.progressData.uid ,
                vit = this.data.progressData.vit,
                weather = this.data.toolData.wea,
                code = this.data.toolData.code,
                style = this.data.toolData.prc[1].values[0].value,
                durationPrc = this.data.toolData.prc[2].values[0].value,
                bodyParts = this.data.toolData.prc[3].values[0].value,
                challenge = this.data.toolData.prc[4].values[0].value,
                bodyStretch = this.data.toolData.prc[5].values[0].value,
                quick = this.data.toolData.prc[6].values[0].value,
                level = this.data.toolData.prc[7].values[0].value,
                equipments = this.data.toolData.prc[1].values[0].value,
                instructor = this.data.toolData.prc[8].values[0].value,
                music = this.data.toolData.prc[9].values[0].value
            )
        }

        else -> {
            ExerciseTool(
                achieved =this.data.progressData.ach ,
                bp = this.data.progressData.bp,
                bpm =this.data.progressData.bpm ,
                calories =this.data.progressData.cal ,
                date = this.data.progressData.date,
                duration =this.data.progressData.dur ,
                id = this.data.progressData.id,
                recommend =this.data.progressData.rcm ,
                target =this.data.progressData.tgt ,
                uid =this.data.progressData.uid ,
                vit = this.data.progressData.vit,
                weather = this.data.toolData.wea,
                code = this.data.toolData.code,
                style = this.data.toolData.prc[1].values[0].value,
                durationPrc = this.data.toolData.prc[2].values[0].value,
                bodyParts = this.data.toolData.prc[3].values[0].value,
                challenge = this.data.toolData.prc[4].values[0].value,
                bodyStretch = this.data.toolData.prc[5].values[0].value,
                quick = this.data.toolData.prc[6].values[0].value,
                level = this.data.toolData.prc[7].values[0].value,
                equipments = this.data.toolData.prc[1].values[0].value,
                instructor = this.data.toolData.prc[8].values[0].value,
                music = this.data.toolData.prc[9].values[0].value
            )
        }
    }

}
fun NetGetStart.getMusicTool(): MusicTool {
    return MusicTool(
        music = MusicData(
            artist_name = this.data.musicData.artist.name,
            artist_url = this.data.musicData.artist.url,
            duration = this.data.musicData.music.dur,
            imgUrl = this.data.musicData.music.imgUrl,
            language = listOf(this.data.musicData.music.lang),
            music_name = this.data.musicData.music.name,
            music_url = this.data.musicData.music.url
        ),
        instructor = this.data.instructorData.map {
            MusicData(
                artist_name = it.artist.name,
                artist_url = it.artist.url,
                duration = it.music.dur,
                imgUrl = it.music.imgUrl,
                language = listOf(it.music.lang),
                music_name = it.music.name,
                music_url = it.music.url
            )
        },
    )
}