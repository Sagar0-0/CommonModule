package fit.asta.health.schedule.tags.adapter.viewholder

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.schedule.tags.data.ScheduleTagData
import fit.asta.health.schedule.tags.listner.ClickListener
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.schedule_tags_card.view.*

class TagsViewHolder(
    viewItem: View,
    private val tagSelectionListener: ClickListener?
) : BaseViewHolder<ScheduleTagData>(viewItem) {

    @SuppressLint("SetTextI18n")
    override fun bindData(content: ScheduleTagData) {

        itemView.tag_name.text = content.tagName
        itemView.imgSelectionMark.visibility = if (content.isSelected) View.VISIBLE else View.GONE
        itemView.context.showImageByUrl(
            Uri.parse(getPublicStorageUrl(itemView.context, content.url)),
            itemView.imgTag
        )

        itemView.setOnClickListener {

            it.imgSelectionMark.visibility = View.VISIBLE
            tagSelectionListener?.onSelectionUpdate(content, true)
        }
    }
}
