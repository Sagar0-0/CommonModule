package fit.asta.health.sunlight.remote.model

import androidx.annotation.DrawableRes

data class WeatherDetails(
    var title: String,
    var temp: String,
    var day: String,
    var time: String,
    @DrawableRes var weatherIcon: Int,
    @DrawableRes var timeIcon: Int,
){
    fun getTemDegreeSymbol():String{
        return "$temp °C"
    }
}