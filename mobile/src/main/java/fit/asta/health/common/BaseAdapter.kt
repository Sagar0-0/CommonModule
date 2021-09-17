package fit.asta.health.common

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    protected var items: ArrayList<T> = arrayListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(inItems: List<T>) {
        items.clear()
        items.addAll(inItems)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return items[position]
    }
}