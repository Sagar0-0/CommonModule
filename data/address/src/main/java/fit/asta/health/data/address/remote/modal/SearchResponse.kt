package fit.asta.health.data.address.remote.modal


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("html_attributions")
    val htmlAttributions: List<Any> = listOf(),
    @SerializedName("next_page_token")
    val nextPageToken: String = "",
    @SerializedName("results")
    val results: List<Result> = listOf(),
    @SerializedName("status")
    val status: String = ""
) {
    data class Result(
        @SerializedName("business_status")
        val businessStatus: String,
        @SerializedName("formatted_address")
        val formattedAddress: String,
        @SerializedName("geometry")
        val geometry: Geometry,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("icon_background_color")
        val iconBackgroundColor: String,
        @SerializedName("icon_mask_base_uri")
        val iconMaskBaseUri: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("opening_hours")
        val openingHours: OpeningHours,
        @SerializedName("permanently_closed")
        val permanentlyClosed: Boolean,
        @SerializedName("photos")
        val photos: List<Photo>,
        @SerializedName("place_id")
        val placeId: String,
        @SerializedName("plus_code")
        val plusCode: PlusCode,
        @SerializedName("price_level")
        val priceLevel: Int,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("reference")
        val reference: String,
        @SerializedName("categories")
        val types: List<String>,
        @SerializedName("user_ratings_total")
        val userRatingsTotal: Int
    ) {
        data class Geometry(
            @SerializedName("location")
            val location: Location,
            @SerializedName("viewport")
            val viewport: Viewport
        ) {
            data class Location(
                @SerializedName("lat")
                val lat: Double,
                @SerializedName("lng")
                val lng: Double
            )

            data class Viewport(
                @SerializedName("northeast")
                val northeast: Northeast,
                @SerializedName("southwest")
                val southwest: Southwest
            ) {
                data class Northeast(
                    @SerializedName("lat")
                    val lat: Double,
                    @SerializedName("lng")
                    val lng: Double
                )

                data class Southwest(
                    @SerializedName("lat")
                    val lat: Double,
                    @SerializedName("lng")
                    val lng: Double
                )
            }
        }

        data class OpeningHours(
            @SerializedName("open_now")
            val openNow: Boolean
        )

        data class Photo(
            @SerializedName("height")
            val height: Int,
            @SerializedName("html_attributions")
            val htmlAttributions: List<String>,
            @SerializedName("photo_reference")
            val photoReference: String,
            @SerializedName("width")
            val width: Int
        )

        data class PlusCode(
            @SerializedName("compound_code")
            val compoundCode: String,
            @SerializedName("global_code")
            val globalCode: String
        )
    }
}