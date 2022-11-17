package fit.asta.health.thirdparty.spotify.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.ItemCategoriesBinding
import fit.asta.health.thirdparty.spotify.model.net.categories.Item
import fit.asta.health.thirdparty.spotify.utils.CommonDiffUtils

class SpotifyCategoriesAdapter(
    private var mList: List<Item>
) : RecyclerView.Adapter<SpotifyCategoriesAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryItem: Item) {
            binding.categoryItem = categoryItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoriesBinding.inflate(layoutInflater, parent, false)
                return InnerViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(updatedList: List<Item>) {
        val commonDiffUtils = CommonDiffUtils(mList, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(commonDiffUtils)
        mList = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}