package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName

data class SubscriptionFinalAmountData(
    @SerializedName("dtls")
    val details: Details = Details(),
    @SerializedName("fea")
    val features: List<Features> = listOf(),
    @SerializedName("tnc")
    val tnc: Tnc = Tnc()
) {
    data class Details(
        @SerializedName("amt")
        val amt: Double = 0.0,
        @SerializedName("fnlAmt")
        val fnlAmt: Double = 0.0,
        @SerializedName("ofr")
        val offerDiscount: Double = 0.0,
        @SerializedName("code")
        val offerCode: String = "",
        @SerializedName("ofrAmt")
        val ofrAmt: Double = 0.0,
        @SerializedName("sub")
        val sub: String = "",
        @SerializedName("ttl")
        val ttl: String = "",
        @SerializedName("unit")
        val unit: Int = 0,
        @SerializedName("url")
        val url: String = ""
    )

    data class Features(
        @SerializedName("dsc")
        val dsc: String = "",
        @SerializedName("ttl")
        val ttl: String = "",
        @SerializedName("url")
        val url: String = ""
    )

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
        val type: Int = 0
    ) {
        data class Sec(
            @SerializedName("cont")
            val cont: String = "",
            @SerializedName("ttl")
            val ttl: String = ""
        )
    }
}