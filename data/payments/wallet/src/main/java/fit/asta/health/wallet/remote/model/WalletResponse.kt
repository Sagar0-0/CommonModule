package fit.asta.health.wallet.remote.model


import com.google.gson.annotations.SerializedName

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
        @SerializedName("type")
        val transactionType: WalletTransactionCode = WalletTransactionType.WalletMoneyUsed.code,
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
        @SerializedName("dr")
        val debitAmounts: WalletTransactionDebitData? = null,
        @SerializedName("cr")
        val creditAmounts: WalletTransactionCreditData? = null,
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

typealias WalletTransactionCode = Int

fun WalletTransactionCode.getWalletTransactionType() =
    WalletTransactionType.entries.first { this == it.code }

enum class WalletTransactionType(val code: WalletTransactionCode) {
    WalletMoneyUsed(1),
    AccountToWallet(2),
    WalletToAccount(3),
    ReferrerCredit(4),
    RefereeCredit(5),
    WalletPointsUsed(8)
}