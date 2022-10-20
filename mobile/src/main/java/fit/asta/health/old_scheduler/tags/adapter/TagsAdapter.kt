package fit.asta.health.old_scheduler.tags.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_scheduler.tags.adapter.viewholder.TagsViewHolder
import fit.asta.health.old_scheduler.tags.data.ScheduleTagData
import fit.asta.health.old_scheduler.tags.listner.ClickListener


class TagsAdapter : BaseAdapter<ScheduleTagData>() {

    private var tagSelectionListener: ClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ScheduleTagData> {
        val layout = R.layout.schedule_tags_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TagsViewHolder(view, tagSelectionListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ScheduleTagData>, position: Int) {
        val itemHolder = holder as TagsViewHolder
        itemHolder.bindData(items[position])
    }

    fun setAdapterListener(listener: ClickListener) {
        tagSelectionListener = listener
    }
}