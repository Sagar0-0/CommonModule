package fit.asta.health.common.multiselect.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.common.multiselect.data.MultiSelectData

class MultiSelectViewHolder(
    val view: View,
    private val selectionUpdateListener: SelectionUpdateListener?
) : BaseViewHolder<MultiSelectData>(view) {
    override fun bindData(content: MultiSelectData) {
        view.findViewById<TextView>(R.id.txtDisplayName).text = content.displayName
        view.findViewById<ImageView>(R.id.imgSelectionMark).visibility =
            if (content.isSelected) View.VISIBLE else View.GONE

        view.setOnClickListener {
            val imgSelectionMark = it.findViewById<ImageView>(R.id.imgSelectionMark)
            imgSelectionMark.visibility = if (imgSelectionMark.visibility == View.VISIBLE) {
                selectionUpdateListener?.onSelectionUpdate(content.id, false)
                View.GONE
            } else {
                selectionUpdateListener?.onSelectionUpdate(content.id, true)
                View.VISIBLE
            }
        }
    }
}
