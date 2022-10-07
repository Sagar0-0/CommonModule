package fit.asta.health.profilenew.apiData

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class ProfileDao(
    val `data`: Map<String,Any>,
    val status: Status
)
