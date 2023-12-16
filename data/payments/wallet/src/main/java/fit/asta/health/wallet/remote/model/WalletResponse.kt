package fit.asta.health.wallet.remote.model


import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("transaction_data")
    val walletTransactionData: List<WalletTransactionData>? = null,
    @SerializedName("moneyAddedTransactions")
    val moneyAddedTransactionsData: List<MoneyAddedTransactionData>? = null,
    @SerializedName("wallet_data")
    val walletData: WalletData = WalletData()
) {
    data class MoneyAddedTransactionData(
        @SerializedName("type")
        val type: Int = 0,
        @SerializedName("orderId")
        val orderId: String = "",
        @SerializedName("paymentId")
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
        val transactionType: WalletTransactionCode = WalletTransactionType.SUBSCRIPTION_CASHBACK.code,
        @SerializedName("referrer")
        val referredBy: String = "",
        @SerializedName("referee")
        val referee: String = "",
        @SerializedName("timeStamp")
        val timeStamp: String = "",
        @SerializedName("from")
        val from: String = "",
        @SerializedName("to")
        val to: String = "",
        @SerializedName("debit")
        val debitAmounts: WalletTransactionDebitData? = null,
        @SerializedName("credit")
        val creditAmounts: WalletTransactionCreditData? = null,
    )

    data class WalletTransactionDebitData(
        @SerializedName("points")
        val points: Int = 0,
        @SerializedName("money")
        val money: Int = 0
    )

    data class WalletTransactionCreditData(
        @SerializedName("points")
        val points: Int = 0,
        @SerializedName("money")
        val money: Int = 0
    )

    data class WalletData(
        @SerializedName("money")
        val money: Int = 0,
        @SerializedName("points")
        val points: Int = 0,
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
    SUBSCRIPTION_CASHBACK(1),
    ADD_MONEY(2),
    REFERRAL_CASHBACK(3),
    SEND_TO_BANK(4),
    WALLET_MONEY_USED(5),
    WALLET_POINTS_USED(6)
}