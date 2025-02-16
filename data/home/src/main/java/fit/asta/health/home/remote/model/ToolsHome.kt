package fit.asta.health.home.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.testimonials.remote.model.Testimonial

data class ToolsHome(
    @SerializedName("bnr") val banners: List<Banner>? = null,
    @SerializedName("tml")
    val testimonials: List<Testimonial>? = null,
    @SerializedName("tool") val tools: List<HealthTool>? = null,
    @SerializedName("ust") val userTools: UserSelectedTool = UserSelectedTool(),
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
        @SerializedName("id") val id: String = "",
        @SerializedName("tid") val tid: List<String> = emptyList(),
        @SerializedName("uid") val uid: String = "",
    )
}