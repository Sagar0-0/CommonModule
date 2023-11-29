package fit.asta.health.wallet.remote.model


import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("transaction_data")
    val transactionData: List<TransactionData>? = null,
    @SerializedName("wallet_data")
    val walletData: WalletData = WalletData()
) {
    data class TransactionData(
        @SerializedName("credit_type")
        val creditType: Int = 0,
        @SerializedName("credits")
        val credits: Int = 0,
        @SerializedName("id")
        val id: String = "",
        @SerializedName("time_stamp")
        val timeStamp: String = "",
        @SerializedName("uid")
        val uid: String = "",
        @SerializedName("tid")
        val tid: String = "",
        @SerializedName("send_to")
        val sendTo: String = "",
        @SerializedName("received_from")
        val receivedFrom: String = "",
        @SerializedName("refBy")
        val refBy: String = "",
        @SerializedName("ref")
        val ref: String = ""
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