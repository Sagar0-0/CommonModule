package fit.asta.health.profile.adapter.viewholders

import android.view.View
import androidx.core.widget.doAfterTextChanged
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.profile.adapter.PlainCardItem
import fit.asta.health.profile.adapter.ProfileItem
import fit.asta.health.profile.listener.OnChangeListener
import fit.asta.health.utils.toEditable
import kotlinx.android.synthetic.main.profile_plain_card.view.*

class PlainCardViewHolder(
    itemView: View,
    private val changeListener: OnChangeListener?
) : BaseViewHolder<ProfileItem>(itemView) {
    override fun bindData(content: ProfileItem) {
        if (content is PlainCardItem) {

            itemView.txtTitle.text = content.label
            if(content.itemValue.isNotEmpty()) {
                itemView.txtValue.text = content.itemValue.toEditable()
            }
            if(itemView.txtValue.toString().isEmpty()){
                itemView.txtValue.error = "Please provide proper input"
            }

            itemView.txtValue.doAfterTextChanged {
                if(it.toString().isNotBlank()){
                    content.updatedValue = it.toString()
                    changeListener?.onTextChange(content)
                }
            }
        }
    }
}