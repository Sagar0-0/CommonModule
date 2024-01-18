package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName

typealias SubscriptionDurationType = Int

data class SubscriptionDurationsData(
    @SerializedName("durs")
    val planDurations: List<Duration> = listOf(),
    @SerializedName("fea")
    val planFeatures: List<Feature> = listOf(),
    @SerializedName("ofr")
    val offers: List<Offer> = listOf(),
    @SerializedName("tnc")
    val termsAndConditions: Tnc = Tnc()
) {
    data class Duration(
        @SerializedName("cur")
        val cur: Int = 0,
        @SerializedName("default")
        val default: Int = 0,
        @SerializedName("type")
        val type: SubscriptionDurationType = 1,
        @SerializedName("dsc")
        val dsc: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("price")
        val price: String = "",
        @SerializedName("sub")
        val sub: String = "",
        @SerializedName("sym")
        val sym: String = "",
        @SerializedName("tag")
        val tag: String = "",
        @SerializedName("ttl")
        val ttl: String = ""
    )

    data class Feature(
        @SerializedName("dsc")
        val dsc: String = "",
        @SerializedName("ttl")
        val ttl: String = "",
        @SerializedName("url")
        val url: String = ""
    )

    data class Offer(
        @SerializedName("areas")
        val areas: List<Area> = listOf(),
        @SerializedName("code")
        val code: String = "",
        @SerializedName("con")
        val con: String = "",
        @SerializedName("dsc")
        val dsc: String = "",
        @SerializedName("end")
        val end: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("ofr")
        val ofr: Int = 0,
        @SerializedName("start")
        val start: String = "",
        @SerializedName("sts")
        val sts: Int = 0,
        @SerializedName("ttl")
        val ttl: String = "",
        @SerializedName("type")
        val type: Int = 0,
        @SerializedName("unit")
        val unit: Int = 0,
        @SerializedName("url")
        val url: String = ""
    ) {
        data class Area(
            @SerializedName("cat")
            val cat: Int = 0,
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("prod")
            val prod: Int = 0,
            @SerializedName("type")
            val type: Int = 0
        )
    }

    data class Tnc(
        @SerializedName("eff")
        val eff: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("last")
        val last: String = "",
        @SerializedName("sec")
        val sec: List<Sec> = listOf(),
        @SerializedName("type")
        val type: String = ""
    ) {
        data class Sec(
            @SerializedName("cont")
            val cont: String = "",
            @SerializedName("ttl")
            val ttl: String = ""
        )
    }
}

enum class SubscriptionDurationTypes(val types: SubscriptionDurationType) {
    ONE_MONTH(1),
    THREE_MONTH(2),
    SIX_MONTH(3),
    TWELVE_MONTH(4)
}