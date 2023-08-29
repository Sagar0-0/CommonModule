package fit.asta.health.data.spotify.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Artist
import fit.asta.health.data.spotify.model.common.ExternalIds
import fit.asta.health.data.spotify.model.common.ExternalUrls
import fit.asta.health.data.spotify.model.common.Image

class CustomTypeConvertor {
    @TypeConverter
    fun listOfArtistToString(item: List<Artist>): String =
        Gson().toJson(item)

    @TypeConverter
    fun stringToListOfArtist(string: String): List<Artist> =
        Gson().fromJson(string, Array<Artist>::class.java).toList()

    @TypeConverter
    fun listOfImagesToString(item: List<Image>): String =
        Gson().toJson(item)

    @TypeConverter
    fun stringToListOfImages(string: String): List<Image> =
        Gson().fromJson(string, Array<Image>::class.java).toList()


    @TypeConverter
    fun listOfAvailableMarketsToString(item: List<String>): String =
        Gson().toJson(item)

    @TypeConverter
    fun stringToListOfAvailableMarkets(string: String): List<String> =
        Gson().fromJson(string, Array<String>::class.java).toList()

    @TypeConverter
    fun externalUrlsToString(item: ExternalUrls): String = Gson().toJson(item)

    @TypeConverter
    fun stringToExternalUrls(string: String): ExternalUrls =
        Gson().fromJson(string, ExternalUrls::class.java)

    @TypeConverter
    fun albumToString(item: Album): String = Gson().toJson(item)

    @TypeConverter
    fun stringToAlbum(string: String): Album =
        Gson().fromJson(string, Album::class.java)

    @TypeConverter
    fun externalIdsToString(item: ExternalIds): String = Gson().toJson(item)

    @TypeConverter
    fun stringToExternalIds(string: String): ExternalIds =
        Gson().fromJson(string, ExternalIds::class.java)
}