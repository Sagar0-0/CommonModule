package fit.asta.health.thirdparty.spotify.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.databinding.ItemUserAlbumBinding
import fit.asta.health.thirdparty.spotify.model.net.me.albums.Item
import fit.asta.health.thirdparty.spotify.utils.CommonDiffUtils

class SpotifyUserAlbumsAdapter(
    private var mList: List<Item>
) : RecyclerView.Adapter<SpotifyUserAlbumsAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: ItemUserAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item.album
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InnerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserAlbumBinding.inflate(layoutInflater, parent, false)
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