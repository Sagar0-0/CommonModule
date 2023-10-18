package fit.asta.health.auth.remote

import com.google.gson.annotations.SerializedName

data class DeleteAccountResponse(
    @SerializedName("flag")
    val flag: Boolean = false
)