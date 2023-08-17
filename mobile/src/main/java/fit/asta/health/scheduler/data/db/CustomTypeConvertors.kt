package fit.asta.health.scheduler.data.db

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import fit.asta.health.scheduler.data.api.net.scheduler.Info
import fit.asta.health.scheduler.data.api.net.scheduler.Ivl
import fit.asta.health.scheduler.data.api.net.scheduler.Meta
import fit.asta.health.scheduler.data.api.net.scheduler.Time
import fit.asta.health.scheduler.data.api.net.scheduler.Tone
import fit.asta.health.scheduler.data.api.net.scheduler.Vib
import fit.asta.health.scheduler.data.api.net.scheduler.Wk
import fit.asta.health.scheduler.data.api.net.tag.Data
import fit.asta.health.scheduler.ref.data.Weekdays

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
    fun weekItemToString(weekItem: Wk): String = Gson().toJson(weekItem)

    @TypeConverter
    fun stringToWeekItem(string: String): Wk =
        Gson().fromJson(string, Wk::class.java)

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
    fun tagItemToString(item: Data): String = Gson().toJson(item)

    @TypeConverter
    fun stringToTagItem(string: String): Data =
        Gson().fromJson(string, Data::class.java)

    //    @TypeConverter
//    fun snoozeItemToString(snoozeItem: SnoozeItem): String = Gson().toJson(snoozeItem)
//
//    @TypeConverter
//    fun stringToSnoozeItem(string: String): SnoozeItem =
//        Gson().fromJson(string, SnoozeItem::class.java)
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun fromWeekdays(weekdays: Weekdays): Int {
        return weekdays.bits
    }

    @TypeConverter
    fun toWeekdays(bits: Int): Weekdays {
        return Weekdays(bits)
    }
}