package fit.asta.health.scheduler.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.LayoutItemCustomIntervalBinding
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.util.AlarmDiffUtils
import fit.asta.health.scheduler.view.interfaces.DialogInterface

class VariantIntervalAdapter(
    private var mList: List<Stat>,
    private var listener: DialogInterface
//    private var viewModel: AlarmViewModel
) : RecyclerView.Adapter<VariantIntervalAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: LayoutItemCustomIntervalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            alarmTimeItem: Stat,
            listener: DialogInterface /*, viewModel: AlarmViewModel*/
        ) {
            binding.alarmTimeItem = alarmTimeItem
            binding.listener = listener
//            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemCustomIntervalBinding.inflate(layoutInflater, parent, false)
                return InnerViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(mList[position], listener/*, viewModel*/)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(updatedList: List<Stat>) {
        val tagDiffUtils = AlarmDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(tagDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun getData(): List<Stat> {
        return mList
    }
}