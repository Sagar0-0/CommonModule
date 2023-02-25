package fit.asta.health.old.scheduler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.scheduler.adapter.viewholder.TimeCycleViewHolder
import fit.asta.health.old.scheduler.data.ScheduleTimeData


class TimeCyclesAdapter : BaseAdapter<ScheduleTimeData>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ScheduleTimeData> {
        val layout = R.layout.schedule_timecycle_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TimeCycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ScheduleTimeData>, position: Int) {
        val itemHolder = holder as TimeCycleViewHolder
        itemHolder.bindData(items[position])
    }

    fun addTime(time: ScheduleTimeData) {
        items.add(time)
        notifyDataSetChanged()
    }
}
