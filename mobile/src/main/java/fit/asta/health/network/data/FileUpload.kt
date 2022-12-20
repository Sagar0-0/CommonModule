package fit.asta.health.network.data

import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("time")
    val time: String
)

data class SingleFileUpload(
    @SerializedName("id")
    val id: String,
    @SerializedName("feature")
    val feature: String,
    @SerializedName("mda")
    val media: Media
)

data class SingleFileUploadRes(
    @SerializedName("data")
    val singleFile: SingleFileUpload,
    @SerializedName("status")
    val status: Status
)

data class MultiFileUpload(
    @SerializedName("id")
    val id: String,
    @SerializedName("feature")
    val feature: String,
    @SerializedName("mda")
    val mda: List<Media>
)

data class MultiFileUploadRes(
    @SerializedName("data")
    val multiFile: MultiFileUpload,
    @SerializedName("status")
    val status: Status
)