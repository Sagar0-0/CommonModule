package fit.asta.health.old_profile.adapter.viewholders

import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_profile.adapter.BodyTypeItem
import fit.asta.health.old_profile.adapter.ProfileItem
import fit.asta.health.old_profile.listener.OnChangeListener
import kotlinx.android.synthetic.main.profile_boddtype_card.view.*

@Suppress("UNUSED_PARAMETER")
class BodyTypeViewHolder(
    itemView: View,
    changeListener: OnChangeListener?
): BaseViewHolder<ProfileItem>(itemView) {
    override fun bindData(content: ProfileItem) {

        if (content is BodyTypeItem) {

            itemView.txtTitle.text = content.label
            itemView.txtValue.text = content.bodyTypeValue

        }
    }
}