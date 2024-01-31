package fit.asta.health.wallet.remote.model


import com.google.gson.annotations.SerializedName

typealias CreditType = Int
typealias WalletType = Int
typealias SourceType = Int

data class WalletResponse(
    @SerializedName("txnData")
    val walletTransactionData: List<WalletTransactionData>? = null,
    @SerializedName("mnyTxn")
    val moneyAddedTransactionsData: List<MoneyAddedTransactionData>? = null,
    @SerializedName("wltData")
    val walletData: WalletData = WalletData()
) {
    data class MoneyAddedTransactionData(
        @SerializedName("type")
        val type: Int = 0,
        @SerializedName("oid")
        val orderId: String = "",
        @SerializedName("pid")
        val paymentId: String = "",
        @SerializedName("cDate")
        val cDate: String = "",
        @SerializedName("ttl")
        val ttl: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("amt")
        val amount: Int = 0,
        @SerializedName("sts")
        val status: String = "",
    )

    data class WalletTransactionData(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("uid")
        val uid: String = "",
        @SerializedName("tid")
        val tid: String = "",
        @SerializedName("sType")
        val sourceType: SourceType = 0,
        @SerializedName("cType")
        val creditType: CreditType = 0,
        @SerializedName("wType")
        val walletType: WalletType = 0,
        @SerializedName("rfr")
        val referredBy: String = "",
        @SerializedName("rfe")
        val referee: String = "",
        @SerializedName("time")
        val timeStamp: String = "",
        @SerializedName("from")
        val from: String = "",
        @SerializedName("to")
        val to: String = "",
        @SerializedName("amt")
        val amount: Double = 0.0,
    )

    data class WalletTransactionDebitData(
        @SerializedName("pts")
        val points: Double = 0.0,
        @SerializedName("mny")
        val money: Double = 0.0
    )

    data class WalletTransactionCreditData(
        @SerializedName("pts")
        val points: Double = 0.0,
        @SerializedName("mny")
        val money: Double = 0.0
    )

    data class WalletData(
        @SerializedName("mny")
        val money: Double = 0.0,
        @SerializedName("pts")
        val points: Double = 0.0,
        @SerializedName("id")
        val id: String = "",
        @SerializedName("uid")
        val uid: String = ""
    )
}

enum class SourceTypes(val code: SourceType) {
    SubscriptionUsage(1),
    AccountToWallet(2),
    WalletToAccount(3),
    ReferrerCredit(4),
    RefereeCredit(5),
}

enum class CreditTypes(val code: CreditType) {
    Debit(1),
    Credit(2)
}

enum class WalletTypes(val code: WalletType) {
    Money(1),
    Points(2)
}