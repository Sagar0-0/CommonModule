package fit.asta.health.common.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetaData(
    @SerializedName("adoLoc")
    val adoLoc: String,
    @SerializedName("imgLoc")
    val imgLoc: String,
    @SerializedName("vdoLoc")
    val vdoLoc: String,
    @SerializedName("artLoc")
    val artLoc: String
) : Parcelable
