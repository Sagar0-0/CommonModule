package fit.asta.health.scheduler.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.SchedulerLayoutItemAlarmBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.util.AlarmDiffUtils
import fit.asta.health.scheduler.viewmodel.AlarmViewModel

/**
 * This class is used as adapter to show data in recyclerview on home-screen
 */
class AlarmAdapter(
    private var mList: List<AlarmEntity>,
    private var viewModel: AlarmViewModel
) : RecyclerView.Adapter<AlarmAdapter.InnerViewHolder>() {

    // a class to hold references to the item of recyclerview
    class InnerViewHolder(private val binding: SchedulerLayoutItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alarmEntity: AlarmEntity, viewModel: AlarmViewModel) {
            binding.alarmItem = alarmEntity
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SchedulerLayoutItemAlarmBinding.inflate(layoutInflater, parent, false)
                return InnerViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(mList[position], viewModel)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(updatedList: List<AlarmEntity>) {
        val alarmDiffUtils = AlarmDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(alarmDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun getData(): List<AlarmEntity> {
        return mList
    }
}