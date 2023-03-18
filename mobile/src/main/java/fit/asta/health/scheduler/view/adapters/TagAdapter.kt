package fit.asta.health.scheduler.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.SchedulerLayoutItemTagBinding
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.util.AlarmDiffUtils
import fit.asta.health.scheduler.viewmodel.AlarmSettingViewModel
import fit.asta.health.scheduler.viewmodel.AlarmViewModel


class TagAdapter(
    private var mList: List<TagEntity>,
    private var viewModel: AlarmViewModel,
    private var alarmSettingViewModel: AlarmSettingViewModel
) : RecyclerView.Adapter<TagAdapter.InnerViewHolder>() {

    private var selectedPosition = -1

    class InnerViewHolder(private val binding: SchedulerLayoutItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tagItem: TagEntity, viewModel: AlarmViewModel) {
            binding.tagItem = tagItem
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        var b = binding

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SchedulerLayoutItemTagBinding.inflate(layoutInflater, parent, false)
                return InnerViewHolder(binding)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(mList[position], viewModel)

        holder.b.tagItem?.selected = (holder.bindingAdapterPosition == selectedPosition)
        if (holder.b.tagItem?.selected == true) {
//            Log.d(TAG, "onBindViewHolder: ${holder.b.tagItem}")
            holder.b.container.strokeWidth = 10
        } else {
            holder.b.container.strokeWidth = 0
        }
        holder.b.container.setOnClickListener { v ->
            alarmSettingViewModel.setSelectedTag(holder.b.tagItem!!)
            selectedPosition = holder.bindingAdapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(updatedList: List<TagEntity>) {
        val tagDiffUtils = AlarmDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(tagDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun getData(): List<TagEntity> {
        return mList
    }
}