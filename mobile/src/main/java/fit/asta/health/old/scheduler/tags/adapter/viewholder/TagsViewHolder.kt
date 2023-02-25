package fit.asta.health.old.scheduler.tags.adapter.viewholder

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.scheduler.tags.data.ScheduleTagData
import fit.asta.health.old.scheduler.tags.listner.ClickListener
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


class TagsViewHolder(
    viewItem: View,
    private val tagSelectionListener: ClickListener?
) : BaseViewHolder<ScheduleTagData>(viewItem) {

    @SuppressLint("SetTextI18n")
    override fun bindData(content: ScheduleTagData) {

        itemView.findViewById<TextView>(R.id.tag_name).text = content.tagName
        itemView.findViewById<ImageView>(R.id.imgSelectionMark).visibility =
            if (content.isSelected) View.VISIBLE else View.GONE
        itemView.context.showImageByUrl(
            Uri.parse(getPublicStorageUrl(itemView.context, content.url)),
            itemView.findViewById(R.id.imgTag)
        )

        itemView.setOnClickListener {

            it.findViewById<ImageView>(R.id.imgSelectionMark).visibility = View.VISIBLE
            tagSelectionListener?.onSelectionUpdate(content, true)
        }
    }
}
