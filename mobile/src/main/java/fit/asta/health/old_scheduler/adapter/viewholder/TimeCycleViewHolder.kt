package fit.asta.health.old_scheduler.adapter.viewholder

import android.annotation.SuppressLint
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_scheduler.data.ScheduleTimeData
import kotlinx.android.synthetic.main.schedule_timecycle_card.view.*

class TimeCycleViewHolder(viewItem: View) : BaseViewHolder<ScheduleTimeData>(viewItem) {

    private var currentItem: ScheduleTimeData? = null

    @SuppressLint("SetTextI18n")
    override fun bindData(content: ScheduleTimeData) {
        currentItem = content
        itemView.time_schedule_text_recyclerview.text = "${content.hour} : ${content.minute}"
    }
}