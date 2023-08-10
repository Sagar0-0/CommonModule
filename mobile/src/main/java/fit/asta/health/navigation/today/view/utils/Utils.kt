package fit.asta.health.navigation.today.view.utils

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object Utils {
    const val HourMinAmPmKey="hourMinAmPmKey"
    fun getDayAndTime(inputDateString:String= "2023-07-03T12:00"):DayAndTime{
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dayFormat = DateTimeFormatter.ofPattern("MMMM dd")
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val currentDateTime = LocalDateTime.now()
        try {
            val inputDateTime: LocalDateTime =
                LocalDateTime.parse(inputDateString, inputFormat)
            val currentTime: LocalTime = inputDateTime.toLocalTime()
            val timeOfDay = when {
                currentTime.isBefore(LocalTime.NOON) -> "Morning"
                currentTime.isBefore(LocalTime.of(17, 0)) -> "Afternoon"
                else -> "Evening"
            }
            val outputFormattedDay: String = when (ChronoUnit.DAYS.between(
                currentDateTime,
                inputDateTime
            )) {
                0L -> "Today"
                1L -> "Tomorrow"
                else -> dayFormat.format(inputDateTime)
            }
            Log.d("today", "getDayAndTime: inputDateTime $inputDateTime,currentTime $currentTime,timeOfDay")
            return DayAndTime(
                day = outputFormattedDay, time = timeFormatter.format(inputDateTime),
                timeOfDay=timeOfDay
            )
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        }
        return DayAndTime("","","")
    }
   fun getHourMinAmPm( inputTimeString:String = "12:00 PM",inputDay:String="Today"): HourMinAmPm {
       val inputFormat = DateTimeFormatter.ofPattern("hh:mm a")
       val day:Int = when(inputDay){
           "Today"->0
           "Tomorrow"->1
           else->2
       }
       try {
           val parsedTime = LocalTime.parse(inputTimeString, inputFormat)
           val hour24 = if (parsedTime.hour == 12) {
               if (parsedTime.minute > 0) 12 else 0
           } else {
               if (parsedTime.isBefore(LocalTime.NOON)) parsedTime.hour else parsedTime.hour + 12
           }

           val amPm = !parsedTime.isBefore(LocalTime.NOON)

           return HourMinAmPm(hour24, parsedTime.minute, amPm,day)
       } catch (e: Exception) {
           println("Error occurred: ${e.message}")
       }
       return HourMinAmPm(0,0,false,day)
   }
}
data class DayAndTime(val day:String,val time:String,val timeOfDay:String)
@Parcelize
data class HourMinAmPm(val hour:Int,val min:Int,val amPm:Boolean,val day:Int): Parcelable
