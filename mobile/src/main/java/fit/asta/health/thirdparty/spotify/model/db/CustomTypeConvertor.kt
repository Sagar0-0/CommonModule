package fit.asta.health.thirdparty.spotify.model.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import fit.asta.health.thirdparty.spotify.model.netx.common.ArtistX
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalIdsX

class CustomTypeConvertor {
    @TypeConverter
    fun listOfArtistToString(item: List<ArtistX>): String =
        Gson().toJson(item)

    @TypeConverter
    fun stringToListOfArtist(string: String): List<ArtistX> =
        Gson().fromJson(string, Array<ArtistX>::class.java).toList()

    @TypeConverter
    fun listOfImagesToString(item: List<ImageX>): String =
        Gson().toJson(item)

    @TypeConverter
    fun stringToListOfImages(string: String): List<ImageX> =
        Gson().fromJson(string, Array<ImageX>::class.java).toList()


    @TypeConverter
    fun listOfAvailableMarketsToString(item: List<String>): String =
        Gson().toJson(item)

    @TypeConverter
    fun stringToListOfAvailableMarkets(string: String): List<String> =
        Gson().fromJson(string, Array<String>::class.java).toList()

    @TypeConverter
    fun externalUrlsToString(item: ExternalUrlsX): String = Gson().toJson(item)

    @TypeConverter
    fun stringToExternalUrls(string: String): ExternalUrlsX =
        Gson().fromJson(string, ExternalUrlsX::class.java)

    @TypeConverter
    fun albumToString(item: AlbumX): String = Gson().toJson(item)

    @TypeConverter
    fun stringToAlbum(string: String): AlbumX =
        Gson().fromJson(string, AlbumX::class.java)

    @TypeConverter
    fun externalIdsToString(item: ExternalIdsX): String = Gson().toJson(item)

    @TypeConverter
    fun stringToExternalIds(string: String): ExternalIdsX =
        Gson().fromJson(string, ExternalIdsX::class.java)
}