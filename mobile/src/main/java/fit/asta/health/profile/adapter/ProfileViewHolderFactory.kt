package fit.asta.health.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.profile.adapter.viewholders.BodyTypeViewHolder
import fit.asta.health.profile.adapter.viewholders.ChipsCardViewHolder
import fit.asta.health.profile.adapter.viewholders.PlainCardViewHolder
import fit.asta.health.profile.adapter.viewholders.SleepScheduleViewHolder
import fit.asta.health.profile.listener.OnChangeListener
import fit.asta.health.profile.listener.OnChipActionListener

class ProfileViewHolderFactory {

    fun create(
        parent: ViewGroup,
        viewType: Int,
        changeListener: OnChangeListener? = null,
        onChipListener: OnChipActionListener? = null
    ): BaseViewHolder<ProfileItem> {

        return when (ProfileItemType.valueOf(viewType)) {
            ProfileItemType.PlainCard -> {
                PlainCardViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.plain_card_item, parent, false), changeListener
                )
            }
            ProfileItemType.BodyTypeCard -> {
                BodyTypeViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.boddtype_card_item, parent, false), changeListener
                )
            }
            ProfileItemType.SleepScheduleCard -> {

                SleepScheduleViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.schedule_card_item, parent, false)
                )
            }
            ProfileItemType.ChipsCard -> {

                ChipsCardViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.chips_card_item, parent, false), onChipListener
                )
            }
        }
    }
}