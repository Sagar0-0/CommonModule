package fit.asta.health.data.scheduler.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import fit.asta.health.data.scheduler.db.entity.Weekdays
import fit.asta.health.data.scheduler.remote.net.scheduler.Info
import fit.asta.health.data.scheduler.remote.net.scheduler.Ivl
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.remote.net.scheduler.Time
import fit.asta.health.data.scheduler.remote.net.scheduler.Tone
import fit.asta.health.data.scheduler.remote.net.scheduler.Vib
import fit.asta.health.data.scheduler.remote.net.tag.TagData

class CustomTypeConvertors {
    @TypeConverter
    fun ringtoneToString(ringtone: Tone): String = Gson().toJson(ringtone)

    @TypeConverter
    fun stringToRingtone(string: String): Tone =
        Gson().fromJson(string, Tone::class.java)

    @TypeConverter
    fun vibrateItemToString(vibrationItem: Vib): String = Gson().toJson(vibrationItem)

    @TypeConverter
    fun stringToVibrateItem(string: String): Vib =
        Gson().fromJson(string, Vib::class.java)


    @TypeConverter
    fun intervalItemToString(intervalItem: Ivl): String = Gson().toJson(intervalItem)

    @TypeConverter
    fun stringToIntervalItem(string: String): Ivl =
        Gson().fromJson(string, Ivl::class.java)

    @TypeConverter
    fun metaItemToString(metaItem: Meta): String = Gson().toJson(metaItem)

    @TypeConverter
    fun stringToMetaItem(string: String): Meta =
        Gson().fromJson(string, Meta::class.java)

    @TypeConverter
    fun infoItemToString(intervalItem: Info): String = Gson().toJson(intervalItem)

    @TypeConverter
    fun stringToInfoItem(string: String): Info =
        Gson().fromJson(string, Info::class.java)

    @TypeConverter
    fun timeItemToString(item: Time): String = Gson().toJson(item)

    @TypeConverter
    fun stringToTimeItem(string: String): Time =
        Gson().fromJson(string, Time::class.java)

    @TypeConverter
    fun tagItemToString(item: TagData): String = Gson().toJson(item)

    @TypeConverter
    fun stringToTagItem(string: String): TagData =
        Gson().fromJson(string, TagData::class.java)

    @TypeConverter
    fun fromWeekdays(weekdays: Weekdays): String = Gson().toJson(weekdays)

    @TypeConverter
    fun toWeekdays(bits: String): Weekdays =
        Gson().fromJson(bits, Weekdays::class.java)
}