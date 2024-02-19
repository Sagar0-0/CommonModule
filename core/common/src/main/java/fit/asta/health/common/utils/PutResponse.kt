package fit.asta.health.common.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PutResponse(
    @SerializedName("id")
    val id: String = "",
) : Parcelable
