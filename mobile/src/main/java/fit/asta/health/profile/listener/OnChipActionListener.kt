package fit.asta.health.profile.listener

import fit.asta.health.profile.adapter.ChipCardItem

interface OnChipActionListener {

    fun onChipAdd(chipCardItem: ChipCardItem)
    fun onChipUpdate(uid: String)
    fun onChipDelete(chipCardItem: ChipCardItem)
}