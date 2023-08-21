package fit.asta.health.common.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T>(protected var items: List<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindData(holder: RecyclerView.ViewHolder, item: T, pos: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
        RecyclerView.ViewHolder {

        return setViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        onBindData(holder, items[position], position)
    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun updateList(inItems: List<T>) {

        items = inItems
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {

        return items[position]
    }
}