package fit.asta.health.navigation.tools.data.model.domain

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.testimonials.model.Testimonial

data class ToolsHomeRes(
    @SerializedName("data") val `data`: ToolsHome,
    @SerializedName("status") val status: Status,
) {
    data class ToolsHome(
        @SerializedName("bnr") val banners: List<Banner>?,
        @SerializedName("tml")
        val testimonials: List<Testimonial>?,
        @SerializedName("tool") val tools: List<HealthTool>?,
        @SerializedName("ust") val userTools: UserSelectedTool,
    ) {

        data class Banner(
            @SerializedName("dsc") val desc: String,
            @SerializedName("id") val id: String,
            @SerializedName("ttl") val ttl: String,
            @SerializedName("type") val type: Int,
            @SerializedName("url") val url: String,
            @SerializedName("vis") val vis: Boolean,
        )

        data class HealthTool(
            @SerializedName("code") val code: Int,
            @SerializedName("dsc") val desc: String,
            @SerializedName("id") val id: String,
            @SerializedName("name") val name: String,
            @SerializedName("ttl") val title: String,
            @SerializedName("url") val url: String,
        )

        data class UserSelectedTool(
            @SerializedName("id") val id: String,
            @SerializedName("tid") val tid: List<String>,
            @SerializedName("uid") val uid: String,
        )
    }

    data class Status(
        @SerializedName("code") val code: Int,
        @SerializedName("msg") val msg: String,
    )

}