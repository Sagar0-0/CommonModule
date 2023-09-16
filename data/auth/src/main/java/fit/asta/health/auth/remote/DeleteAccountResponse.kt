package fit.asta.health.auth.remote

import com.google.gson.annotations.SerializedName

data class DeleteAccountResponse(
    @SerializedName("flag")
    val flag: Boolean = false,
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Status(
        @SerializedName("code")
        val code: Int=0,
        @SerializedName("msg")
        val msg: String=""
    )
}