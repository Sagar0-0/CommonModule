package fit.asta.health.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.common.data.MultiSelectData
import org.koin.core.KoinComponent

class MultiSelectAdapter: BaseAdapter<MultiSelectData>(), KoinComponent {

    private var selectionUpdateListener: SelectionUpdateListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MultiSelectData> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.multi_select_item, parent, false)
        return  MultiSelectViewHolder(view, selectionUpdateListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MultiSelectData>, position: Int) {
        holder.bindData(items[position])
    }

    fun setAdapterListener(listener: SelectionUpdateListener){
        selectionUpdateListener = listener
    }

}