package fit.asta.health.feedback.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetFeedbackRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: NetFeedback
)

data class NetFeedback(
    @SerializedName("date")
    val date: String,
    @SerializedName("fid")
    val fid: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("qns")
    val qns: List<Qn>
)

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)

data class Qn(
    @SerializedName("isDet")
    val isDet: Boolean,
    @SerializedName("opts")
    val opts: List<String>,
    @SerializedName("qn")
    val qn: String,
    @SerializedName("qno")
    val qno: Int,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int
)