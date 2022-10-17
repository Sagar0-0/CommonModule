package fit.asta.health.old_profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_profile.adapter.viewholders.BodyTypeViewHolder
import fit.asta.health.old_profile.adapter.viewholders.ChipsCardViewHolder
import fit.asta.health.old_profile.adapter.viewholders.PlainCardViewHolder
import fit.asta.health.old_profile.adapter.viewholders.SleepScheduleViewHolder
import fit.asta.health.old_profile.listener.OnChangeListener
import fit.asta.health.old_profile.listener.OnChipActionListener

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
                        .inflate(R.layout.profile_plain_card, parent, false), changeListener
                )
            }
            ProfileItemType.BodyTypeCard -> {
                BodyTypeViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.profile_boddtype_card, parent, false), changeListener
                )
            }
            ProfileItemType.SleepScheduleCard -> {

                SleepScheduleViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.profile_schedule_card, parent, false)
                )
            }
            ProfileItemType.ChipsCard -> {

                ChipsCardViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.profile_chips_card, parent, false), onChipListener
                )
            }
        }
    }
}