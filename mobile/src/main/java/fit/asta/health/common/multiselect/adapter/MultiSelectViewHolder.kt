package fit.asta.health.common.multiselect.adapter

import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.common.multiselect.data.MultiSelectData
import kotlinx.android.synthetic.main.listview_multiselect_item.view.*

class MultiSelectViewHolder(
    val view: View,
    private val selectionUpdateListener: SelectionUpdateListener?
) : BaseViewHolder<MultiSelectData>(view) {
    override fun bindData(content: MultiSelectData) {
        view.txtDisplayName.text = content.displayName
        view.imgSelectionMark.visibility = if (content.isSelected) View.VISIBLE else View.GONE

        view.setOnClickListener {
            it.imgSelectionMark.visibility = if (it.imgSelectionMark.visibility == View.VISIBLE) {
                selectionUpdateListener?.onSelectionUpdate(content.id, false)
                View.GONE
            } else {
                selectionUpdateListener?.onSelectionUpdate(content.id, true)
                View.VISIBLE
            }
        }
    }
}
