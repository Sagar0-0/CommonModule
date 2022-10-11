package fit.asta.health.profile.model.network

import fit.asta.health.network.data.Status

data class ProfileDao(
    val `data`: Map<String,Any>,
    val status: Status
)
