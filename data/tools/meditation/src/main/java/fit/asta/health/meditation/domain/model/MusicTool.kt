package fit.asta.health.meditation.domain.model

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
