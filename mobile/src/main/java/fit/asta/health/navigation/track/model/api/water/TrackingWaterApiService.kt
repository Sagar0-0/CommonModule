package fit.asta.health.navigation.track.model.api.water

import fit.asta.health.navigation.track.model.network.water.NetWaterDailyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterMonthlyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterWeeklyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterYearlyRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackingWaterApiService {

    @GET("tracking/{trackingType}/daily/get/")
    suspend fun getDailyData(
        @Path("trackingType") trackingType: String,
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String
    ): NetWaterDailyRes

    @GET("tracking/{trackingType}/weekly/get/")
    suspend fun getWeeklyData(
        @Path("trackingType") trackingType: String,
        @Query("uid") uid: String,
        @Query("date") date: String
    ): NetWaterWeeklyRes

    @GET("tracking/{trackingType}/monthly/get/")
    suspend fun getMonthlyData(
        @Path("trackingType") trackingType: String,
        @Query("uid") uid: String,
        @Query("month") month: String
    ): NetWaterMonthlyRes

    @GET("tracking/{trackingType}/yearly/get/")
    suspend fun getYearlyData(
        @Path("trackingType") trackingType: String,
        @Query("uid") uid: String,
        @Query("year") year: String
    ): NetWaterYearlyRes
}