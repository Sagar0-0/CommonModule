package fit.asta.health.profilenew.apiData

import fit.asta.health.network.data.Status

data class ProfileDao(
    val `data`: Map<String,Any>,
    val status: Status
)
