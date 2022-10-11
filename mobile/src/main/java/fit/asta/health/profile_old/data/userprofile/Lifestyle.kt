package fit.asta.health.profile_old.data.userprofile


import com.google.gson.annotations.SerializedName

data class Lifestyle(
    @SerializedName("chipCards")
    var chipCards: List<ChipCard> = listOf(),
    @SerializedName("plainCard")
    var plainCard: PlainCard = PlainCard(),
    @SerializedName("schedule")
    var schedule: Schedule = Schedule()
)