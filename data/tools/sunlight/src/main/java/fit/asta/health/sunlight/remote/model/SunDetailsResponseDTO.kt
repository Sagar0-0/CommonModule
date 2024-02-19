package com.hoho.assignmentsun.model

import com.google.gson.annotations.SerializedName

data class SunDetailsResponseDTO(
    @SerializedName("sunrise")
    var sunrise:String?=null,
    @SerializedName("sunset")
    var sunset:String?=null,
)